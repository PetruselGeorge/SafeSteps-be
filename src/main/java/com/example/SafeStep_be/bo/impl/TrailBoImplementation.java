package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.ReverseGeocodingBo;
import com.example.SafeStep_be.bo.TrailBo;
import com.example.SafeStep_be.bo.utils.ComputeTotalDistance;
import com.example.SafeStep_be.bo.utils.LatLng;
import com.example.SafeStep_be.bo.utils.TrailDifficultyEstimator;
import com.example.SafeStep_be.data.access.layer.TrailCoordinateRepository;
import com.example.SafeStep_be.data.access.layer.TrailRepository;
import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.dto.TrailSummaryDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailBoImplementation implements TrailBo {

    private final TrailRepository trailRepository;
    private final TrailCoordinateRepository trailCoordinateRepository;
    private final TrailDifficultyEstimator trailDifficultyEstimator;
    private final ReverseGeocodingBo reverseGeocodingBo;

    @Override
    public TrailEntity createTrailFromGpx(MultipartFile gpxFile) {
        try {
            InputStream is = gpxFile.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);

            NodeList nameNodes = doc.getElementsByTagNameNS("*", "name");
            String name = (nameNodes.getLength() > 0) ? nameNodes.item(0).getTextContent() : "Unnamed Trail";

            NodeList segNodes = doc.getElementsByTagNameNS("*", "trkseg");
            List<LatLng> allPoints = new ArrayList<>();
            List<TrailCoordinateEntity> coordinateEntities = new ArrayList<>();

            TrailEntity trailEntity = TrailEntity.builder()
                    .name(name)
                    .difficulty("pending")
                    .distanceKm(BigDecimal.ZERO)
                    .build();

            trailRepository.save(trailEntity);

            int segmentIndex = 0;
            for (int s = 0; s < segNodes.getLength(); s++) {
                Element seg = (Element) segNodes.item(s);
                NodeList trkptNodes = seg.getElementsByTagNameNS("*", "trkpt");

                for (int i = 0; i < trkptNodes.getLength(); i++) {
                    Element trkpt = (Element) trkptNodes.item(i);
                    double lat = Double.parseDouble(trkpt.getAttribute("lat"));
                    double lon = Double.parseDouble(trkpt.getAttribute("lon"));
                    LatLng point = new LatLng(lat, lon);
                    allPoints.add(point);

                    coordinateEntities.add(TrailCoordinateEntity.builder()
                            .trail(trailEntity)
                            .latitude(lat)
                            .longitude(lon)
                            .positionOrder(i)
                            .segmentIndex(segmentIndex)
                            .build());
                }
                segmentIndex++;
            }

            trailCoordinateRepository.saveAll(coordinateEntities);

            BigDecimal distance = ComputeTotalDistance.computeTotalDistance(allPoints);
            String difficulty = trailDifficultyEstimator.estimateDifficulty(allPoints, name, distance);
            trailEntity.setDistanceKm(distance);
            trailEntity.setDifficulty(difficulty);

            if (!allPoints.isEmpty()) {
                LatLng firstPoint = allPoints.getFirst();
                String location = reverseGeocodingBo.getCountryFromCoordinates(firstPoint.getLat(), firstPoint.getLon());
                trailEntity.setLocation(location);
            }

            trailRepository.save(trailEntity);
            return trailEntity;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<TrailEntity> getAllTrails(Pageable pageable) {
        return trailRepository.findAll(pageable);
    }

    @Override
    public void updateMainImage(UUID trailId, MultipartFile file) {
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        try {
            byte[] imageBytes = file.getBytes();
            trail.setMainImage(imageBytes);
            trailRepository.save(trail);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }
    }

    @Override
    public Optional<TrailEntity> findById(UUID trailId) {
        return trailRepository.findById(trailId);
    }

    @Override
    public Page<TrailSummaryDto> searchWithFilters(String name, BigDecimal maxDistance, String difficulty, Pageable pageable) {
        return trailRepository.searchWithFilters(name, maxDistance, difficulty, pageable);
    }
}

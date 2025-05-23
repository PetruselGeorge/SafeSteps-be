package com.example.SafeStep_be.bo;

import org.springframework.web.multipart.MultipartFile;

public interface TrailBo {

    void createTrailFromGpx(MultipartFile gpxFile);

}

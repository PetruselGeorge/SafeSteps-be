package com.example.SafeStep_be.bo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;



public class ComputeTotalDistance {
    public static BigDecimal computeTotalDistance(List<LatLng> points){
        double totalKm=0.0;
        for(int i=1; i<points.size();i++){
            totalKm += haversine(points.get(i-1),points.get(i));
        }

        return BigDecimal.valueOf(totalKm).setScale(2, RoundingMode.HALF_UP);
    }

    private static double haversine(LatLng p1, LatLng p2){
        double R = 6371;
        double dLat = Math.toRadians(p2.getLat() - p1.getLat());
        double dLon = Math.toRadians(p2.getLon() - p1.getLon());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(p1.getLat())) * Math.cos(Math.toRadians(p2.getLat())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
    }
}

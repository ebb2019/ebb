package com.ebb.utils;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;

public class DistUtils {
    /**
     * 计算两点之间的距离
     * @param lonA 经度A
     * @param latA 纬度A
     * @param lonB 经度B
     * @param latB 纬度B
     * @return  两点之间的距离
     */
    public static double countDistance(double lonA,double latA,double lonB,double latB){
        SpatialContext geo = SpatialContext.GEO;
        double distance = geo.calcDistance(geo.makePoint(lonA, latA), geo.makePoint(lonB, latB)) * DistanceUtils.DEG_TO_KM;
        return distance;
    }
}

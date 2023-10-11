package me.urkkiz.util;

import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Transform {
    public static void MovePolygon(Polygon polygon, float x, float y){
        int[][] coordinates=new int[][]{polygon.xpoints, polygon.ypoints};
        for(int i = 0; i < polygon.npoints; i++) {
            coordinates[0][i] += (int) ((coordinates[0][i]+x)-coordinates[0][i]);
            coordinates[1][i] += (int) ((coordinates[1][i]+y)-coordinates[1][i]);
            //coordinates[1][i]= coordinates[1][i]+Math.round((coordinates[1][i]+y)-coordinates[1][i]);
        }
    }
    public static void RotatePolygon(Polygon polygon, float amount){
        System.out.println("real");
    }
    public static void ScalePolygon(Polygon polygon, float x, float y){

    }
}

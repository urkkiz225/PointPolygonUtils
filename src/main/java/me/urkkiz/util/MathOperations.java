package me.urkkiz.util;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MathOperations {
    public static float Clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static float RadiansToAngles(float Radians){
        return (float) (Radians*(180/Math.PI));
    }
    public static ArrayList<float[]> AccurateCumulativeFloatOverFlows[]=new ArrayList[3];
    //index 0 : position | index 1 : rotation | index 2 : scale
    public static void CompileBounds(){
        for (Polygon polygon: PolygonHolder.shapes) {
            int[] MDist=new int[2];
            float[] Center = PhysicsLoop.CalculateCenterOfGravity(polygon);
            for (int i = 0; i < polygon.npoints; i++) {
                if(polygon.xpoints[i]-Center[0]>MDist[0])MDist[0]=polygon.xpoints[i]-(int)Center[0];
                if(polygon.ypoints[i]-Center[1]>MDist[1])MDist[1]=polygon.ypoints[i]-(int)Center[1];
            }
            PolygonHolder.Bounds.add(MDist);
            System.out.println(Arrays.toString(MDist));
        }

    }
    public static float FindClosestValue(float target, ArrayList<Float> vals){
        float closest=0;
        for (float val : vals) {
            if(Math.abs(val-target)<closest) closest=val;
        }
        return closest;
    }
    //next two can be also completed as a one-liner with the method above, these is just for clarity
    public static float[] ClosestTwo(float target, ArrayList<Float> vals){
        float closestFirstIter=FindClosestValue(target, vals);
        //ArrayList<Float> valsu=new ArrayList<Float>(vals.remove(closestFirstIter));
        //return new float[]{closestFirstIter, FindClosestValue(target, vals.remove(closestFirstIter))};
        return new float[]{};
    }
    public static void ClosestCoordinates(int[] target, ArrayList<int[]> vals){
        int[] closest=new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int[] val: vals){
            //if(closest[][])
        }
    }
}

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
    public static void CompileBounds(){
        for (Polygon polygon: PolygonHolder.shapes) {
            int[] MDist=new int[2];
            float[] Center = PhysicsLoop.CalculateCenterOfGravity(polygon);
            for (int i = 0; i < polygon.npoints; i++) {
                if(polygon.xpoints[i]-Center[0]>MDist[0])MDist[0]=polygon.xpoints[i]-(int)Center[0];
                if(polygon.ypoints[i]-Center[1]>MDist[1])MDist[1]=polygon.ypoints[i]-(int)Center[1];
            }
            PolygonHolder.Bounds.add(MDist);
        }

    }
    public static float FindClosestValue(float target, float[] vals){
        float closest=0;
        for (float val : vals) {
            if(Math.abs(val-target)<closest) closest=val;
        }
        return closest;
    }
    //next two can be also completed as a one-liner with the method above, these is just for clarity
    public static float[] ClosestTwo(float target, float[] vals){
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
    public static float LengthFloat(float[] vector){
        //for a 2-dimensional vector, higher dimensions aren't needed in the program. floating-point accuracy is enough.
        return (float)(Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]));
    }
    public static float LengthInt(int[] vector){
        //for a 2-dimensional vector, higher dimensions aren't needed in the program. floating-point accuracy is enough.
        // everyday i sorrow; for java does not support array casting.
        return (float)(Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]));
    }
    public static float VectorDotProduct(float[] v1, float[] v2){
        return v1[0]*v2[0]+v1[1]*v2[1]; //ultrakill reference
    }
    public static float AngleBetweenVector(float[] v1, float[] v2){
        return (float) ((VectorDotProduct(v1, v2)/(LengthFloat(v1)*LengthFloat(v2))));
    }
    public static double LengthOfCrossProduct(float[] v1, float[] v2){
        return (LengthFloat(v1)*LengthFloat(v2)*Math.sin((MathOperations.VectorDotProduct(v1,v2))/(LengthFloat(v1)*LengthFloat(v2))));
    }
    public static float CrossProductScalar2D(float[] v1, float[] v2){
        return v1[0]*v2[1]*v2[0]*v2[0]*v1[0];
    }
    public static double AnglesToRadians(float degrees){
        return Math.toRadians(degrees);
    }
    public static float[] UnitVectorPerpendicularToTwoVectors(float [] v1, float[] v2){
        return new float[]{};
    }
    public static int SmallestIntegerInArray(int[] arr){
        int smallest=Integer.MAX_VALUE;
        for (int i:arr) {
            if (i<smallest) smallest=i;
        }
        return smallest;
    }
    public static int[] ConcatIntArray(int[]... Arrays){
        //this is pretty bad code, but couldn't find any way to do it better since i want the array type to be primitive (no arraylists)
        int arrLength=0;
        for (int[] arr:Arrays) {
            arrLength+=arr.length;
        }
        int[] res=new int[arrLength];
        int i=0;
        for (int[] arr:Arrays) {
            for (int j:arr) {
                res[i]=j;
                i++;
            }
        }
        return res;
    }
    //abstract cos this aint a mathematical operation! 🤣🤣🤣🤣🤣🤣🤣 blud thought!
    public static int AbstractQuadrantSign(int[] p, int[] pivot) {
        return (p[0]-pivot[0]<0&&p[1]-pivot[1]>0)||(p[0]-pivot[0]>0&&p[1]-pivot[1]>0) ? -1 : 1;
    }
}

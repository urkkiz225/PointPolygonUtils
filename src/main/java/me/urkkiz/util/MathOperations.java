package me.urkkiz.util;

import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.util.ArrayList;

public class MathOperations {
    public static float Clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static float RadiansToAngles(float Radians){
        return (float) (Radians*(180/Math.PI));
    }
    public static float FindClosestValue(float target, float[] vals){
        float closest=0;
        for (float val : vals) {
            if(Math.abs(val-target)<closest) closest=val;
        }
        return closest;
    }
    public static float LengthFloat(float[] vector){
        //for a 2-dimensional vector, higher dimensions aren't needed in the program. floating-point accuracy is enough.
        return (float)(Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]));
    }
    public static float LengthInt(int[] vector){
        //for a 2-dimensional vector, higher dimensions aren't needed in the program. floating-point accuracy is enough.
        // every day i sorrow; for java does not support array casting.
        //TODO implement fast inverse square root and split function respectively
        return (float)(Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]));
    }
    public static float VectorDotProduct(float[] v1, float[] v2){
        return v1[0]*v2[0]+v1[1]*v2[1]; //ultrakill reference
    }
    public static float AngleBetweenVector(float[] v1, float[] v2){
        return ((VectorDotProduct(v1, v2)/(LengthFloat(v1)*LengthFloat(v2))));
    }
    public static double LengthOfCrossProduct(float[] v1, float[] v2){
        return (LengthFloat(v1)*LengthFloat(v2)*Math.sin((MathOperations.VectorDotProduct(v1,v2))/(LengthFloat(v1)*LengthFloat(v2))));
    }
    public static double DegreesToRadians(float degrees){
        //woah! what a cool function! i sure hope it is original code.
        return Math.toRadians(degrees);
    }
    public static int SmallestIntegerInArray(int[] arr){
        int smallest=Integer.MAX_VALUE;
        for (int i:arr) {
            if (i<smallest) smallest=i;
        }
        return smallest;
    }
    public static int[][] ClosestTwoPoints(int[] p1, ArrayList<int[]> points){
        int[][] c = new int[2][2];
        float cL = 0;
        for (int[] p2:points) {
            if(LengthInt(new int[]{p2[0]-p1[0], p2[1]-p1[1]})>cL){
                c[0]=p2;
                cL=LengthInt(new int[]{p2[0]-p1[0], p2[1]-p1[1]});
            }
        }
        points.remove(c[0]);
        cL=0;
        for (int[] p2:points) {
            if (LengthInt(new int[]{p2[0]-p1[0], p2[1]-p1[1]}) > cL) {
                c[1] = p2;
                cL = LengthInt(new int[]{p2[0]-p1[0], p2[1]-p1[1]});
            }
        }
        return c;
    }
    public static int[] ConcatIntArray(int[]... Arrays){
        //this is pretty bad code, but couldn't find any way to do it better since I want the array type to be primitive (no arraylists)
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
    public static float DeltaPointDirection(int[] pMinusOne, int[] p){
        return (p[0]!=pMinusOne[0])?((float)p[1]-(float)pMinusOne[1])/((float)p[0]-(float)pMinusOne[0]):0; // a completely vertical line cannot have a rational slope. 0  is returned in this case, because I hate null values.
    }
    //an alternative to calculate concave polygons with cross product with magnitude (instead of dot product and vector normals, which is currently used.)
    public static int AbstractQuadrantSign(int[] p, int[] pivot) {
        return (p[0]-pivot[0]<0&&p[1]-pivot[1]>0)||(p[0]-pivot[0]>0&&p[1]-pivot[1]>0) ? -1 : 1;
    }
    //this doesn't work. will work on later. maybe.
    public static float[] CalculateCentroid(Polygon p) {
        double area = CalculateArea(p);
        float[] cxy = new float[2];
        for (int i = 0; i < p.npoints; i++) {
            double term = Math.abs(p.xpoints[i] * p.ypoints[(i + 1) % p.npoints] - p.xpoints[(i + 1) % p.npoints] * p.ypoints[i]); // Take absolute value
            cxy[0] += (p.xpoints[i] + p.xpoints[(i + 1) % p.npoints]) * term;
            cxy[1] += (p.ypoints[i] + p.ypoints[(i + 1) % p.npoints]) * term;
        }
        cxy[0] /= (6 * area);
        cxy[1] /= (6 * area);
        return cxy;
    }
    private static double CalculateArea(Polygon p) {
        double area = 0;
        for (int i = 0; i < p.npoints; i++) {
            area += p.xpoints[i] * p.ypoints[(i + 1) % p.npoints] - p.xpoints[(i + 1) % p.npoints] * p.ypoints[i];
        }
        return Math.abs(area / 2);
    }
    public static Object[] CheckLineIntersection(int[][] l1, int[][] l2) {
        float[] slopes = new float[]{DeltaPointDirection(l1[0], l1[1]), DeltaPointDirection(l2[0], l2[1])};
        //parallel line check
        if (slopes[0] == slopes[1]) return new Object[]{false};
        else {
            float[] intersection=new float[]{(slopes[0] * l1[0][0] - slopes[1] * l2[0][0] + l2[0][1] - l1[0][1]) / (slopes[0] - slopes[1]),slopes[0] * ((slopes[0] * l1[0][0] - slopes[1] * l2[0][0] + l2[0][1] - l1[0][1]) / (slopes[0] - slopes[1]) - l1[0][0]) + l1[0][1]};
            if ((PointComponentLiesWithin(new int[]{l1[0][0], l1[1][0]}, intersection[0]) && PointComponentLiesWithin(new int[]{l1[0][1], l1[1][1]}, intersection[1])) && (PointComponentLiesWithin(new int[]{l2[0][0], l2[1][0]}, intersection[0]) && PointComponentLiesWithin(new int[]{l2[0][1], l2[1][1]}, intersection[1]))) {
                return new Object[]{false, intersection};
            } else {
                return new Object[]{false};
            }
        }
    }
    private static boolean PointComponentLiesWithin (int[] p, float p1){
        return p1 >= Math.min(p[0], p[1]) && p1 <= Math.max(p[0], p[1]);
    }
    public static float GetPenetrationDepthOfPointOnPolygon(int[] p, int MasterIndex, Polygon poly){
        //this threw ArrayIndexOutOfBounds() exceptions and then... stopped throwing them from that point forward after a rerun. I never could replicate it. ???
        //TODO use twoclosestpoints() to check the edge of the closest two points. This is the most likely edge to intersect. but whatever.
        for (int i = 0; i < poly.npoints; i++) {
            Object[] IntersectionData = CheckLineIntersection(new int[][]{new int[]{poly.xpoints[i], poly.ypoints[i]},new int[]{poly.xpoints[(i + 1) % poly.npoints], poly.ypoints[(i + 1) % poly.npoints]}},
                    new int[][]{new int[]{p[0], p[1]}, new int[]{Transform.DeltaPositions.get(MasterIndex)[0][(int) Clamp(i,0, PolygonHolder.shapes.get(MasterIndex).npoints-1)],Transform.DeltaPositions.get(MasterIndex)[1][(int) Clamp(i,0, PolygonHolder.shapes.get(MasterIndex).npoints-1)]}});
            //the second array element of IntersectionData is only really used if your fps is very low -> in most cases returns 0.0.
            if((boolean)IntersectionData[0]) {
                return LengthFloat(new float[]{((float[]) IntersectionData[1])[0] - PolygonHolder.shapes.get(MasterIndex).xpoints[(int) Clamp(i, 0, PolygonHolder.shapes.get(MasterIndex).npoints - 1)], ((float[]) IntersectionData[1])[1] - PolygonHolder.shapes.get(MasterIndex).ypoints[(int) Clamp(i, 0, PolygonHolder.shapes.get(MasterIndex).npoints - 1)]});
            }
        }
        return 0;
    }
}

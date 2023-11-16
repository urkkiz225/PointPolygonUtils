package me.urkkiz.Physics;

import me.urkkiz.MainWindow.MainLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsLoop {
    public static float Angles=0;
    public static boolean ConcaveDebug=false;

    public static void physicsLoop(){
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            if(ConcaveDebug) IsPolygonConcave(PolygonHolder.shapes.get(i));
            //please got damnit pass only convex polygons
            int[] previousPos=new int[]{PolygonHolder.shapes.get(i).ypoints[0],PolygonHolder.shapes.get(i).xpoints[0]};
            //Transform.MovePolygon(PolygonHolder.shapes.get(i), 0, CalculateGravity(i) - PolygonHolder.shapes.get(i).ypoints[0]);
            Transform.RotatePolygon(i, Angles,CalculateCenterOfGravity(PolygonHolder.shapes.get(i)));
            //Detect if shape has not moved \/ (plz make dual point precision later)
            if(PolygonHolder.shapes.get(i).xpoints[0]-previousPos[0]==0&&PolygonHolder.shapes.get(i).ypoints[1]-previousPos[1]==0) TimeManager.Timers.set(i,0F);
        }
    }
    public static float CalculateGravity(int TimerIndex){
        return (MathOperations.Clamp((0 + (GravitationalConstant*(TimeManager.Timers.get(TimerIndex)*TimeManager.Timers.get(TimerIndex)))), 0, 420));
    }
    public static float[] CalculateCenterOfGravity(Polygon polygon){

        float[] res=new float[2];
        //average of points
        for (int i = 0; i < polygon.npoints; i++) {
            res[0]+=polygon.xpoints[i];
            res[1]+=polygon.ypoints[i];
        }
        return new float[]{res[0] / polygon.npoints, res[1] / polygon.npoints};
    }
    //split into two functions due to the large usage of CalculateCenterOfGravity() for polygons -> Polygon as a parameter is easier
    //just realized that this is not an ample way of calculating polygon centers. Visual center is needed instead. GDFGHIUHI!!!
    public static float[] CalculateCenterOfGravityForFloatArray(float[][] arr){
        float[] res=new float[2];
        //average of points
        for (int i = 0; i < arr[0].length; i++) {
            res[0]+=arr[0][i];
            res[1]+=arr[1][i];
        }
        return new float[]{res[0] / arr[0].length, res[1] / arr[0].length};
    }
    public static void CheckCollision(int[] point, Polygon TargetPolygon){
        //
        if(PolygonHolder.shapes.size()!=0) {
            for (int i = 0; i < TargetPolygon.npoints - 1; i++) {
                if (((point[0] < TargetPolygon.xpoints[i] && point[0] > TargetPolygon.xpoints[i + 1]) || (point[0] > TargetPolygon.xpoints[i] && point[0] < TargetPolygon.xpoints[i + 1]))
                        && ((point[0] < TargetPolygon.ypoints[i] && point[1] > TargetPolygon.ypoints[i + 1]) || (point[1] > TargetPolygon.ypoints[i] && point[1] < TargetPolygon.ypoints[i + 1]))) {
                    System.out.println("bruh!");
                }
            }
        }
    }
    public static void IsPolygonConcave(Polygon polygon) {
        boolean isPolygonConcave=false;
        ArrayList<Integer[]> ConcaveEdgePoints = new ArrayList<>();
        if (polygon.npoints == 3) {
            PolygonHolder.ConcaveHandler.add(null); //if the polygon only has 3 points, it can not be defined as convex or concave.
            return;
        }
        int Direction = (polygon.xpoints[0] - polygon.xpoints[1] < 0) ? -1 : 1;
        //any other polygon is concave, if the interior angle between any adjacent edges is more than 180deg
        //the way i do this is by checking the dot product between the previous edge and the normal of the angle between next edge, so as to detect if the angle is more than 90deg (dotp<0)
        float[] MinusOneEdgeVector;
        for (int i = 1; i < polygon.npoints - 1; i++) {
            MinusOneEdgeVector = Direction == 1 ? new float[]{polygon.xpoints[i] - polygon.xpoints[i - 1], polygon.ypoints[i] - polygon.ypoints[i - 1]}//could have used Vector<> but this might be more comprehensive
                    : new float[]{polygon.xpoints[i] - polygon.xpoints[i + 1], polygon.ypoints[i] - polygon.ypoints[i + 1]};
            if(ConcaveDebug) {
                //this doesn't work.
                System.out.println(MathOperations.VectorDotProduct(MinusOneEdgeVector, new float[]{
                        (float) ((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90))),
                        (float) ((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90)))
                }));
                //this maybe works.
                MainLoop.g.draw(new Polygon(new int[]{polygon.xpoints[i + 1], (polygon.xpoints[i + 1]) + 4, (int) (Math.round((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90))) + polygon.xpoints[i + 1]) + 3,
                (int) Math.round((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90)) + polygon.xpoints[i + 1])},
                new int[]{polygon.ypoints[i + 1], polygon.ypoints[i + 1], (int) Math.round((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90))) + polygon.ypoints[i + 1],
                (int) Math.round((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90))) + polygon.ypoints[i + 1]}, 4));
            }
            //this works.
            if (MathOperations.VectorDotProduct(MinusOneEdgeVector, new float[]{
                    (float) ((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90))),
                    (float) ((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.AnglesToRadians(90)))}) < 0) {
                ConcaveEdgePoints.add(new Integer[]{i-1, i, i+1});
                isPolygonConcave=true;
            }
        }
        //final check between the last and first edge. It isn't very elegant and could've probably been done with a modulo operator in the code block above instead, but i felt lazy.
        if(MathOperations.VectorDotProduct(new float[]{
                (float) ((polygon.xpoints[polygon.npoints - 2] - polygon.xpoints[polygon.npoints - 1]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[polygon.npoints - 2] - polygon.ypoints[polygon.npoints - 1]) * Math.sin(MathOperations.AnglesToRadians(90))),
                (float) ((polygon.ypoints[polygon.npoints - 2] - polygon.ypoints[polygon.npoints - 1]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[polygon.npoints - 2] - polygon.xpoints[polygon.npoints - 1]) * Math.sin(MathOperations.AnglesToRadians(90)))}, new float[]{
                (float) ((polygon.xpoints[polygon.npoints - 1] - polygon.xpoints[0]) * Math.cos(MathOperations.AnglesToRadians(90)) - (polygon.ypoints[polygon.npoints - 1] - polygon.ypoints[0]) * Math.sin(MathOperations.AnglesToRadians(90))),
                (float) ((polygon.ypoints[polygon.npoints - 1] - polygon.ypoints[0]) * Math.cos(MathOperations.AnglesToRadians(90)) + (polygon.xpoints[polygon.npoints - 1] - polygon.xpoints[0]) * Math.sin(MathOperations.AnglesToRadians(90)))}) < 0){
            isPolygonConcave=true;
            ConcaveEdgePoints.add(new Integer[]{polygon.npoints-2, polygon.npoints-1, 0});
        }
        PolygonHolder.ConcaveHandler.add(isPolygonConcave ? new Object[]{true, ConcaveEdgePoints} : new Object[]{false});
    }
    /*
    the algorithm used in this program to check collisions between convex polygons is called Separating Axes Theorem. If you have two convex polygons,
    their collision state can be determined by their projected "shadows" when rotated to line with the x and y-axis respectively.
     */
    public static boolean CollisionCheckConvex(Polygon p1, Polygon p2) {
        return !(SeperatingAxisCheck(p1, p2) && SeperatingAxisCheck(p2, p1));
    }
    private static boolean SeperatingAxisCheck(Polygon p1, Polygon p2) {
        for (int i = 0; i < p1.npoints; i++) {
            //using infinities here instead of double.MAX_VALUE for mathematical clarity. their memory performance is the same. (due to hexadecimal format, infinity = 7FF0000000000000)
            double[] projectionBounds = new double[]{Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
            int[] axes=new int[]{p1.ypoints[i]-p1.ypoints[(i + 1) % p1.npoints], p1.xpoints[(i + 1) % p1.npoints]-p1.xpoints[i]};
            // Project all vertices of p2 onto the axis
            for (int j = 0; j < p1.npoints; j++) {
                //max and min bounds (projectionBounds[0-1]). axis normalization also happens here
                projectionBounds[0] = Math.min(projectionBounds[0], (axes[0] / MathOperations.LengthInt(axes)) * p1.xpoints[j] + (axes[1] / MathOperations.LengthInt(axes)) * p1.ypoints[j]);
                projectionBounds[1] = Math.max(projectionBounds[1], (axes[0] / MathOperations.LengthInt(axes)) * p1.xpoints[j] + (axes[1] / MathOperations.LengthInt(axes)) * p1.ypoints[j]);
            }
            // Project all vertices of p2 onto the axis
            for (int j = 0; j < p2.npoints; j++) {
                //max and min bounds (projectionBounds[2-3]). axis normalization also happens here
                projectionBounds[2] = Math.min(projectionBounds[2], (axes[0] / MathOperations.LengthInt(axes)) * p2.xpoints[j] + (axes[1] / MathOperations.LengthInt(axes) * p2.ypoints[j]));
                projectionBounds[3] = Math.max(projectionBounds[3], (axes[0] / MathOperations.LengthInt(axes)) * p2.xpoints[j] + (axes[1] / MathOperations.LengthInt(axes) * p2.ypoints[j]));
            }
            //check projection overlaps
            if (!(projectionBounds[3] >= projectionBounds[0] && projectionBounds[1] >= projectionBounds[2])) {
                return true;
            }
        }
        return false;
    }
}


package me.urkkiz.Physics;

import me.urkkiz.MainWindow.Init;
import me.urkkiz.MainWindow.MainLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.Transform;


import static me.urkkiz.MainWindow.Init.frame;
import static me.urkkiz.MainWindow.MainLoop.PseudoCenters;
import static me.urkkiz.MainWindow.MainLoop.g;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsLoop {
    public static float Degrees = 0;
    public static boolean ConcaveDebug=false;
    public static float[] Movement = new float[2];
    public static int[] PrevMousePosition=new int[]{MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x,MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y};
    public static ArrayList<Polygon> Collisions=new ArrayList<>();

    public static void physicsLoop() {
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            if(Init.IsMouseDown&&!Init.GeneralInfo.isVisible())MouseDragMove(i);
            if (ConcaveDebug) IsPolygonConcave(PolygonHolder.shapes.get(i));
            if(Init.PolygonsSelected.contains(i)) {
                if(Movement[0]!=0||Movement[1]!=0){
                    Transform.MovePolygon(i, Movement[0]*(MainLoop.Period/18f), Movement[1] * (MainLoop.Period)/18f);
                    PseudoCenters.set(i,CalculatePseudoCenter(PolygonHolder.shapes.get(i)));
                }
                if(Degrees !=0){
                    Transform.RotatePolygon(i, Degrees*(MainLoop.Period/18f), CalculatePseudoCenter(PolygonHolder.shapes.get(i)));
                    PseudoCenters.set(i,CalculatePseudoCenter(PolygonHolder.shapes.get(i)));
                }
            }
            CheckAllCollisions(i);
            Init.AmountOfCollisions.setText(!Collisions.isEmpty() ?"Amount of collisions:"+Collisions.size():"Amount of collisions: 0");
        }
        PrevMousePosition=new int[]{MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x,MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y};
    }
    public static void MouseDragMove(int i){
        Transform.MovePolygon(i,(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x)-PrevMousePosition[0],(MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y)-PrevMousePosition[1]);
    }
    public static float[] CalculatePseudoCenter(Polygon polygon){
        float[] res=new float[2];
        //average of points
        for (int i = 0; i < polygon.npoints; i++) {
            res[0]+=polygon.xpoints[i];
            res[1]+=polygon.ypoints[i];
        }
        return new float[]{res[0] / polygon.npoints, res[1] / polygon.npoints};
    }
    //split into two functions due to the large usage of CalculateCenterOfGravity() for polygons -> Polygon as a parameter is easier
    //just realized that this is not an ample way of calculating polygon centers. Centroid is needed instead. GDFGHIUHI!!!
    public static float[] CalculateCenterOfGravityForFloatMatrix(float[][] arr){
        float[] res=new float[2];
        //average of points
        for (int i = 0; i < arr[0].length; i++) {
            res[0]+=arr[0][i];
            res[1]+=arr[1][i];
        }
        return new float[]{res[0] / arr[0].length, res[1] / arr[0].length};
    }
    public static void CheckAllCollisions(int i){
        int AmountOfCollision=Collisions.size();
        if((boolean)PolygonHolder.ConcaveHandler.get(i)[0]){
            for (Polygon p2:PolygonHolder.shapes) {
                if(PolygonHolder.shapes.get(i)!=p2){
                    boolean collision=CollisionCheckConcave(PolygonHolder.shapes.get(i),p2);
                    if(collision&&!Collisions.contains(PolygonHolder.shapes.get(i)))Collisions.add(PolygonHolder.shapes.get(i));
                    else if(!collision)Collisions.remove(PolygonHolder.shapes.get(i));
                }
            }
        }
        if(!(boolean)PolygonHolder.ConcaveHandler.get(i)[0]){
            for (Polygon p2:PolygonHolder.shapes) {
                if(PolygonHolder.shapes.get(i)!=p2&&!(boolean)PolygonHolder.ConcaveHandler.get(PolygonHolder.shapes.indexOf(p2))[0]){
                    boolean collision=CollisionCheckConvex(PolygonHolder.shapes.get(i),p2);
                    if(collision&&!Collisions.contains(PolygonHolder.shapes.get(i)))Collisions.add(PolygonHolder.shapes.get(i));
                    else if(!collision)Collisions.remove(PolygonHolder.shapes.get(i));
                }else if ((boolean)PolygonHolder.ConcaveHandler.get(PolygonHolder.shapes.indexOf(p2))[0]){
                    boolean collision=CollisionCheckConcave(PolygonHolder.shapes.get(i),p2);
                    if(collision&&!Collisions.contains(PolygonHolder.shapes.get(i)))Collisions.add(PolygonHolder.shapes.get(i));
                    else if(!collision)Collisions.remove(PolygonHolder.shapes.get(i));
                }
            }
        }
        if(AmountOfCollision-Collisions.size()==2)Collisions.remove(Collisions.size()-1);
    }

    //I do not dare to question the very gears of the following function; it is a miracle it works in the first place, and miracles are not to be tampered with.
    public static void IsPolygonConcave(Polygon polygon) {
        boolean isPolygonConcave=false;
        ArrayList<Integer[]> ConcaveEdgePoints = new ArrayList<>();
        if (polygon.npoints == 3) {
            PolygonHolder.ConcaveHandler.add(new Object[]{true}); //if the polygon only has 3 points, it can not be defined as convex or concave. However, for collision detection purposes, it is listed as concave.
            return;
        }
        int Direction = (polygon.xpoints[0] - polygon.xpoints[1] < 0) ? -1 : 1;
        //any other polygon is concave, if the interior angle between any adjacent edges is more than 180deg
        //the way this is done by checking the dot product between the previous edge and the normal of the angle between next edge to detect if the angle is more than 90deg (dotp<0)
        float[] MinusOneEdgeVector;
        for (int i = 1; i < polygon.npoints - 1; i++) {
            MinusOneEdgeVector = Direction == 1 ? new float[]{polygon.xpoints[i] - polygon.xpoints[i - 1], polygon.ypoints[i] - polygon.ypoints[i - 1]}//could have used Vector<> but this might be more comprehensive
                    : new float[]{polygon.xpoints[i] - polygon.xpoints[i + 1], polygon.ypoints[i] - polygon.ypoints[i + 1]};
            if(ConcaveDebug) {
                System.out.println(isPolygonConcave);
                System.out.println(MathOperations.VectorDotProduct(MinusOneEdgeVector, new float[]{
                        (float) ((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90))),
                        (float) ((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90)))
                }));
                g.fill(new Polygon(new int[]{polygon.xpoints[i + 1], (polygon.xpoints[i + 1]) + 4, (int) (Math.round((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90))) + polygon.xpoints[i + 1]) + 3,
                (int) Math.round((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90)) + polygon.xpoints[i + 1])},
                new int[]{polygon.ypoints[i + 1], polygon.ypoints[i + 1], (int) Math.round((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90))) + polygon.ypoints[i + 1],
                (int) Math.round((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90))) + polygon.ypoints[i + 1]}, 4));
            }
            if (MathOperations.VectorDotProduct(MinusOneEdgeVector, new float[]{
                    (float) ((polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90))),
                    (float) ((polygon.ypoints[i] - polygon.ypoints[i + 1]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[i] - polygon.xpoints[i + 1]) * Math.sin(MathOperations.DegreesToRadians(90)))}) < 0) {
                ConcaveEdgePoints.add(new Integer[]{i-1, i, i+1});
                isPolygonConcave=true;
            }
        }
        //final check between the last and first edge. It isn't very elegant and could've probably been done with a modulo operator in the code block above instead, but i felt lazy.
        if(MathOperations.VectorDotProduct(new float[]{
                (float) ((polygon.xpoints[polygon.npoints - 2] - polygon.xpoints[polygon.npoints - 1]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[polygon.npoints - 2] - polygon.ypoints[polygon.npoints - 1]) * Math.sin(MathOperations.DegreesToRadians(90))),
                (float) ((polygon.ypoints[polygon.npoints - 2] - polygon.ypoints[polygon.npoints - 1]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[polygon.npoints - 2] - polygon.xpoints[polygon.npoints - 1]) * Math.sin(MathOperations.DegreesToRadians(90)))}, new float[]{
                (float) ((polygon.xpoints[polygon.npoints - 1] - polygon.xpoints[0]) * Math.cos(MathOperations.DegreesToRadians(90)) - (polygon.ypoints[polygon.npoints - 1] - polygon.ypoints[0]) * Math.sin(MathOperations.DegreesToRadians(90))),
                (float) ((polygon.ypoints[polygon.npoints - 1] - polygon.ypoints[0]) * Math.cos(MathOperations.DegreesToRadians(90)) + (polygon.xpoints[polygon.npoints - 1] - polygon.xpoints[0]) * Math.sin(MathOperations.DegreesToRadians(90)))}) < 0){
            isPolygonConcave=true;
            ConcaveEdgePoints.add(new Integer[]{polygon.npoints-2, polygon.npoints-1, 0});
        }
        PolygonHolder.ConcaveHandler.add(isPolygonConcave ? new Object[]{true, ConcaveEdgePoints} : new Object[]{false});
    }
    /*
    the algorithm used in this program to check collisions between convex polygons is called Separating Axes Theorem. If you have two convex polygons,
    their collision state can be determined by the overlap of their projected "shadows" when rotated to line with the x and y-axis respectively.
     */
    public static boolean CollisionCheckConvex(Polygon p1, Polygon p2) {
        return !(SeperatingAxisCheck(p1, p2) && SeperatingAxisCheck(p2, p1));
    }
    private static boolean SeperatingAxisCheck(Polygon p1, Polygon p2) {
        for (int i = 0; i < p1.npoints; i++) {
            //using infinities here instead of double.MAX_VALUE for mathematical clarity, their memory performance is the same. (due to hexadecimal format, infinity = 7FF0000000000000)
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
    public static boolean CollisionCheckConcave(Polygon p1, Polygon p2){
        //also works for convex polygons, but why would you want to do that? this collision detection algorithm is much more resource-intensive.
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < p1.npoints; j++) {
                if(CheckIfPointIsInsidePolygon(new int[]{p1.xpoints[j], p1.ypoints[j]},p2)){
                    //Collisions.set(PolygonHolder.shapes.indexOf(i==0?p1:p2),true);
                    //note to self: when calculating point polygon depth, MasterIndex must be that of p1
                    //System.out.println(MathOperations.GetPenetrationDepthOfPointOnPolygon(new int[]{p1.xpoints[j], p1.ypoints[j]},PolygonHolder.shapes.indexOf(p1),p2));
                    return true;
                }
            }
            Polygon temp = p1;
            p1=p2;
            p2=temp;
        }
        return false;
        /*
        the problem with this algorithm is, for example, if you have two very long triangles perpendicular to each other
        intersecting each other at their visual center, this will not detect a collision, since none of the points of either polygons are inside the other polygon.
        in physics simulation, this fortunately only happens with objects with a very high velocity / with a very low fps. Continuous collision detection resolves these issues (not discrete)
         */
    }
    /*
    this next method for checking if a point is inside a polygon is called winding number algorithm aka nonzero-rule algorithm
    how it works is if the winding number which is calculated here is more than zero, it means that the point is inside the polygon.
    the sign of a winding number is determined by the direction in which the edges wind/wrap around the point.
     */
    //the reason for this function is to check if a point is inside a concave polygon
    public static boolean CheckIfPointIsInsidePolygon(int[] p, Polygon polygon) {
        boolean inside = false;
        for (int i = 0, j = polygon.npoints - 1; i < polygon.npoints; j = i++) {
            if (((polygon.ypoints[i] > p[1]) != (polygon.ypoints[j] > p[1])) &&
                    (p[0] < (polygon.xpoints[j] - polygon.xpoints[i]) * (p[1] - polygon.ypoints[i]) / (polygon.ypoints[j]-polygon.ypoints[i]) + polygon.xpoints[i])) {
                inside = !inside;
            }
        }
        return inside;
    }
}


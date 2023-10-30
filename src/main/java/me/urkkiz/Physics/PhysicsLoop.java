package me.urkkiz.Physics;

import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Calendar;

public class PhysicsLoop {
    public static float Velocity=0;
    public static int test=1;
    public static void physicsLoop(){
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            CalculateGravity();
            Transform.RotatePolygon(i, 5f,CalculateCenterOfGravity(PolygonHolder.shapes.get(i)));
            Transform.MovePolygon(PolygonHolder.shapes.get(i), 0, Velocity - PolygonHolder.shapes.get(i).ypoints[0]);
        }
    }
    public static void CalculateGravity(){
        Velocity=((int) MathOperations.Clamp((0 + (GravitationalConstant*(TimeManager.MasterTime*TimeManager.MasterTime))), 0, 420));
    }
    public static int[] CalculateCenterOfGravity(Polygon polygon){

        int[] res=new int[2];
        int i=0;
        //average of points
        for (i = 0; i < polygon.npoints; i++) {
            res[0]+=polygon.xpoints[i];
            res[1]+=polygon.ypoints[i];
        }
        return new int[]{res[0] / polygon.npoints, res[1] / polygon.npoints};
    }
}


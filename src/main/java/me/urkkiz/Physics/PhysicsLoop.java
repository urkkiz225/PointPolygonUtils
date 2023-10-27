package me.urkkiz.Physics;

import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class PhysicsLoop {
    public static float Velocity=0;
    public static int test=1;
    public static void physicsLoop(){
        test++;
        CalculateGravity();
        if(test%10000==0){Transform.RotatePolygon(PolygonHolder.FinalPolygons.get(0), (float) (Math.PI/6),CalculateCenterOfGravity(PolygonHolder.shapes.get(0)));System.out.println(test);}

        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            //Transform.MovePolygon(PolygonHolder.shapes.get(i), 0, Velocity - PolygonHolder.shapes.get(0).ypoints[0]);
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


package me.urkkiz.Physics;

import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;

public class PhysicsLoop {
    public static float Velocity=0;
    public static void physicsLoop(){
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            CalculateGravity();
            //Transform.MovePolygon(PolygonHolder.shapes.get(i), 0, Velocity - PolygonHolder.shapes.get(i).ypoints[0]);
            Transform.RotatePolygon(i, 0.5f,CalculateCenterOfGravity(PolygonHolder.shapes.get(i)));
        }
    }
    public static void CalculateGravity(){
        Velocity=((int) MathOperations.Clamp((0 + (GravitationalConstant*(TimeManager.MasterTime*TimeManager.MasterTime))), 0, 420));
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
}


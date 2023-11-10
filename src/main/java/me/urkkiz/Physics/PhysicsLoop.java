package me.urkkiz.Physics;

import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PhysicsLoop {
    public static float Angles=0;
    public static void physicsLoop(){
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
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
    public static void CheckCollision(int[] point, Polygon TargetPolygon){
        //
        if(PolygonHolder.shapes.size()!=0) {
            for (int i = 0; i < TargetPolygon.npoints - 1; i++) {
                //System.out.println("vb");
                /*
                System.out.println((point[0] > TargetPolygon.xpoints[i]) + " " + (point[0] < TargetPolygon.xpoints[i + 1]) + " " +
                        (point[1] > TargetPolygon.ypoints[i]) + " " + (point[1] < TargetPolygon.ypoints[i + 1]));
                 */
                System.out.println(((point[0] < TargetPolygon.xpoints[i] && point[0] > TargetPolygon.xpoints[i + 1]) || (point[0] > TargetPolygon.xpoints[i] && point[0] < TargetPolygon.xpoints[i + 1]))+" : " + ((point[0] < TargetPolygon.ypoints[i] && point[1] > TargetPolygon.ypoints[i + 1]) || (point[1] > TargetPolygon.ypoints[i] && point[1] < TargetPolygon.ypoints[i + 1])));
                if (((point[0] < TargetPolygon.xpoints[i] && point[0] > TargetPolygon.xpoints[i + 1]) || (point[0] > TargetPolygon.xpoints[i] && point[0] < TargetPolygon.xpoints[i + 1]))
                        && ((point[0] < TargetPolygon.ypoints[i] && point[1] > TargetPolygon.ypoints[i + 1]) || (point[1] > TargetPolygon.ypoints[i] && point[1] < TargetPolygon.ypoints[i + 1]))) {
                    System.out.println("bruh!");
                }
            }
        }
    }
}


package me.urkkiz.Physics;

import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import static me.urkkiz.Physics.PhysicalPropertiesGlobal.GravitationalConstant;

import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class PhysicsLoop {
    public static float Velocity=0;
    public static void physicsLoop(){
        CalculateGravity();
        for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
            Transform.MovePolygon(PolygonHolder.shapes.get(i),
                    0,
                    Velocity - PolygonHolder.shapes.get(0).ypoints[0]);
        }
    }
    public static void CalculateGravity(){
        Velocity=((int) MathOperations.Clamp((float) (0 + (9.81*(TimeManager.MasterTime*TimeManager.MasterTime))), 0, 420));
    }
}


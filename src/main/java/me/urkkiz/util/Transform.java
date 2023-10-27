package me.urkkiz.util;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Transform {
    public static void MovePolygon(Polygon polygon, float x, float y){
        int[][] coordinates=new int[][]{polygon.xpoints, polygon.ypoints};/*
        for(int i = 0; i < polygon.npoints; i++) {
            coordinates[0][i] += (int) ((coordinates[0][i]+x)-coordinates[0][i]);
            coordinates[1][i] += (int) ((coordinates[1][i]+y)-coordinates[1][i]);
            //coordinates[1][i]= coordinates[1][i]+Math.round((coordinates[1][i]+y)-coordinates[1][i]);
        }
        */
        polygon.translate((int) ((PhysicsLoop.CalculateCenterOfGravity(polygon)[0] +x)-PhysicsLoop.CalculateCenterOfGravity(polygon)[0]),(int) (PhysicsLoop.CalculateCenterOfGravity(polygon)[1]+y)-PhysicsLoop.CalculateCenterOfGravity(polygon)[1]);
    }
    public static void RotatePolygon(Polygon polygon, float Angles, int[] pivot){
        for(int i = 0; i < polygon.npoints; i++) {
            System.out.println((int)(Math.round(((pivot[0]-PolygonHolder.FinalPolygons.get(0).xpoints[i])*Math.cos(Math.toDegrees(Angles))-((pivot[1]-PolygonHolder.FinalPolygons.get(0).ypoints[i])*Math.sin(Math.toDegrees(Math.toDegrees(Angles))))))+pivot[0]) +", " + ((int)Math.round((pivot[1]-PolygonHolder.FinalPolygons.get(0).ypoints[i])*Math.cos(Math.toDegrees(Angles))+(pivot[0]-PolygonHolder.FinalPolygons.get(0).ypoints[i])*Math.sin(Math.toDegrees(Angles)))+pivot[1]));
            //PolygonHolder.shapes.get(0).xpoints[i]=(int)(Math.round(((pivot[0]-PolygonHolder.FinalPolygons.get(0).xpoints[i])*Math.cos(Angles)-((pivot[1]-PolygonHolder.FinalPolygons.get(0).ypoints[i])*Math.sin(Angles))))+pivot[0]);
            double x = ((((PolygonHolder.FinalPolygons.get(0).xpoints[i]-pivot[0])*Math.cos(Angles))-(((PolygonHolder.FinalPolygons.get(0).ypoints[i]-pivot[1])*Math.sin(Angles)))))+pivot[0];
            double y = (((((PolygonHolder.FinalPolygons.get(0).ypoints[i]-pivot[1])*Math.cos(Angles))+(((PolygonHolder.FinalPolygons.get(0).xpoints[i]-pivot[0])*Math.sin(Angles))))))+pivot[1];
            PolygonHolder.shapes.get(0).xpoints[i]=(int)x;
            PolygonHolder.shapes.get(0).ypoints[i]=(int)y;
        }
    }
    public static void ScalePolygon(Polygon polygon, float x, float y, int[] pivot){
        /*

        Polygon FinalPolygon=PolygonHolder.FinalPolygons.get(0);
        //System.out.println((polygon.xpoints[i]-pivot[0])*Math.cos(Angles)-(polygon.ypoints[i]-pivot[1])*Math.sin(Angles)+pivot[0]+ ", "+((((polygon.ypoints[i]-pivot[1])*Math.cos(Angles))+(polygon.xpoints[i]-pivot[0])*Math.sin(Angles))+pivot[1]));
        polygon.xpoints[i]+=(int)((FinalPolygon.xpoints[i]-pivot[0])*Math.cos(Angles))-(int)((FinalPolygon.ypoints[i]-pivot[1])*Math.sin(Angles));
        System.out.println((int)(25*Math.cos(TimeManager.MasterTime))-(int)(25*Math.sin(TimeManager.MasterTime))+125+"bruh");
        //System.out.println((((int)((FinalPolygon.xpoints[i]-pivot[0])*Math.cos(Angles)))-((int)((FinalPolygon.ypoints[i]-pivot[1])*Math.sin(Angles)))));
        System.out.println(Arrays.toString(polygon.xpoints));
        System.out.println((int)(((FinalPolygon.xpoints[i]-pivot[0])*Math.cos(Angles)))-((int)((FinalPolygon.ypoints[i]-pivot[1])*Math.sin(Angles)))+pivot[0]+", "+ (int)((FinalPolygon.ypoints[i]-pivot[1])*Math.cos(Angles))+(int)((FinalPolygon.xpoints[i]-pivot[0])*Math.sin(Angles))+pivot[1]);
        polygon.ypoints[i]=(int)((FinalPolygon.ypoints[i]-pivot[1])*Math.cos(Angles))+(int)((FinalPolygon.xpoints[i]-pivot[0])*Math.sin(Angles))+pivot[1];
        //System.out.println((int) (((polygon.xpoints[i]-pivot[0])*Math.cos(Angles)+(polygon.ypoints[i]-pivot[1])*Math.sin(Angles))+pivot[0])+", " + (int) (((polygon.ypoints[i]-pivot[1])*Math.cos(Angles)+(polygon.xpoints[i]-pivot[0])*Math.sin(Angles))+pivot[1]));
        //polygon.xpoints[i]=Math.round((float)((-pivot[0]+polygon.xpoints[i])*Math.cos(Angles)-(-pivot[1]+polygon.ypoints[i])*Math.sin(Angles)));
        //polygon.ypoints[i]=Math.round((float)((-pivot[0]+polygon.xpoints[i])*Math.sin(Angles)+(-pivot[1]+polygon.ypoints[i])*Math.cos(Angles)));

         */
    }
}
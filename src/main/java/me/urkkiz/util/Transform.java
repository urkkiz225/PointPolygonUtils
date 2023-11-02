package me.urkkiz.util;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;

public class Transform {
    public static void MovePolygon(Polygon polygon, float x, float y){
        int[][] coordinates=new int[][]{polygon.xpoints, polygon.ypoints};
        // \/- obsolete - JFrame has a translate() method for moving objects. (works nonetheless)
        /*
        for(int i = 0; i < polygon.npoints; i++) {
            coordinates[0][i] += (int) ((coordinates[0][i]+x)-coordinates[0][i]);
            coordinates[1][i] += (int) ((coordinates[1][i]+y)-coordinates[1][i]);
            //coordinates[1][i]= coordinates[1][i]+Math.round((coordinates[1][i]+y)-coordinates[1][i]);
        }
         */
        polygon.translate((int) ((PhysicsLoop.CalculateCenterOfGravity(polygon)[0] +x)-PhysicsLoop.CalculateCenterOfGravity(polygon)[0]), (int) ((int) (PhysicsLoop.CalculateCenterOfGravity(polygon)[1]+y)-PhysicsLoop.CalculateCenterOfGravity(polygon)[1]));
    }
    public static void RotatePolygon(int PolygonIndex, float Angles, float[] pivot){
        double[] xy = new double[2];
        for(int i = 0; i < PolygonHolder.shapes.get(PolygonIndex).npoints; i++) {
            //PolygonHolder.shapes.get(0).xpoints[i]=(int)(Math.round(((pivot[0]-PolygonHolder.FinalPolygons.get(0).xpoints[i])*Math.cos(Angles)-((pivot[1]-PolygonHolder.FinalPolygons.get(0).ypoints[i])*Math.sin(Angles))))+pivot[0]);
            xy[0] = ((((PolygonHolder.FinalPolygons.get(PolygonIndex).xpoints[i]-pivot[0])*Math.cos(Angles))-(((PolygonHolder.FinalPolygons.get(PolygonIndex).ypoints[i]-pivot[1])*Math.sin(Angles)))))+pivot[0];
            xy[1] = (((((PolygonHolder.FinalPolygons.get(PolygonIndex).ypoints[i]-pivot[1])*Math.cos(Angles))+(((PolygonHolder.FinalPolygons.get(PolygonIndex).xpoints[i]-pivot[0])*Math.sin(Angles))))))+pivot[1];
            PolygonHolder.shapes.get(PolygonIndex).xpoints[i]=(int)Math.round(xy[0]);
            PolygonHolder.shapes.get(PolygonIndex).ypoints[i]=(int)Math.round(xy[1]);
        }
        /*
        for(int i = 0; i < PolygonHolder.shapes.get(PolygonIndex).npoints; i++) {
            //optimize later with the outermost point of polygon and distance from pivot
            PolygonHolder.shapes.get(PolygonIndex).xpoints[i]-=(pivot[0]-xy[0]);
            PolygonHolder.shapes.get(PolygonIndex).ypoints[i]-=(pivot[1]-xy[1]);
        }
         */
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
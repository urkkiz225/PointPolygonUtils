package me.urkkiz.util;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.util.Arrays;

public class Transform {
    public static double[][] AccurateCumulativeDoubleOverFlows = new double[3][2];
    //index 0 : position | index 1 : rotation | index 2 : scale (for rounding errors)
    public static void MovePolygon(Polygon polygon, float x, float y){
        int[][] coordinates=new int[][]{polygon.xpoints, polygon.ypoints};
        // \/- obsolete - JFrame has a translate() method for moving objects. (works nonetheless, might be faster so leaving it here)
        /*
        for(int i = 0; i < polygon.npoints; i++) {
            coordinates[0][i] += (int) ((coordinates[0][i]+x)-coordinates[0][i]);
            coordinates[1][i] += (int) ((coordinates[1][i]+y)-coordinates[1][i]);
            //coordinates[1][i]= coordinates[1][i]+Math.round((coordinates[1][i]+y)-coordinates[1][i]);
        }
        */
        polygon.translate((int) ((PhysicsLoop.CalculateCenterOfGravity(polygon)[0] +x)-PhysicsLoop.CalculateCenterOfGravity(polygon)[0]), (int) ((int) (PhysicsLoop.CalculateCenterOfGravity(polygon)[1]+y)-PhysicsLoop.CalculateCenterOfGravity(polygon)[1]));
    }
    public static void RotatePolygon(int PolygonIndex, float Angles, float[] pivot) {
        /*rounding errors must be accounted for, as java awts custom polygons can not be instantiated with double/float accuracy.
          however, over a time period long enough (very long time) the doubles 64-bit accuracy will not be sufficient for accurately rotating polygons, but
          this avoids unnecessary operations of copying the polygon from PolygonHolder.BasePolygons and translating it to CustomPolygon.shapes with accounting for transformations.
        */
        double[] xy = new double[2];
        double[] xyCumulativeOverFlow = new double[2]; //done with cpu optimization in mind; rounding errors can also be accounted for with a one-liner
        for (int i = 0; i < PolygonHolder.shapes.get(PolygonIndex).npoints; i++) {
            xy[0] = ((((PolygonHolder.BasePolygons.get(PolygonIndex).xpoints[i] - pivot[0]) * Math.cos(Angles)) - (((PolygonHolder.BasePolygons.get(PolygonIndex).ypoints[i] - pivot[1]) * Math.sin(Angles))))) + pivot[0];
            xy[1] = (((((PolygonHolder.BasePolygons.get(PolygonIndex).ypoints[i] - pivot[1]) * Math.cos(Angles)) + (((PolygonHolder.BasePolygons.get(PolygonIndex).xpoints[i] - pivot[0]) * Math.sin(Angles)))))) + pivot[1];
            if(Math.abs(Math.round(AccurateCumulativeDoubleOverFlows[1][0])) > 0){
                xyCumulativeOverFlow[0]=Math.round(AccurateCumulativeDoubleOverFlows[1][0]);
                AccurateCumulativeDoubleOverFlows[1][0] = (AccurateCumulativeDoubleOverFlows[1][0]-xyCumulativeOverFlow[0]);
            }
            else AccurateCumulativeDoubleOverFlows[1][0] += xy[0] - (int) (Math.round(xy[0])); //POSSIBLE ERROR HERE!!!1! FIX LATER PLZ!!!!!!!!
            if(Math.abs(Math.round(AccurateCumulativeDoubleOverFlows[1][1])) > 0){
                xyCumulativeOverFlow[1] = Math.round(AccurateCumulativeDoubleOverFlows[1][1]);
                AccurateCumulativeDoubleOverFlows[1][1] = (AccurateCumulativeDoubleOverFlows[1][1]-xyCumulativeOverFlow[1]);
            }
            else AccurateCumulativeDoubleOverFlows[1][1] += xy[1] - (Math.round(xy[1])); //POSSIBLE ERROR HERE!!!1! FIX LATER PLZ!!!!!!!!

            //MathOperations.AccurateCumulativeDoubleOverFlows[1][0] = Math.round(xy[0] - (int) (Math.round(xy[0])) == 1 ? (1 - (xy[0] - (int) (Math.round(xy[0])))) : MathOperations.AccurateCumulativeDoubleOverFlows[1][0] + xy[0] - (int) (Math.round(xy[0])));
            //MathOperations.AccurateCumulativeDoubleOverFlows[1][1] = Math.round(xy[1] - (int) (Math.round(xy[1])) == 1 ? 1 - (xy[1] - (int) (Math.round(xy[1]))) : MathOperations.AccurateCumulativeDoubleOverFlows[1][1] + xy[1] - (int) (Math.round(xy[1])));
            //System.out.println(Arrays.deepToString(AccurateCumulativeDoubleOverFlows));
            // ^ checks if the values of both components are ready for rounding, otherwise adds the result of the iteration to the master sum.
            PolygonHolder.shapes.get(PolygonIndex).xpoints[i] = (int) (Math.round(xy[0])) + (int)xyCumulativeOverFlow[0];
            PolygonHolder.shapes.get(PolygonIndex).ypoints[i] = (int) (Math.round(xy[1])) + (int)xyCumulativeOverFlow[1];
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
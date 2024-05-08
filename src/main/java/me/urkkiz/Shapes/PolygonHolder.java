package me.urkkiz.Shapes;

import me.urkkiz.MainWindow.MainLoop;
import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.util.MathOperations;

import java.awt.*;
import java.util.ArrayList;

public class PolygonHolder {
    public static ArrayList<Polygon> shapes=new ArrayList<>();
    public static ArrayList<Polygon> BasePolygons=new ArrayList<>();
    public static ArrayList<int[]> Bounds=new ArrayList<>();

    public static ArrayList<Object[]> ConcaveHandler=new ArrayList<>();
    public static void PushPolygon(int[] verticesX, int[] verticesY) {
        shapes.add(new Polygon(verticesX,verticesY, verticesX.length));
        MainLoop.PseudoCenters.add(PhysicsLoop.CalculatePseudoCenter(PolygonHolder.shapes.get(PolygonHolder.shapes.size()-1)));
    }
    public static void CompilePolygons(){
        //do not runtime modify BasePolygons
        BasePolygons=shapes;
        CompileBounds();
    }
    public static ArrayList<int[]> TempPolygon=new ArrayList<>();
    public static void CompileBounds(){
        for (Polygon polygon : PolygonHolder.shapes) {
            int[] MDist=new int[2];
            float[] Center = MathOperations.CalculateCentroid(polygon);
            for (int i = 0; i < polygon.npoints; i++) {
                if(polygon.xpoints[i]-Center[0]>MDist[0])MDist[0]=polygon.xpoints[i]-(int)Center[0];
                if(polygon.ypoints[i]-Center[1]>MDist[1])MDist[1]=polygon.ypoints[i]-(int)Center[1];
            }
            PolygonHolder.Bounds.add(MDist);
        }

    }
}

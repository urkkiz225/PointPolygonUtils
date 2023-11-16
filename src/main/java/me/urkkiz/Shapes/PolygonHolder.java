package me.urkkiz.Shapes;

import me.urkkiz.util.MathOperations;

import java.awt.*;
import java.util.ArrayList;

public class PolygonHolder {
    public static ArrayList<Polygon> shapes=new ArrayList<>();
    public static ArrayList<Polygon> BasePolygons=new ArrayList<>();

    public static ArrayList<int[]> Bounds=new ArrayList<>();

    public static ArrayList<Object[]> ConcaveHandler=new ArrayList<>();
    public static void PushPolygon(int[] verticesX, int[] verticesY, int nPoints) {
        shapes.add(new Polygon(verticesX,verticesY, Math.max(verticesX.length,verticesY.length)));
    }
    public static void CompilePolygons(){
        //do not modify BasePolygons
        BasePolygons=shapes;
        MathOperations.CompileBounds();
    }
    public static ArrayList<int[]> TempPolygon=new ArrayList<>();

}

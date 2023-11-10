package me.urkkiz.Shapes;

import me.urkkiz.util.MathOperations;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PolygonHolder {
    public static ArrayList<Polygon> shapes=new ArrayList<>();
    public static ArrayList<Polygon> FinalPolygons=new ArrayList<>();

    public static ArrayList<int[]> Bounds=new ArrayList<>();
    public static void PushPolygon(int[] verticesX, int[] verticesY, int nPoints) {
        shapes.add(new Polygon(verticesX,verticesY, Math.max(verticesX.length,verticesY.length)));
    }
    public static void CompilePolygons(){
        //do not modify finalpolygons
        FinalPolygons=shapes;
        MathOperations.CompileBounds();
        System.out.println(FinalPolygons.size()+", "+shapes.size());
        System.out.println(Arrays.toString(shapes.get(0).xpoints)+", "+Arrays.toString(shapes.get(0).ypoints)+", "+shapes.get(0).npoints);
    }
    public static ArrayList<int[]> TempPolygon=new ArrayList<>();

}

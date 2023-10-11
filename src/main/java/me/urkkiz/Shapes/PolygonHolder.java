package me.urkkiz.Shapes;

import java.awt.*;
import java.util.ArrayList;

public class PolygonHolder {
    public static ArrayList<Polygon> shapes=new ArrayList<>();
    public static void PushPolygon(int[] verticesX, int[] verticesY, int nPoints) {
        shapes.add(new Polygon(verticesX,verticesY, Math.max(verticesX.length,verticesY.length)));
    }
}

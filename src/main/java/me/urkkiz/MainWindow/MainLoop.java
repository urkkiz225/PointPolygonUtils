package me.urkkiz.MainWindow;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainLoop extends Init{
    public static int i=0;
    public static final Graphics2D g =((Graphics2D)DrawPanel.getGraphics());
    public static void Loop(){
        boolean Running=true;
        long frameEndTime=System.nanoTime();

        while(Running){
            TimeManager.MasterTime+=(System.nanoTime()-frameEndTime)*0.000000001f;
            TimeManager.CalcFrameEndTime();
            frameEndTime = System.nanoTime();
            label.setText(String.valueOf(TimeManager.MasterTime));
            //byte[] array = new byte[random.nextInt(4,16)];
            //new Random().nextBytes(array);
            //frame.setTitle("physics simulator epic"+new String(array, StandardCharsets.UTF_8));
            //label.setText(new String(array, StandardCharsets.UTF_8));
            PhysicsLoop.physicsLoop();
            Render();
        }
    }
    public static void Render(){
        synchronized(g) {
            for(Polygon polygon:PolygonHolder.shapes){
                g.setColor(Color.blue);
                g.fillPolygon(polygon);
            }
            g.fillRect(500, (int) MathOperations.Clamp((float) (0 + (9.81*(TimeManager.MasterTime*TimeManager.MasterTime))), 0, 420), 50, 50);
            if(i%2==0){
                DrawPanel.repaint();
                i--;
            }else i++;
        }
    }
}

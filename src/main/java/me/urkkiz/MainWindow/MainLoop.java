package me.urkkiz.MainWindow;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import java.awt.*;
import java.util.*;

public class MainLoop extends Init{
    public static int i=0;
    public static Graphics2D g = ((Graphics2D)DrawPanel.getGraphics());


    public static void Loop(){
        TimerTask MainLoop = new TimerTask(){
            @Override
            public void run() {
                Render();
                long frameEndTime = System.nanoTime();
                TimeManager.MasterTime += 34*0.001f;
                TimeManager.CalcFrameEndTime();
                frameEndTime = System.nanoTime();
                label.setText(String.valueOf(TimeManager.MasterTime));
                //byte[] array = new byte[random.nextInt(4,16)];
                //new Random().nextBytes(array);
                //frame.setTitle("physics simulator epic"+new String(array, StandardCharsets.UTF_8));
                //label.setText(new String(array, StandardCharsets.UTF_8));
                PhysicsLoop.physicsLoop();
            }
        };
        long delay=17;
        long period=34;
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(MainLoop,delay,period);
    }
    public static void Render(){
        DrawPanel.paintAll(g);
        for (Polygon polygon : PolygonHolder.shapes) {
            g.setColor(Color.red);
            g.fill(polygon);
        }
        g.fillRect(500, (int) MathOperations.Clamp((float) (0 + (9.81 * (TimeManager.MasterTime * TimeManager.MasterTime))), 0, 420), 50, 50);
    }
}

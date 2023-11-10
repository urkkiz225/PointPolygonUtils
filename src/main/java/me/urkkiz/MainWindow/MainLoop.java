package me.urkkiz.MainWindow;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.CustomPolygon;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.TimeManager;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class MainLoop extends Init {
    public static Graphics2D g = ((Graphics2D) DrawPanel.getGraphics());


    public static void Loop() {
        TimerTask MainLoop = new TimerTask() {
            @Override
            public void run() {
                Render();
                long frameEndTime = System.nanoTime();
                TimeManager.Timers.replaceAll(aFloat -> aFloat + 34 * 0.001f);
                TimeManager.MasterTime += 34 * 0.001f;
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
        long delay = 17;
        long period = 34;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(MainLoop, delay, period);
    }

    public static void Render() {
        DrawPanel.paintAll(g);
        for (Polygon polygon : PolygonHolder.shapes) {
            g.setColor(Color.red);
            g.fill(polygon);
        }
        //drawing of polygon editing
        if (Init.GeneralInfo.isVisible()) {
            g.draw(new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 5, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 5, 10, 10));
            if (PolygonHolder.TempPolygon.size() != 0) {
                for (int j = 0; j < PolygonHolder.TempPolygon.size() - 1; j++) {
                    PolygonHolder.TempPolygon.set(PolygonHolder.TempPolygon.size() - 1, new int[]{MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y});
                    Line2D TLN = new Line2D.Float(PolygonHolder.TempPolygon.get(j)[0], PolygonHolder.TempPolygon.get(j)[1], PolygonHolder.TempPolygon.get(j + 1)[0], PolygonHolder.TempPolygon.get(j + 1)[1]);
                    Rectangle2D PointIndicator = new Rectangle2D.Float((float) TLN.getX1() - 5, (float) TLN.getY1() - 5, 10, 10);
                    if (j + 2 == PolygonHolder.TempPolygon.size())
                        g.draw((Math.abs(PolygonHolder.TempPolygon.get(j + 1)[0] - PolygonHolder.TempPolygon.get(0)[0]) < 16 && Math.abs(PolygonHolder.TempPolygon.get(j + 1)[1] - PolygonHolder.TempPolygon.get(0)[1]) < 16)
                                ? new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 8, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 8, 16, 16)
                                : new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 5, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 5, 10, 10));
                    g.fill(PointIndicator);
                    g.draw(TLN);
                }
            }
            if (PolygonHolder.TempPolygon.size() > 2 && (Math.abs(PolygonHolder.TempPolygon.get(PolygonHolder.TempPolygon.size() - 2)[0] - PolygonHolder.TempPolygon.get(0)[0]) < 16 && Math.abs(PolygonHolder.TempPolygon.get(PolygonHolder.TempPolygon.size() - 2)[1] - PolygonHolder.TempPolygon.get(0)[1]) < 16)) {
                int[][] Coordinates = new int[2][PolygonHolder.TempPolygon.size()];
                //unoptimized conversion ik
                for (int i = 0; i < PolygonHolder.TempPolygon.size(); i++) {
                    Coordinates[0][i] = PolygonHolder.TempPolygon.get(i)[0];
                    Coordinates[1][i] = PolygonHolder.TempPolygon.get(i)[1];
                }
                PolygonHolder.PushPolygon(Coordinates[0], Coordinates[1], Coordinates[0].length);
                TimeManager.Timers.add(0F);
                PolygonHolder.CompilePolygons();
                PolygonHolder.TempPolygon.clear();
            }
        }
        //i do not know what i have done in this code block

        /*else if (PolygonHolder.TempPolygon.size()==0){
            g.draw(new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x-5,MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().y-5,10,10));
        }else{
            PolygonHolder.TempPolygon.clear();
        }
             */
        g.fillRect(500, (int) MathOperations.Clamp((float) (0 + (9.81 * (TimeManager.MasterTime * TimeManager.MasterTime))), 0, 420), 50, 50);
    }
}

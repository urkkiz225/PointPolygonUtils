package me.urkkiz.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.urkkiz.Actions.WindowRuntime;
import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.*;

public class Init extends JFrame{
    public static JFrame frame = new JFrame();
    public static Canvas DrawPanel=new Canvas();
    public static JLabel label = new JLabel("that scouts a spy!");

    public static JLabel GeneralInfo = new JLabel("Entered polygon creation mode");
    public static MouseListener ML;

    public static boolean IsMouseDown=false;


    public static void main(String[] args){
        InitWindow();
    }
    public static void InitWindow(){
        DrawPanel.addKeyListener(WindowRuntime.listener);
        GeneralInfo.setBounds(frame.getWidth(),frame.getHeight(),600,100);
        GeneralInfo.setFont(new Font(null, Font.PLAIN,24));
        GeneralInfo.setLocation(1280-600, 340);
        GeneralInfo.setOpaque(true);
        label.setBounds(frame.getWidth(),frame.getHeight(),300,100);
        label.setFont(new Font(null,Font.BOLD,25));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("physics simulator epic");
        frame.setSize(1280,480);
        DrawPanel.setSize(1280,480);
        frame.add(GeneralInfo);
        frame.add(label);
        frame.add(DrawPanel);
        frame.setLayout(null);
        frame.setVisible(true);
        Graphics2D g2d=(Graphics2D) frame.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        //PolygonHolder.PushPolygon(new int[]{160,200,150,300},new int[]{0,0,150,300},4);
        frame.repaint();
        MainLoop.g=(Graphics2D)DrawPanel.getGraphics();
        //PolygonHolder.CompilePolygons();
        MainLoop.Loop();
        ML=new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(PolygonHolder.shapes.size()!=0)System.out.println(PhysicsLoop.CheckIfPointIsInsidePolygon(new int[]{e.getX(), e.getY()},PolygonHolder.shapes.get(0)));
                IsMouseDown=true;
                if(GeneralInfo.isVisible()){
                    PolygonHolder.TempPolygon.add(new int[]{e.getX(), e.getY()});
                }
                if(!GeneralInfo.isVisible()){
                    int[] PrevPosition = new int[]{e.getX(), e.getY()};
                    for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
                        if ((PhysicsLoop.CalculateCenterOfGravity(PolygonHolder.shapes.get(i))[0] - e.getX() < PolygonHolder.Bounds.get(i)[0] * 0.5f)
                                && (PhysicsLoop.CalculateCenterOfGravity(PolygonHolder.shapes.get(i))[1] - e.getY() < PolygonHolder.Bounds.get(i)[1] * 0.5f)) {
                            for (int j = 0; j < PolygonHolder.shapes.get(i).npoints; j++) {
                                PolygonHolder.shapes.get(i).xpoints[j] += (e.getX() - PrevPosition[0]);
                                PolygonHolder.shapes.get(i).ypoints[j] += (e.getY() - PrevPosition[1]);
                                PrevPosition = new int[]{e.getX(), e.getY()};
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                IsMouseDown=false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        DrawPanel.addMouseListener(ML);
    }
}

package me.urkkiz.MainWindow;

import javax.swing.*;
import java.awt.*;
import me.urkkiz.Shapes.*;

public class Init {
    public static float t=0;
    static JFrame frame = new JFrame();
    static Canvas DrawPanel=new Canvas();
    static JLabel label = new JLabel("that scouts a spy!");
    private static CustomPolygon customShape;


    public static void main(String[] args){
        InitWindow();
    }
    public static void InitWindow(){
        label.setBounds(frame.getWidth(),frame.getHeight(),300,100);
        label.setFont(new Font(null,Font.BOLD,25));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("physics simulator epic");
        frame.setSize(1280,480);
        frame.add(label);
        DrawPanel.setSize(1280,480);
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

        PolygonHolder.PushPolygon(new int[]{150,100,100,150},new int[]{200,200,250,250},4);
        PolygonHolder.CompilePolygons();
        MainLoop.Loop();
    }
}

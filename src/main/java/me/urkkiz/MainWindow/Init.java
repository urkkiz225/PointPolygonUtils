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
        frame.repaint();
        PolygonHolder.PushPolygon(new int[]{160,200,150,300},new int[]{0,0,150,300},2);
        MainLoop.Loop();
    }
}

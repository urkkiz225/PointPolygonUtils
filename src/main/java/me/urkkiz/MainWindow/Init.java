package me.urkkiz.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import me.urkkiz.Actions.WindowRuntime;
import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

public class Init extends JFrame{
    public static JFrame frame = new JFrame();
    public static Canvas DrawPanel=new Canvas();
    public static JLabel label = new JLabel("");
    public static ArrayList<Integer> PolygonsSelected=new ArrayList<>();

    public static JLabel GeneralInfo = new JLabel("Polygon creation mode active");
    public static JLabel SelectedPolygon = new JLabel("Selected Polygon: None");
    public static JLabel AmountOfCollisions = new JLabel("Amount of collisions: 0");
    public static MouseListener ML;

    public static boolean IsMouseDown=false;


    public static void main(String[] args){
        InitWindow();
    }
    public static void InitWindow(){
        Random random = new Random();
        DrawPanel.addKeyListener(WindowRuntime.listener);
        DrawPanel.addMouseWheelListener(WindowRuntime.mListener);
        GeneralInfo.setBounds(frame.getWidth(),frame.getHeight(),450,60);
        GeneralInfo.setFont(new Font(null, Font.PLAIN,18));
        GeneralInfo.setLocation(Math.abs(Toolkit.getDefaultToolkit().getScreenSize().width-500), Math.abs(Toolkit.getDefaultToolkit().getScreenSize().height-100)-140);
        GeneralInfo.setOpaque(true);
        SelectedPolygon.setBounds(frame.getWidth(),frame.getHeight(),450,60);
        SelectedPolygon.setFont(new Font(null, Font.PLAIN,18));
        SelectedPolygon.setLocation(Math.abs(Toolkit.getDefaultToolkit().getScreenSize().width-500), Math.abs(Toolkit.getDefaultToolkit().getScreenSize().height-100)-200);
        SelectedPolygon.setOpaque(true);
        AmountOfCollisions.setBounds(frame.getWidth(),frame.getHeight(),450,60);
        AmountOfCollisions.setFont(new Font(null, Font.PLAIN,18));
        AmountOfCollisions.setLocation(Math.abs(Toolkit.getDefaultToolkit().getScreenSize().width-500), Math.abs(Toolkit.getDefaultToolkit().getScreenSize().height-100)-260);
        AmountOfCollisions.setOpaque(true);
        label.setBounds(frame.getWidth(),frame.getHeight(),300,100);
        label.setFont(new Font(null,Font.BOLD,25));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(new String[]{"polygon point epic application","\"Polygon polygon, polygon!\" -Polygon", "The usurper", "Do these two lines really intersect?", "I love concave polygons!!1!!","Minecraft 1.8.9", "ULTRAKILL", "bind o \"explode\"","the idiocy", "nice day today", "helltale", "slowcrunch"}[random.nextInt(0,12)]);
        frame.setSize(Math.abs(Toolkit.getDefaultToolkit().getScreenSize().width-100),Math.abs(Toolkit.getDefaultToolkit().getScreenSize().height-100));
        DrawPanel.setSize(frame.getWidth(),frame.getHeight());
        frame.add(GeneralInfo);
        frame.add(SelectedPolygon);
        frame.add(AmountOfCollisions);
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
        frame.repaint();
        MainLoop.g=(Graphics2D)DrawPanel.getGraphics();
        MainLoop.Loop();
        ML=new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(GeneralInfo.isVisible()){
                    PolygonHolder.TempPolygon.add(new int[]{e.getX(), e.getY()});
                }
                if(!GeneralInfo.isVisible()){
                    int[] PrevPosition = new int[]{e.getX(), e.getY()};
                    boolean hits=false;
                    for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
                        if(PhysicsLoop.CheckIfPointIsInsidePolygon(new int[]{e.getX(), e.getY()}, PolygonHolder.shapes.get(i))){
                            hits=true;
                            if(!PolygonsSelected.contains(i)&&WindowRuntime.IsShiftDown) PolygonsSelected.add(i);
                            else{
                                PolygonsSelected.clear();
                                PolygonsSelected.add(i);
                            }
                            SelectedPolygon.setText(PolygonsSelected.size()==1 ? "Selected Polygon: " + i : "Selected Polygons: " + PolygonsSelected);
                            for (int j = 0; j < PolygonHolder.shapes.get(i).npoints; j++) {
                                PolygonHolder.shapes.get(i).xpoints[j] += (e.getX() - PrevPosition[0]);
                                PolygonHolder.shapes.get(i).ypoints[j] += (e.getY() - PrevPosition[1]);
                                PrevPosition = new int[]{e.getX(), e.getY()};
                            }
                        }
                    }if(!hits){
                        PolygonsSelected.clear();
                        SelectedPolygon.setText("Selected Polygons: None");
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                IsMouseDown=true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                IsMouseDown=false;
            }
            //these method declarations can not be deleted.
            @Override
            public void mouseEntered(MouseEvent e) {
                /*
                In a galaxy far, far away...
                It is a period of civil war.
                Rebel spaceships, striking
                from a hidden base, have won
                their first victory against
                the evil Galactic Empire.

                During the battle, Rebel
                spies managed to steal secret
                plans to the Empire's
                ultimate weapon, the DEATH
                STAR, an armored space
                station with enough power to
                destroy an entire planet.

                Pursued by the Empire's
                sinister agents, Princess
                Leia races home aboard her
                starship, custodian of the
                stolen plans that can save
                her people and restore
                freedom to the galaxy....
                 */
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //RAA! RAATATATATAA! WA - A - A - A!
            }
        };
        DrawPanel.addMouseListener(ML);
    }

}

package me.urkkiz.Actions;

import me.urkkiz.MainWindow.Init;
import me.urkkiz.MainWindow.MainLoop;
import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

public class WindowRuntime{
    public static boolean IsShiftDown=false;
    public static float scale=1;
    //this is a prime example of a forgotten class...
    public static void GeneralInformation(boolean Toggle, String SetText){
        Init.GeneralInfo.setVisible(Toggle);
        if(SetText.length()!=0) Init.GeneralInfo.setText(SetText);
    }
    //usage of method is self-evident.
    public static KeyListener listener=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
         //i know this is empty, but an override method implementation for every master method is required for KeyListener -> can't delete
        }
        @Override
        public void keyPressed(KeyEvent e) {
            //cases r prolly better but this will suffice
            Random random =new Random();
            if(e.getKeyCode()==KeyEvent.VK_RIGHT) PhysicsLoop.Degrees =0.05f;
            if(e.getKeyCode()==KeyEvent.VK_LEFT) PhysicsLoop.Degrees =-0.05f;
            if(e.getKeyCode()==KeyEvent.VK_W) PhysicsLoop.Movement[1]=-5;
            if(e.getKeyCode()==KeyEvent.VK_S) PhysicsLoop.Movement[1]=5f;
            if(e.getKeyCode()==KeyEvent.VK_A) PhysicsLoop.Movement[0]=-5f;
            if(e.getKeyCode()==KeyEvent.VK_D) PhysicsLoop.Movement[0]=5;
            if (e.getKeyCode()==KeyEvent.VK_G) GeneralInformation(!Init.GeneralInfo.isVisible(), Init.GeneralInfo.getText());
            if(e.getKeyCode()==KeyEvent.VK_SHIFT) IsShiftDown=true;
            if(e.getKeyCode()==KeyEvent.VK_A&&e.isControlDown()){
                if(MainLoop.PolygonsSelected.size()!=PolygonHolder.shapes.size()) {
                    MainLoop.PolygonsSelected.clear();
                    System.out.println("All polygons selected.");
                    for (int i = 0; i < PolygonHolder.shapes.size(); i++) {
                        MainLoop.PolygonsSelected.add(i);
                        MainLoop.SelectedPolygon.setText("Selected Polygons: " + MainLoop.PolygonsSelected);
                    }
                }else {
                    MainLoop.PolygonsSelected.clear();
                    System.out.println("All polygons deselected.");
                    MainLoop.SelectedPolygon.setText("Selected Polygons: " + "None");
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_P) {
                try {
                    System.out.print("\r");
                    PhysicsLoop.ConcaveDebug=false;
                    MainLoop.DebugLogEnabled=false;
                    MainLoop.InputCases();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT) PhysicsLoop.Degrees =0.0f;
            if(e.getKeyCode()==KeyEvent.VK_W) PhysicsLoop.Movement[1]=0;
            if(e.getKeyCode()==KeyEvent.VK_S) PhysicsLoop.Movement[1]=0;
            if(e.getKeyCode()==KeyEvent.VK_A) PhysicsLoop.Movement[0]=0;
            if(e.getKeyCode()==KeyEvent.VK_D) PhysicsLoop.Movement[0]=0;
            if(e.getKeyCode()==KeyEvent.VK_SHIFT) IsShiftDown=false;
        }
    };
    public static MouseWheelListener mListener=new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            MainLoop.g.scale(1+(e.getPreciseWheelRotation()*0.01f),1+(e.getPreciseWheelRotation()*0.01f));
            scale = (float) (1+(e.getPreciseWheelRotation()*0.01f));
            ((Graphics2D)MainLoop.DrawPanel.getGraphics()).scale(1+(e.getPreciseWheelRotation()*0.01f),1+(e.getPreciseWheelRotation()*0.01f));
            //Init.DrawPanel.setBounds((int) (Init.WIDTH*(1+(e.getPreciseWheelRotation())*0.01f)), (int) (Init.HEIGHT*(1+(e.getPreciseWheelRotation()*0.01f))));
        }
    };
}

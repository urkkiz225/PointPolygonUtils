package me.urkkiz.Actions;

import me.urkkiz.MainWindow.Init;
import me.urkkiz.MainWindow.MainLoop;
import me.urkkiz.Physics.PhysicsLoop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class WindowRuntime{
    //this is a prime example of a forgotten class...
    public static void GeneralInformation(boolean Toggle, String SetText){
        Init.GeneralInfo.setVisible(Toggle);
        if(SetText.length()!=0) Init.GeneralInfo.setText(SetText);
    }
    public static KeyListener listener=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //cases r prolly better but this will suffice
            Random random =new Random();
            if(e.getKeyCode()==KeyEvent.VK_RIGHT) PhysicsLoop.Angles=0.05f;
            if(e.getKeyCode()==KeyEvent.VK_LEFT) PhysicsLoop.Angles=-0.05f;
            if (e.getKeyCode()==KeyEvent.VK_G) GeneralInformation(!Init.GeneralInfo.isVisible(), Init.GeneralInfo.getText()+random.nextInt(4,61));
            if(e.getKeyCode()==KeyEvent.VK_P) {
                try {
                    System.out.print("\r");
                    MainLoop.InputCases();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT) PhysicsLoop.Angles=0.0f;
        }
    };
}

package me.urkkiz.Actions;

import me.urkkiz.MainWindow.Init;
import me.urkkiz.MainWindow.MainLoop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class WindowRuntime{
    public static void CreatePolygon() {

    }
    public static void PolygonContructor(int[] xy){

    }
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
            System.out.println("bruh!!1");
            Random random =new Random();
            if (e.getKeyCode()==KeyEvent.VK_G) GeneralInformation(!Init.GeneralInfo.isVisible(), Init.GeneralInfo.getText()+random.nextInt(4,61));
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
}

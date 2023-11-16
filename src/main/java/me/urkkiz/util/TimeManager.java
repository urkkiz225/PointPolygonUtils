package me.urkkiz.util;

import java.util.ArrayList;

public class TimeManager {
    public static ArrayList<Float> Timers=new ArrayList<>();
    public static float frameEndTime;
    public static void CalcFrameEndTime(){
        frameEndTime=((System.nanoTime()-frameEndTime));
    }
    public static float MasterTime=0;
}

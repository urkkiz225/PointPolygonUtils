package me.urkkiz.util;

import java.util.ArrayList;

public class TimeManager {
    public static ArrayList<Long> Timers=new ArrayList<>();
    public static float frameEndTime;
    public static void CalcFrameEndTime(){
        frameEndTime=((System.nanoTime()-frameEndTime));
    }
    public static float DeltaTime(){
        return ((System.nanoTime()-frameEndTime)*0.00000000001f);
    }
    public static float MasterTime=0;
}

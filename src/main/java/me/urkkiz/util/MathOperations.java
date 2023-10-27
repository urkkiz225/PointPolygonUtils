package me.urkkiz.util;

public class MathOperations {
    public static float Clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static float RadiansToAngles(float Radians){
        return (float) (Radians*(180/Math.PI));
    }
}

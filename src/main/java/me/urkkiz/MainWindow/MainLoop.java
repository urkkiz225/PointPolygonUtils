package me.urkkiz.MainWindow;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.StringUtil;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;


public class MainLoop extends Init {
    public static Graphics2D g = ((Graphics2D) DrawPanel.getGraphics());

    private static final Random random=new Random();
    public static boolean DebugLogEnabled=false;

    public static void Loop() {
        TimerTask MainLoop = new TimerTask() {
            @Override
            public void run() {
                Render();
                TimeManager.Timers.replaceAll(aFloat -> aFloat + 34 * 0.001f);
                TimeManager.MasterTime += 34 * 0.001f;
                TimeManager.CalcFrameEndTime();
                label.setText(String.valueOf(TimeManager.MasterTime));
                PhysicsLoop.physicsLoop();
                if(DebugLogEnabled) RequestDebugInfo();
            }
        };
        long delay = 17;
        long period = 34;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(MainLoop, delay, period);
    }

    public static void Render() {
        DrawPanel.paintAll(g);
        for (Polygon polygon : PolygonHolder.shapes) {
            g.setColor(Color.red);
            g.fill(polygon);
        }
        //drawing of polygon editing
        if (Init.GeneralInfo.isVisible()) {
            g.draw(new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 5, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 5, 10, 10));
            if (PolygonHolder.TempPolygon.size() != 0) {
                for (int j = 0; j < PolygonHolder.TempPolygon.size() - 1; j++) {
                    PolygonHolder.TempPolygon.set(PolygonHolder.TempPolygon.size() - 1, new int[]{MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y});
                    Line2D TLN = new Line2D.Float(PolygonHolder.TempPolygon.get(j)[0], PolygonHolder.TempPolygon.get(j)[1], PolygonHolder.TempPolygon.get(j + 1)[0], PolygonHolder.TempPolygon.get(j + 1)[1]);
                    Rectangle2D PointIndicator = new Rectangle2D.Float((float) TLN.getX1() - 5, (float) TLN.getY1() - 5, 10, 10);
                    if (j + 2 == PolygonHolder.TempPolygon.size())
                        g.draw((Math.abs(PolygonHolder.TempPolygon.get(j + 1)[0] - PolygonHolder.TempPolygon.get(0)[0]) < 16 && Math.abs(PolygonHolder.TempPolygon.get(j + 1)[1] - PolygonHolder.TempPolygon.get(0)[1]) < 16)
                                ? new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 8, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 8, 16, 16)
                                : new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x - 5, MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 5, 10, 10));
                    g.fill(PointIndicator);
                    g.draw(TLN);
                }
            }
            if (PolygonHolder.TempPolygon.size() > 2 && (Math.abs(PolygonHolder.TempPolygon.get(PolygonHolder.TempPolygon.size() - 2)[0] - PolygonHolder.TempPolygon.get(0)[0]) < 16 && Math.abs(PolygonHolder.TempPolygon.get(PolygonHolder.TempPolygon.size() - 2)[1] - PolygonHolder.TempPolygon.get(0)[1]) < 16)) {
                int[][] Coordinates = new int[2][PolygonHolder.TempPolygon.size()-1];
                //unoptimized conversion ik
                for (int i = 0; i < PolygonHolder.TempPolygon.size()-1; i++) {
                    Coordinates[0][i] = PolygonHolder.TempPolygon.get(i)[0];
                    Coordinates[1][i] = PolygonHolder.TempPolygon.get(i)[1];
                }
                PolygonHolder.PushPolygon(Arrays.copyOf(Coordinates[0], Coordinates[0].length-1), Arrays.copyOf(Coordinates[1], Coordinates[1].length-1)); //using Arrays.copyOf as a hotfix for too many points. might fix later.
                TimeManager.Timers.add(0F);
                PolygonHolder.CompilePolygons();
                PolygonHolder.TempPolygon.clear();
            }
        }
        //i do not know what i have done in this code block. it is unholy.
        if (PolygonHolder.TempPolygon.size()==0){
            g.draw(new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x-5,MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y-5,10,10));
        }
        g.fillRect(500, (int) MathOperations.Clamp((float) (0 + (9.81 * (TimeManager.MasterTime * TimeManager.MasterTime))), 0, 420), 50, 50);
    }
    @SuppressWarnings("unchecked")
    private static void RequestDebugInfo(){
        PhysicsLoop.IsPolygonConcave(PolygonHolder.shapes.get(0));
        System.out.print("\nMathOperations: \n" +
                "   Vector Length (int and float param): " + MathOperations.LengthFloat(new float[]{1.5f,1+random.nextInt(8)}) + " : " + MathOperations.LengthInt(new int[]{1,1})
                +"\n    Vector dot product: " + MathOperations.VectorDotProduct(new float[]{1,1+random.nextInt(8)}, new float[]{-2,-2})
                +"\n     Length of vector cross product: " + MathOperations.LengthOfCrossProduct(new float[]{1,1+random.nextInt(8)}, new float[]{-2,-2})
                + "\n    Angle between vectors: " + MathOperations.AngleBetweenVector(new float[]{1,1+random.nextInt(8)}, new float[]{2,random.nextInt(8)})+"rad / " + MathOperations.AngleBetweenVector(new float[]{1,1+random.nextInt(8)}, new float[]{2,random.nextInt(8)})*(float)(180/Math.PI)+"°"
                +  "\n    SmallestIntegerInArray: " + MathOperations.SmallestIntegerInArray(new int[]{1+random.nextInt(8),2,3,4,5,6,7,8,9,6,9,4,2,0})
                +   "\n    ConcatIntArray: " + Arrays.toString(MathOperations.ConcatIntArray(new int[]{2,3+random.nextInt(8)},new int[]{3,2}))
                +    "\n    Closest value to number from list:" +MathOperations.FindClosestValue(3.14159f,new float[]{4,4,4+random.nextInt(8),4})
                +     "\n    Radians to angles: "+MathOperations.RadiansToAngles(3.14159f+random.nextFloat(-3.14159f,3.14159f))+"°"
                +      "\n    Abstract sign of quadrant: "+MathOperations.AbstractQuadrantSign(new int[]{random.nextInt(-4,4),random.nextInt(-4,4)},new int[]{random.nextInt(-4,4),random.nextInt(-4,4)})
                + "\n Polygon holder: \n" +
                    "   Base Polygons: " + PolygonHolder.BasePolygons.size()
                +       "\n   First shape stats: "+(PolygonHolder.shapes.size()!=0?Arrays.toString(PolygonHolder.shapes.get(0).xpoints)+", "+Arrays.toString(PolygonHolder.shapes.get(0).ypoints)+", "+PolygonHolder.shapes.get(0).npoints:"And yet, the polygon array was empty. He stood there, unaware of what do make of his current situation. "
                + "He blankly stared the 0-size of the array for many hours, and then promptly fell asleep. Next time, consider pushing a polygon.")
                +"\n Physics: \n"
                +"     Collision (SAT): "+(PolygonHolder.shapes.size()>1?PhysicsLoop.CollisionCheckConvex(PolygonHolder.shapes.get(0),PolygonHolder.shapes.get(1)):"At least 2 polygons needed to evaluate collision. Didn't you play with a shape sorter box a toddler?")
                +"\n     Collision (repetitive point check): "+(PolygonHolder.shapes.size()>1?PhysicsLoop.CollisionCheckConcave(PolygonHolder.shapes.get(0),PolygonHolder.shapes.get(1)):"At least 2 polygons needed to evaluate collision. Didn't you play with a shape sorter box a toddler?")
                +"\n     Is polygon concave: " + PolygonHolder.ConcaveHandler.get(0)[0] + "" + (PolygonHolder.ConcaveHandler.get(0).length==2? (Arrays.toString(((ArrayList<Integer[]>) (PolygonHolder.ConcaveHandler.get(0)[1])).get(0))) : "")
                +"\n Transform: \n"+"     Accurate cumulative float overflow: "+Arrays.deepToString(Transform.AccurateCumulativeDoubleOverFlows));
    }
    public static void InputCases() throws InterruptedException{
        System.out.println("Awaiting new input line...");
        String UserInput = StringUtil.UserLineInput();
        //checking is case-sensitive purely due to comedic purposes.
        switch(UserInput){
            case "toggle the got damn debugs!", "debug", "enable debug plz", "TOGGLE THE FUCKING DEBUG YOU FUCKING IDIOTIC MACHINE!!! RAAAHHH!", "Hello, good sir. Do you mind apprising me with all the knowledge of mankind?", "give me debug. give it to me!", "beep boop. beep?", "Those who are wayward in spirit will gain understanding; those who complain will accept instruction." -> {
                System.out.println("Warning! This amount of console lines can slow down the program. Nevertheless, in your stupidity, you have enabled debug logs. It can not be turned off. Have fun.");
                for (int i = 8; i != -1; i--) {
                    System.out.print("\rApproaching hell. ETA: " + i + "s...\r");
                    Thread.sleep(1350);
                }
                DebugLogEnabled=true;
            } case "EXIT", "/E", "guh bye" -> {
                System.out.println("guh bye.");
                for (int i = 3; i != -1; i--) {
                    System.out.print("Exiting in " + i + "s...\r");
                    Thread.sleep(1000);
                }
                System.exit(0);
            }case "Sing me a song" -> {
                System.out.println("Nah");
            }
            case "ConcaveDebug", "concave" -> {
                System.out.println("Toggled concave debug. Type again to toggle... again. Continuing in five seconds.");
                Thread.sleep(5000);
                DebugLogEnabled=false;
                PhysicsLoop.ConcaveDebug=!PhysicsLoop.ConcaveDebug;
            }
            default -> System.out.println("Not a valid input. Try again. Or don't.");
        }
    }
}

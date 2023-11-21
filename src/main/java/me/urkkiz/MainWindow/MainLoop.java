package me.urkkiz.MainWindow;

import me.urkkiz.Physics.PhysicsLoop;
import me.urkkiz.Shapes.PolygonHolder;
import me.urkkiz.util.MathOperations;
import me.urkkiz.util.StringUtil;
import me.urkkiz.util.TimeManager;
import me.urkkiz.util.Transform;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Timer;


public class MainLoop extends Init {
    public static Graphics2D g = ((Graphics2D) DrawPanel.getGraphics());

    private static final Random random=new Random();
    public static boolean DebugLogEnabled=false;
    public static ArrayList<float[]> PseudoCenters=new ArrayList<>();
    private static TimerTask MainLoop_;
    public static int Period=18;
    public static boolean DrawIndexes=false;
    private static boolean PouringSoulIntoClairTheLune=false;
    private static int ThePianistInteger=0;
    private static boolean SeeingRainbows =false;

    public static void Loop() {
        MainLoop_ = new TimerTask() {
            @Override
            public void run() {
                Render();
                TimeManager.Timers.replaceAll(aFloat -> aFloat + 34 * 0.001f);
                TimeManager.MasterTime += Period * 0.001f;
                TimeManager.CalcFrameEndTime();
                label.setText(TimeManager.MasterTime+"s");
                if(DebugLogEnabled) RequestDebugInfo();
                PhysicsLoop.physicsLoop();
            }
        };
        long delay = 17;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(MainLoop_, delay, Period);
    }

    public static void Render() {
        DrawPanel.paintAll(g);
        if(SeeingRainbows) RainbowsAndCupcakes();
        for (int i=0; i< PolygonHolder.shapes.size();i++) {
            g.fill(PolygonHolder.shapes.get(i));
            if(DrawIndexes){
                PseudoCenters.set(i,PhysicsLoop.CalculatePseudoCenter(PolygonHolder.shapes.get(i)));
                DrawPanel.getGraphics().drawString(String.valueOf(i), (int) PseudoCenters.get(i)[0], (int) PseudoCenters.get(i)[1]);
            }
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
                if(Coordinates[0].length!=2) {
                    Transform.DeltaPositions.add(new int[2][Coordinates[0].length - 1]);
                    PolygonHolder.PushPolygon(Arrays.copyOf(Coordinates[0], Coordinates[0].length - 1), Arrays.copyOf(Coordinates[1], Coordinates[1].length - 1)); //using Arrays.copyOf as a hotfix for too many points. might fix later.
                    PhysicsLoop.IsPolygonConcave(new Polygon(Arrays.copyOf(Coordinates[0], Coordinates[0].length - 1), Arrays.copyOf(Coordinates[1], Coordinates[1].length - 1), Coordinates[0].length - 1));
                    PseudoCenters.add(PhysicsLoop.CalculatePseudoCenter(PolygonHolder.shapes.get(PolygonHolder.shapes.size()-1)));
                    TimeManager.Timers.add(0F);
                    PolygonHolder.CompilePolygons();
                    PolygonHolder.TempPolygon.clear();
                }else{
                    System.out.println("Can't create polygon from 2 points!");
                    PolygonHolder.TempPolygon.clear();
                }
            }
        }
        if (PolygonHolder.TempPolygon.size()==0&&GeneralInfo.isVisible()){
            g.draw(new Rectangle2D.Float(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x-5,MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y-5,10,10));
        }
    }
    @SuppressWarnings("unchecked")
    private static void RequestDebugInfo(){
        PhysicsLoop.IsPolygonConcave(PolygonHolder.shapes.get(0));
        ArrayList<int[]> points=new ArrayList<>(){{add(new int[]{2,3});add(new int[]{3,2});add(new int[]{1920,1080});}};
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
                +       "\n    Line intersection: " + (PolygonHolder.shapes.size()>1?Arrays.deepToString(MathOperations.CheckLineIntersection(new int[][]{new int[]{PolygonHolder.shapes.get(0).xpoints[0], PolygonHolder.shapes.get(0).ypoints[0]},new int[]{PolygonHolder.shapes.get(0).xpoints[1], PolygonHolder.shapes.get(0).ypoints[1]}},new int[][]{new int[]{PolygonHolder.shapes.get(1).xpoints[0], PolygonHolder.shapes.get(0).ypoints[0]},new int[]{PolygonHolder.shapes.get(1).xpoints[1], PolygonHolder.shapes.get(0).ypoints[1]}})) : "Not enough polygons to eval intersection")
                +        "\n    Depth of point polygon penetration: " + (PolygonHolder.shapes.size()>1?MathOperations.GetPenetrationDepthOfPointOnPolygon(new int[]{PolygonHolder.shapes.get(0).xpoints[0],PolygonHolder.shapes.get(0).xpoints[0]},0,PolygonHolder.shapes.get(1)) : "Not enough polygons to eval intersection")
                +         "\n    Closest two points: " + (PolygonHolder.shapes.size()>1?Arrays.deepToString(MathOperations.ClosestTwoPoints(new int[]{PolygonHolder.shapes.get(0).xpoints[0],PolygonHolder.shapes.get(0).xpoints[0]},points)) : "Not enough polygons to eval intersection")
                + "\n Polygon holder: \n" +
                    "   Base Polygons: " + PolygonHolder.BasePolygons.size()
                +       "\n   First shape stats: "+(PolygonHolder.shapes.size()!=0?Arrays.toString(PolygonHolder.shapes.get(0).xpoints)+", "+Arrays.toString(PolygonHolder.shapes.get(0).ypoints)+", "+PolygonHolder.shapes.get(0).npoints:"And yet, the polygon array was empty. He stood there, unaware of what do make of his current situation. "
                + "He blankly stared the 0-size of the array for many hours, and then promptly fell asleep. Next time, consider pushing a polygon.")
                +"\n Physics: \n"
                +"     Collision (SAT): "+(PolygonHolder.shapes.size()>1?PhysicsLoop.CollisionCheckConvex(PolygonHolder.shapes.get(0),PolygonHolder.shapes.get(1)):"At least 2 polygons needed to evaluate collision. Didn't you play with a shape sorter box a toddler?")
                +"\n     Collision (repetitive point check): "+(PolygonHolder.shapes.size()>1?PhysicsLoop.CollisionCheckConcave(PolygonHolder.shapes.get(0),PolygonHolder.shapes.get(1)):"At least 2 polygons needed to evaluate collision. Didn't you play with a shape sorter box a toddler?")
                +"\n     Is polygon concave: " + PolygonHolder.ConcaveHandler.get(0)[0] + "" + (PolygonHolder.ConcaveHandler.get(0).length==2? (Arrays.toString(((ArrayList<Integer[]>) (PolygonHolder.ConcaveHandler.get(0)[1])).get(0))) : "")
                +"\n Transform: \n"+"     Accurate cumulative float overflow: "+Arrays.deepToString(Transform.AccurateCumulativeDoubleOverFlows)
                +"\n\n\n");
    }
    public static void RainbowsAndCupcakes(){
        g.setColor(new Color((int) (127*(Math.sin(TimeManager.MasterTime)+1)), (int) (127*(Math.sin(TimeManager.MasterTime*0.25f)+1)), (int) (127*(Math.sin(TimeManager.MasterTime*0.6)+1))));
        DrawPanel.setBackground(new Color((int) (127*(Math.sin(-TimeManager.MasterTime)+1)), (int) (127*(Math.sin(-TimeManager.MasterTime*0.25f)+1)), (int) (127*(Math.sin(-TimeManager.MasterTime*0.6)+1))));
    }
    public static void InputCases() throws InterruptedException{
        if(PolygonHolder.shapes.size()!=0) {
            PhysicsLoop.Movement=new float[]{0,0};
            PhysicsLoop.Degrees=0;
            System.out.println("Awaiting new input line...");
            String UserInput = StringUtil.UserLineInput("");
            //checking is case-sensitive purely due to comedic purposes.
            switch (UserInput) {
                case "toggle the got damn debugs!", "debug", "enable debug plz", "TOGGLE THE FUCKING DEBUG YOU FUCKING IDIOTIC MACHINE!!! RAAAHHH!", "Hello, good sir. Do you mind apprising me with all the knowledge of mankind?", "give me debug. give it to me!", "beep boop. beep?", "Those who are wayward in spirit will gain understanding; those who complain will accept instruction." -> {
                    System.out.println("You have enabled debug logs. It can not be turned off. Have fun.");
                    for (int i = 8; i != -1; i--) {
                        System.out.print("\rApproaching hell. ETA: " + i + "s...\r");
                        Thread.sleep(1250);
                    }
                    DebugLogEnabled = true;
                }
                case "EXIT", "/E", "guh bye" -> {
                    System.out.println("guh bye.");
                    for (int i = 3; i != -1; i--) {
                        System.out.print("Exiting in " + i + "s...\r");
                        Thread.sleep(1000);
                    }
                    System.exit(0);
                }
                case "Sing me a song", "Play for me", "Play me the most beautiful piano piece" -> {
                    try {
                        InputStream audioSrc = Init.class.getClassLoader().getResourceAsStream("ClairDeLune.wav");
                        InputStream bufferedIn = new BufferedInputStream(audioSrc);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                        Clip clip = AudioSystem.getClip();
                        if(ThePianistInteger==0){
                        }
                        if (!PouringSoulIntoClairTheLune) {
                            clip.open(audioInputStream);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            PouringSoulIntoClairTheLune = true;
                            System.out.println("Enjoy this majestic piece of history. Do NOT type prompts again to toggle. Please.");
                        } else {
                            clip.stop();
                            clip.close();
                            System.out.println("Did you just... Interrupt me?");
                            Thread.sleep(4000);
                            System.out.println("This is the rudest thing that has happened in many centuries. No on has dared to disrespect me like this.");
                            Thread.sleep(4000);
                            System.out.println("You know what? Just for the disrespect, I'm going to make you quit.");
                            for (int i = 0; i != 4; i++) {
                                Thread.sleep(4000);
                                System.out.println("...");
                            }
                            System.out.println("Good bye. May your woes be many, and your days few.");
                            for (int i = 3; i != -1; i--) {
                                System.out.print("Exiting in " + i + "s...\r");
                                Thread.sleep(1500);
                            }
                            System.exit(0);
                        }
                    } catch (UnsupportedAudioFileException | LineUnavailableException e) {
                        System.out.println("Something went terribly wrong. I really do not know what, and I have a headache. Continuing in five seconds. " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "background", "bgc" -> {
                    System.out.println("Please enter a new background color: ");
                    Color c=DrawPanel.getBackground();
                    String s = StringUtil.UserLineInput("");
                    switch (s.toLowerCase(Locale.ROOT)) {
                        case "red" -> DrawPanel.setBackground(Color.red);
                        case "blue" -> DrawPanel.setBackground(Color.blue);
                        case "black" -> DrawPanel.setBackground(Color.black);
                        case "white" -> DrawPanel.setBackground(Color.white);
                        case "yellow" -> DrawPanel.setBackground(Color.yellow);
                        case "orange" -> DrawPanel.setBackground(Color.orange);
                        case "gray", "grey" -> DrawPanel.setBackground(Color.gray);
                        case "green" -> DrawPanel.setBackground(Color.green);
                        case "cyan" -> DrawPanel.setBackground(Color.cyan);
                        case "magenta" -> DrawPanel.setBackground(Color.magenta);
                        case "pink" -> DrawPanel.setBackground(Color.pink);
                        case "gold" -> DrawPanel.setBackground(new Color(212, 175, 55));
                        case "blood" -> {
                            System.out.println("Hell is full. Blood is fuel.");
                            DrawPanel.setBackground(new Color(136, 8, 8));
                        }
                        case "custom" ->{
                            try{
                                Color color = (new Color(Integer.parseInt(StringUtil.UserLineInput("Type value R")),Integer.parseInt(StringUtil.UserLineInput("Type value G")),Integer.parseInt(StringUtil.UserLineInput("Type value B"))));
                                if(color.getRed()<256&&color.getGreen()<256&&color.getBlue()<256) {
                                    DrawPanel.setBackground(color);
                                    System.out.println("Color set. ("+color+")");
                                }
                                else System.out.println("Not a valid color. R, G and B must be in range of 0,255");
                            }catch( NumberFormatException e){
                                System.out.println("Not an integer.");
                            }
                        }
                        default -> System.out.println("No color found with prompt. To set a custom color, type \"color\"");
                    }
                    if (Objects.equals(DrawPanel.getBackground(), g.getColor()))
                        System.out.println("Warning! Color of background and polygons are the same.");
                    if(!Objects.equals(DrawPanel.getBackground(),c)){
                        SeeingRainbows =false;
                    }
                }
                case "color", "c" -> {
                    System.out.println("Please enter a new polygon color: ");
                    Color c=g.getColor();
                    String s = StringUtil.UserLineInput("");
                    switch (s.toLowerCase(Locale.ROOT)) {
                        case "red" -> g.setColor(Color.red);
                        case "blue" -> g.setColor(Color.blue);
                        case "black" -> g.setColor(Color.black);
                        case "white" -> g.setColor(Color.white);
                        case "yellow" -> g.setColor(Color.yellow);
                        case "orange" -> g.setColor(Color.orange);
                        case "gray", "grey" -> g.setColor(Color.gray);
                        case "green" -> g.setColor(Color.green);
                        case "cyan" -> g.setColor(Color.cyan);
                        case "magenta" -> g.setColor(Color.magenta);
                        case "pink" -> g.setColor(Color.pink);
                        case "gold" -> g.setColor(new Color(212, 175, 55));
                        case "blood" -> {
                            System.out.println("Hell is full. Blood is fuel.");
                            g.setColor(new Color(136, 8, 8));
                        }
                        case "custom" ->{
                            try{
                                Color color = (new Color(Integer.parseInt(StringUtil.UserLineInput("Type value R")),Integer.parseInt(StringUtil.UserLineInput("Type value G")),Integer.parseInt(StringUtil.UserLineInput("Type value B"))));
                                if(color.getRed()<256&&color.getGreen()<256&&color.getBlue()<256){
                                    g.setColor(color);
                                    System.out.println("Color set. ("+color+")");
                                }
                                else System.out.println("Not a valid color. R, G and B must be in range of 0,255");
                            }catch( NumberFormatException e){
                                System.out.println("Not an integer.");
                            }
                        }
                        default -> System.out.println("No color found with prompt. To set a custom color, type \"color\"");
                    }
                    DrawPanel.update(DrawPanel.getGraphics());
                    if (Objects.equals(DrawPanel.getBackground(), g.getColor()))
                        System.out.println("Warning! Color of background and polygons are the same.");
                    if(!Objects.equals(g.getColor(),c)){
                        SeeingRainbows =false;
                    }
                }
                case "ConcaveDebug", "concave", "GEBUERJEIT" -> {
                    System.out.println("Toggled concave debug. Type again to toggle... again. Continuing in five seconds.");
                    Thread.sleep(5000);
                    DebugLogEnabled = false;
                    PhysicsLoop.ConcaveDebug = !PhysicsLoop.ConcaveDebug;
                }
                case "FPS", "fps", "limitFPS", "limit", "delay" -> {
                    System.out.println("Please enter new minimum delay between frames (ms): ");
                    try {
                        String s = StringUtil.UserLineInput("");
                        if (Integer.parseInt(s) != 0) {
                            System.out.println(Integer.parseInt(s) < 10 ? "Warning! A delay this small (" + Integer.parseInt(s) + ") way create rendering issues. Continuing in five seconds..."
                                    : "Continuing with new delay (" + Integer.parseInt(s) + ") in five seconds...");
                            Thread.sleep(5000);
                            Period = Integer.parseInt(s);
                            MainLoop_.cancel();
                            Loop();
                        } else System.out.println("Delay can not be 0");
                    } catch (NumberFormatException e) {
                        System.out.println("That's not a valid integer. Consider taking your first-grade math lessons again. Continuing in five seconds...");
                        Thread.sleep(5000);
                    }
                }
                case "indexes", "index", "polygonIndex" -> {
                    DrawIndexes = !DrawIndexes;
                    System.out.println(!DrawIndexes ? "Index drawing disabled" : "Index drawing enabled");
                }case "rainbow", "lsd" -> {
                    System.out.println("Warning! This slows down rendering and creates rendering issues.");
                    SeeingRainbows =!SeeingRainbows;
                }
                default -> System.out.println("Not a valid input. Try again. Or don't.");
            }
        }else System.out.println("Please push a polygon first. I have gotten enough ArrayIndexOutOfBoundsExceptions.");
    }
}

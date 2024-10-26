package byog.Core;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.io.Serializable;

public class Game implements Serializable {
    boolean inputing = false;
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static final int MIDWIDTH = 40;
    public static final int MIDHEIGHT = 20;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public Game(){
        StdDraw.setCanvasSize(WIDTH*16,HEIGHT*16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0,WIDTH);
        StdDraw.setYscale(0,HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }
    private void drawFrames(String[] input){
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        int startWidth = WIDTH/2;
        int startHeight = HEIGHT/2+5;
        for( String s : input){
            StdDraw.text(startWidth,startHeight,s);
            startHeight -=5;
        }
        StdDraw.show();
    }
    private void appendDraw(String s, int x , int y){
        StdDraw.setPenColor(Color.white);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }
    private void drawFrame(String s, int x , int y){
//        int midWidth = WIDTH / 2;
//        int midHeight = HEIGHT / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }
    private void flash(String s,int x,int y,int time){
        appendDraw(s,x,y);
        StdDraw.pause(time);
        System.out.println("Pausing");
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(x,y,MIDWIDTH,1);
        StdDraw.show();
        StdDraw.setPenColor(Color.white);
    }
    private void newGame(){
        /** start a new game */
        boolean gameover = false;
        drawFrame("Type in the seed and press s to start : ",MIDWIDTH,MIDHEIGHT+5);
        String seed = "";
        while(! gameover){
            boolean typing = true;
            int x= MIDWIDTH;
            int y= MIDHEIGHT;
            while(typing){
                if(!StdDraw.hasNextKeyTyped()){
                    continue; // if no key is pressed, continue the loop;
                }
                char key = StdDraw.nextKeyTyped();
                appendDraw(Character.toString(key),x-5,y);
                x+=1;
                if(key == 's'){
                    typing = false;
                    break;
                }
                seed += key;
            }
            if(seed.isEmpty()){
                System.out.println("The seed is empty");
                flash("The Seed must be a long number. Type again",MIDWIDTH,MIDHEIGHT,1000);
            }else{
                gameover = true;
                play(seed);
            }
        }
    }


    public class GameState implements Serializable {
        private static final long serialVersionUID = 1L;
        private TETile[][] world;
        private GameObject player;
        private GameObject monster;
        private GameObject token;
        private GameObject entrance;
        private GameObject exit;
        public GameState(TETile[][] world, GameObject player, GameObject monster, GameObject token, GameObject entrance, GameObject exit) {
            this.world = world;
            this.player = player;
            this.monster = monster;
            this.token = token;
            this.entrance = entrance;
            this.exit = exit;
        }

    }
    /** GameObject class */
    private class GameObject implements Serializable{
        private int x;
        private int y;
        private String name;
        private TETile tile;
        private TETile[][] world;
        private Map.Position pos;
        public GameObject(String name ,int x , int y , TETile[][] world){
            this.x = x;
            this.y = y;
            this.name = name;
            this.world = world;
            this.tile = fill(name);
            this.world[x][y] = this.tile;
            ter.renderFrame(this.world);
        }
        private TETile fill(String name){
            switch(name){
                case "player" : return Tileset.PLAYER;
                case "monster" : return Tileset.GRASS; // monster pretends to be a tree
                case "token" : return Tileset.TREE;
                case "entrance" : return Tileset.UNLOCKED_DOOR;
                case "exit" : return Tileset.LOCKED_DOOR;
                default : return Tileset.NOTHING;
            }
        }
        public boolean getClose(GameObject target){
            return distance(target) <= 4;
        }
        public boolean reach(GameObject target){
            return x == target.x && y == target.y;
        }
        private boolean moveable(int x, int y){
            return world[x][y].equals(Tileset.FLOOR);
        }
        private char[] chasePath(GameObject target){
            return null;
        }
        private void chase(GameObject target){
            char[] directions = {'w','a','s','d'};
            int distance = distance(target);
            int x_loss = this.x - target.x; // this on the left of target if x_loss < 0 ;
            int y_loss = this.y -target.y; // this on the down of target if y_loss < 0 ;
            int currentdistance = distance;
            while(currentdistance >= distance){
                if(distance == 0){
                    break;
                }
                move(chasePath(target)[0]);
            }
        }
        public int distance(GameObject target){
            return Math.abs(x-target.x) + Math.abs(y-target.y);
        }
        public void move(char key){
            switch(key){
                case 'w' :
                    goUp();
                    break;
                case 's' :
                    goDown();
                    break;
                case 'a' :
                    goLeft();
                    break;
                case 'd' :
                    goRight();
                    break;
                default : break;
            }
        }
        private boolean accessable(int x ,int y){
            if(! inMaze(x,y)){
                return false;
            }
            return !world[x][y].equals(Tileset.WALL) && !world[x][y].equals(Tileset.NOTHING);
        }
        private boolean inMaze(int x,int y){
            return x>=0 && x < WIDTH && y >= 0 && y < HEIGHT;
        }
        public void goUp(){
            if(!accessable(x,y+1)){
                return;
            }
            world[x][y] = Tileset.FLOOR;
            this.y +=1;
            world[x][y] = Tileset.PLAYER;
            ter.renderFrame(world);
        }
        public void goDown(){
            if(!accessable(x,y-1)){
                return;
            }
            world[x][y] = Tileset.FLOOR;
            this.y -=1;
            world[x][y] = Tileset.PLAYER;
            ter.renderFrame(world);
        }
        public void goLeft(){
            if(!accessable(x-1,y)){
                return;
            }
            world[x][y] = Tileset.FLOOR;
            this.x -=1;
            world[x][y] = Tileset.PLAYER;
            ter.renderFrame(world);
        }
        public void goRight(){
            if(! accessable(x+1,y)){
                return;
            }
            world[x][y] = Tileset.FLOOR;
            this.x +=1;
            world[x][y] = Tileset.PLAYER;
            ter.renderFrame(world);
        }
        public void update(){
            world[this.x][this.y] = this.tile;
        }
    }






    private void saveAndQuit(GameState gamestate){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gamestate.ser"))) {
            out.writeObject(gamestate);
            System.out.println("游戏状态已保存：" + gamestate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sign(GameState gamestate){
        int x = (int)Math.floor(StdDraw.mouseX());
        int y = (int)Math.floor(StdDraw.mouseY());
        if(x >= WIDTH || y >= HEIGHT){
            return;
        }
        appendDraw("This is "+gamestate.world[x][y].description(),5,HEIGHT-5);
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(5,HEIGHT-5,5,1);
        StdDraw.setPenColor(Color.white);
    }
    private void mainGameLoop(GameState gamestate){
        boolean gameover = false;
        boolean tokenGot = false;
        ter.renderFrame(gamestate.world);
        while(! gameover){
            /** add mouse signal */
            sign(gamestate);

            /** jump if no key input */
            if (! StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if(key == ':'){
                StdDraw.pause(1000); // wait to catch the q;
                if(! StdDraw.hasNextKeyTyped()){
                    continue;
                }
                if(StdDraw.nextKeyTyped() == 'q'){
                    saveAndQuit(gamestate);
                    drawFrame("Game Saved Successfully",MIDWIDTH,MIDHEIGHT);
                    return;
                }
            }


            /** move if key input */
            gamestate.player.move(key);
            if(gamestate.player.reach(gamestate.exit)){
                gameover = true;
                break;
            }
            /** monster chase player */
            if(gamestate.player.getClose(gamestate.monster)){
//                gamestate.monster.chase(gamestate.player);
//                if(gamestate.player.reach(gamestate.monster)){
//                    break;
//                }
            }
            /** get the token */
            if(gamestate.player.reach(gamestate.token) && !tokenGot){
                tokenGot = true;
                appendDraw("You got the token !",MIDWIDTH,HEIGHT-1);
            }
        }
        /** game over */
        if(gameover){
            drawFrame("Congratulation ! You win. The Game is over",MIDWIDTH,MIDHEIGHT);
        }else{
            drawFrame("Ooops ! Caught by a Monster! The Game is over",MIDWIDTH,MIDHEIGHT);
        }
    }
    private void play(String input){
        /** initialize the world */
        ter.initialize(WIDTH, HEIGHT);
//        int MazeWidth = WIDTH-20;
//        int MazeHeight = HEIGHT-10;
        int basebound = 5;
        int sidebound = 10;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1){
                world[x][y] = Tileset.NOTHING;
            }
        }
        long seed = readSeed(input);
        Map map= new Map(seed,WIDTH,HEIGHT,world);
        map.addMaze();

        /** add object to the world */
        GameObject entrance = new GameObject("entrance", map.entrance.x , map.entrance.y, world);
        GameObject exit = new GameObject("exit", map.exit.x, map.exit.y,world);
        GameObject player = new GameObject("player",map.entrance.x ,map.entrance.y,world);
        int[] monsterPos = map.addObjectPos();
        int[] tokenPos = map.addObjectPos();
        GameObject monster = new GameObject("monster",monsterPos[0],monsterPos[1],world);
        GameObject token = new GameObject("token",tokenPos[0],tokenPos[1],world);
        GameState gamestate = new GameState(world,player,monster,token,entrance,exit);

        /** main part of the game */
        mainGameLoop(gamestate);
    }
    private void loadGame(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gamestate.ser"))) {
            GameState gameState = (GameState) in.readObject();
            System.out.println("游戏状态已加载：" + gameState);
            ter.initialize(WIDTH, HEIGHT);
            mainGameLoop(gameState);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void endGame(){
        drawFrame("Game Over !",MIDWIDTH,MIDHEIGHT);
    }
    public void playWithKeyboard() {
        boolean gameRunning = true;
        String[] input = {"Welcome to the Game !","Please enter : ", "N : New Game ","L : Load the Game","Q : Quit "};
        drawFrames(input);
        while(gameRunning){
            if(StdDraw.hasNextKeyTyped()){
                char inputKey = StdDraw.nextKeyTyped();
                switch(inputKey){
                    case 'n' : newGame();break;
                    case 'l' : loadGame();break;
                    case 'q' :
                        gameRunning = false;
                        endGame();
                        break;
                    default : break;
                }
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    private static long readSeed(String input){
        /** read the input Command String and return the seed */
        String seedLong = input.replaceAll("[^0-9]", "");
        long seed = Long.parseLong(seedLong);
        return seed;
    }
    private String readCommand(String input){
        /** read the input Command String and return the command */
        String command = input.replaceAll("[0-9]", "");
        return command;
    }
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter.initialize(WIDTH, HEIGHT);
//         initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int MazeWidth = WIDTH-10;
        int MazeHeight = HEIGHT-5;
        int basebound = 5;
        int sidebound = 10;
        System.out.println("the maze is done, drawing");
        ter.renderFrame(world);
        inputing = true;
        try {
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);


            String text = input;
            for (char c : text.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (KeyEvent.CHAR_UNDEFINED != keyCode) {
                    if(c==':'){
                        robot.keyPress(KeyEvent.VK_SHIFT);
                        robot.keyPress(KeyEvent.VK_SEMICOLON);
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                        continue;
                    }
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }
                // 加入延迟，让每个字符有一点间隔
                robot.delay(100);
            }
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        playWithKeyboard();
//        long seed = readSeed(input);
//        Map map= new Map(seed,MazeWidth,MazeHeight,world);
//        map.addMaze();
//        ter.renderFrame(world);
        return world;
    }
}

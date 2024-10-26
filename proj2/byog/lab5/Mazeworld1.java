package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a maze world
 */
public class Mazeworld1 {
    private static int HIGHT = 40;
    private static int WIDTH = 60;
//    private int down_bound = 40;
//    private int up_bound = 70;
//    private int right_bound = 70;
//    private int left_bound = 30;
    private static final long SEED = 2776133;
    private static final Random RANDOM = new Random(SEED);
    public Mazeworld1(){
    }
    private static class Position{
        private int x ;
        private int y;;
        private int x_trend = -1;
        private int y_trend = 0;
        private Position(int x, int y){
            this.x = x ;
            this.y = y ;
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }
    private static boolean accessble(Position p, TETile[][] world){
        /** return true if the position is in the maze and has not been accessed */
        if( ! inMaze(p, world)){
            return false;
        }else if(world[p.x][p.y] != Tileset.FLOOR){
            return true;
        }else{
            return false;
        }
    }
    private static boolean inMaze(Position p, TETile[][] world){
        if(p.x < 0 || p.x >= WIDTH || p.y < 0 || p.y >= HIGHT){
            return false;
        }
        return true;
    }
    private void buildwall(TETile[][] world){
        for(int i=0;i<WIDTH;i+=1){
            for(int j=0;j<HIGHT;j+=1){
                world[i][j] = Tileset.WALL;
            }
        }
    }
    private Position randomchoose(TETile[][] world , Random rand, Position cur, int x_trend , int y_trend){
        int direction = rand.nextInt(12);
        Position next;
        if(direction>=0 && direction < 8){
            next = new Position(cur.x+cur.x_trend,cur.y+cur.y_trend);
            next.x_trend = cur.x_trend;
            next.y_trend = cur.y_trend; // do not change the direction;
        }else if(direction == 8){
            next = new Position(cur.x,cur.y+1); // turn up ;
            next.x_trend = 0;
            next.y_trend = 1;
        }else if(direction == 9){
            next = new Position(cur.x,cur.y-1); // turn down;
            next.x_trend = 0;
            next.y_trend = -1;
        }else if(direction == 10){
            next = new Position(cur.x-1,cur.y); // turn right;
            next.x_trend = -1;
            next.y_trend = 0;
        }else{
            next = new Position(cur.x+1,cur.y); // turn left;
            next.x_trend = 1;
            next.y_trend = 0;
        }
        return next;
    }
    private Position randomNextPostion(TETile[][] world, Random rand,Position cur){
        Position nextPostion = randomchoose(world,rand,cur,cur.x_trend, cur.y_trend);
        while( ! accessble(nextPostion,world) ){
            if(deadend(cur,world)){
                System.out.println("Come across a dead end");
                while(! inMaze(nextPostion,world)){
                    nextPostion = randomchoose(world,rand,cur,cur.x_trend, cur.y_trend);
                }
                break;
            }
            nextPostion = randomchoose(world,rand,cur,cur.x_trend, cur.y_trend);
        }
        return nextPostion;
    }
    private Position randomPosition(TETile[][] world, Random RANDOM){
        /** definite the random position for entrance or exit */
        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HIGHT);
        return new Position(x, y);
    }
    private static boolean deadend(Position p, TETile[][] world){
        /**return true if cur comes acrosss a dead end(Position around cur all cannot be accessed*/
        Position up = new Position(p.x,p.y+1);
        Position down = new Position(p.x,p.y-1);
        Position left = new Position(p.x-1 ,p.y);
        Position right = new Position(p.x+1,p.y);
        if(accessble(up,world) || accessble(down,world) || accessble(left,world) || accessble(right,world)){
            return false;
        }
        return true;
    }
    private Position dig(TETile[][] world,Random rand,Position cur){
        Position next = randomNextPostion(world,rand,cur);
        world[next.x][next.y] = Tileset.FLOOR;
        return next;
    }
    private boolean positionEqual(Position p1, Position p2){
        return p1.x == p2.x && p1.y == p2.y;
    }
    public void addMaze(TETile[][] world, Random rand, TERenderer ter){
        /**build a maze */
        buildwall(world);
        Position entrance = randomPosition(world, rand); // add the entrance;
        Position exit = randomPosition(world,rand); //add the exit ;
        world[entrance.x][entrance.y] = Tileset.UNLOCKED_DOOR;
        world[exit.x][exit.y] = Tileset.LOCKED_DOOR;
        Position cur = entrance;
        while(! positionEqual(cur,exit)){
            System.out.println("current position is x : " + cur.x +" y : "+ cur.y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ter.renderFrame(world);
            cur = dig(world, RANDOM,cur); // choose a position and update the cur;
        }
        world[cur.x][cur.y] = Tileset.LOCKED_DOOR;
        System.out.println("Find the exit !");
    }
    public static void main(String[] args){
        /** initialize the tile rendering engine with a window of size WIDTH x HEIGHT */
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HIGHT);
        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Mazeworld1 maze = new Mazeworld1();
        maze.addMaze(world,RANDOM,ter);
        System.out.println("the maze is done, drawing");
        ter.renderFrame(world);
    }
}
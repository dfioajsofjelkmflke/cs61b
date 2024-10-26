package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static int HIGHT = 100;
    private static int WIDTH = 100;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    private static class Position{
        private int x ;
        private int y;
        private Position(int x, int y){
            this.x = x ;
            this.y = y ;
        }
    }
    private static int row_caculater(int i , int size){
        int row_number ;
        if(i < size){
            row_number = size + 2*i;
        }else{
            row_number = -2*i + 5*size-2;
        }
        return row_number;
    }
    private Position position_caculator(int i, int size, Position p){
        Position pos ;
        int row_number = row_caculater(i,size);
        int bias_x = (row_number-size)/2;
        pos = new Position(p.x-bias_x , p.y-i);
        return pos;
    }
    private static void fill_row(TETile[][] world,Position p, int number, TETile something){
        for(int x = p.x; x < p.x+number;x+=1){
            world[x][p.y]= something;
        }
    }
    public void addHex(TETile[][] world, Position p, int size, TETile something){
        int lines = size * 2;
        for(int i = 0; i < lines; i+=1){
            int number = row_caculater(i ,size);
            Position pos = position_caculator(i , size, p);
            fill_row(world , pos, number, something);
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
    public void addTessHex(TETile[][] world, Position p, int size , Random rand){
        Position p1 = down_right(p,size);
        Position p2 = down_left(p, size);
        Position p3 = down_right(p1,size);
        Position p4 = down_left(p2,size);
        add_down(world,p,size,5,rand);
        add_down(world,p1,size,4,rand);
        add_down(world,p3,size,3,rand);
        add_down(world,p2,size,4,rand);
        add_down(world,p4,size,3,rand);
        return;
    }
    private void add_down(TETile[][] world, Position p, int size,int times, Random rand){
        Position cur_pos = p;
        int i = 0;
        while(i<times){
            addHex(world, p, size, randomTile());
            p.y = p.y -size*2;
            i+=1;
        }
    }
    private Position down(Position p , int size){
        return new Position(p.x , p.y-2*size);
    }
    private Position down_right(Position p, int size){
        return new Position(p.x + 2*size-1, p.y-size);
    }
    private Position down_left(Position p, int size){
        return new Position(p.x-(2*size-1), p.y-size);
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
//        world[20][20] = Tileset.WALL;
        HexWorld hex = new HexWorld();
        Position position = new Position(50 , 60);
        hex.addTessHex(world,position,3,RANDOM);
        ter.renderFrame(world);
    }
}
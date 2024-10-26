package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
public class Mazeworld2 {
    private static int HIGHT = 41;
    private static int WIDTH = 61;
    private static final long SEED = 1723533;
    private static final Random RANDOM = new Random(SEED);
    public Mazeworld2(){
    }
    private static class Position{
        private int x ;
        private int y;;
        private Position next =null;
        private Position pre = null;
        private Position(int x, int y){
            this.x = x ;
            this.y = y ;
        }
    }
    private Position randomPosition(TETile[][] world, Random rand){
        /** choose a random place that is not filled with floor for entrance and exit */
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HIGHT);
        while(world[x][y] == Tileset.WALL){
            x= rand.nextInt(WIDTH);
            y= rand.nextInt(HIGHT);
        }
        return new Position(x,y);
    }
    private void buildwall(TETile[][] world){
        for(int i=0;i<WIDTH;i+=1){
            for(int j=0;j<HIGHT;j+=1){
                world[i][j] = Tileset.WALL;
            }
        }
    }
    private void divideMaze(TETile[][] world,Random rand){
        for(int i=0;i<WIDTH;i+=2){
            for(int j=0;j<HIGHT;j+=2){
                world[i][j] = Tileset.GRASS;
            }
        }
    }
    private static boolean posEqual(Position p1, Position p2, TETile[][] world){
        return world[p1.x][p1.y] == world[p2.x][p2.y];
    }
    private static Position[] accessablePosaround(Position[] allPos, Position cur, TETile[][] world){
        Position[] Pos;
        int accessable_numbers  = 0;
        for(Position p : allPos){
            if(p != null &&  ! accessed(p,world)){
                accessable_numbers +=1;
            }
        }
        Pos = new Position[accessable_numbers];
        int index =0 ;
        for(Position p : aroundPos(cur,world)){
            if(p != null && ! accessed(p,world)){
                Pos[index] = p;
                index +=1;
            }
        }
        return Pos;
    }
    private static boolean deadend(Position cur,TETile[][] world){
        for(Position p : aroundPos(cur,world)){
            if( p != null && ! accessed(p,world)){
                return false;
            }
        }
        return true;
    }
    private static boolean inMaze(Position p, TETile[][] world){
        return p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HIGHT;
    }
    private static boolean accessed(Position cur, TETile[][] world){
        TETile containing = world[cur.x][cur.y];
        return containing == Tileset.FLOOR || containing == Tileset.LOCKED_DOOR ;
    }
    private static Position next(Position cur, TETile[][] world, Random rand){
        Position[] Posaround = aroundPos(cur,world);
        Position[] accessablepos = accessablePosaround(Posaround,cur,world);
        int index = rand.nextInt(accessablepos.length);
        return accessablepos[index];
    }
    private static void dig(Position cur, Position next , TETile[][] world){
        Position wallbetween = new Position((cur.x+next.x)/2, (cur.y+next.y)/2);
        world[wallbetween.x][wallbetween.y] = Tileset.FLOOR;
//        int x_loss = next.x -cur.x;
//        int y_loss = next.y-cur.y;
//        if(x_loss == 0){
//            if(y_loss>0){
//                for(int j=0;j<=y_loss;j+=1){
//                    world[cur.x][cur.y+j] = Tileset.FLOOR;
//                }
//            }else if(y_loss <0) {
//                for(int j=0;j>=y_loss;j-=1){
//                    world[cur.x][cur.y+j] = Tileset.FLOOR;
//                }
//            }else{
//                return;
//            }
//        }else if(y_loss == 0){
//            if(x_loss > 0){
//                for(int i=0;i<=x_loss;i+=1){
//                    world[cur.x+i][cur.y] = Tileset.FLOOR;
//                }
//            }else if(x_loss < 0){
//                for(int i=0;i>=x_loss;i-=1){
//                    world[cur.x+i][cur.y] = Tileset.FLOOR;
//                }
//            }else{
//                return;
//            }
//        }
    }
    private static void fillGrass(TETile[][] world){
        for(int i=0;i<WIDTH;i+=1){
            for(int j=0;j<HIGHT;j+=1){
                if(world[i][j] == Tileset.GRASS){
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }
    private static Position[] aroundPos(Position cur,TETile[][] world){
        /** return the pos around that are both not accessed and not null; */
        Position[] PosList = new Position[4];
        Position up = new Position(cur.x,cur.y+2); // the up pos of cur;
        Position down = new Position(cur.x,cur.y-2); // the down pos of cur;
        Position left = new Position(cur.x -2, cur.y); // the left pos of cur;
        Position right = new Position(cur.x+2,cur.y); // the right pos of cur;
        PosList[0] = (inMaze(up,world) && ! accessed(up,world)) ? up : null;
        PosList[1] = (inMaze(down,world) && ! accessed(down,world)) ? down : null;
        PosList[2] = (inMaze(left,world) && ! accessed(left,world)) ? left : null;
        PosList[3] = (inMaze(right,world) && ! accessed(right,world))? right : null;
        return PosList;
    }
    private void addMaze(TETile[][] world,Random rand,TERenderer ter){
        /** add a maze to the world */
        buildwall(world); //fill the world with walls;
        divideMaze(world,rand); // divide the world ;
        ter.renderFrame(world);
        Position entrance =  randomPosition(world,rand);
        world[entrance.x][entrance.y] = Tileset.LOCKED_DOOR;
        Position exit = randomPosition(world,rand);  //define the entrance and the exit of the maze;
        world[exit.x][exit.y] = Tileset.UNLOCKED_DOOR;
        Position cur = entrance;
        while(! posEqual(cur,exit,world)){
            while(! deadend(cur,world) && !posEqual(cur,exit,world)){
                ter.renderFrame(world);
                System.out.println("current position is x : " + cur.x + " y : " + cur.y + " currentpos dead or not ? : " + deadend(cur,world));
                Position next = next(cur,world,rand);
                next.pre = cur;
                cur.next = next;
                dig(cur,next,world);
                cur = cur.next;
                world[cur.x][cur.y] = Tileset.FLOOR;
                ter.renderFrame(world);
                System.out.println("current position is x : " + cur.x + " y : " + cur.y + " currentpos dead or not ? : " + deadend(cur,world));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(posEqual(cur,exit,world)){
                break;
            }else{
                System.out.println("going back");
                cur = cur.pre;
            }
        }
        world[cur.x][cur.y] = Tileset.UNLOCKED_DOOR;
        fillGrass(world);
        return ;
    }
    public static void main(String[] args){
        /** initialize the tile rendering engine with a window of size WIDTH x HEIGHT */
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HIGHT);
//         initialize tiles
        TETile[][] world = new TETile[WIDTH][HIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Mazeworld2 maze = new Mazeworld2();
        maze.addMaze(world,RANDOM,ter);
        System.out.println("the maze is done, drawing");
        ter.renderFrame(world);
    }

}

package byog.Core;
import java.util.Arrays;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Map {
    private final int HIGHT ;
    private final int WIDTH ;
    private final TETile[][] world;
    private final Random RANDOM;
    public Position entrance;
    public Position exit;
    public Position monster;
    public Position token;
    private int mazeBaseBound;
    private int mazeLeftBound;
    private int mazeUpBound ;
    private int mazeRightBound;
    private int mazeWidth;
    private int mazeHeight;
    public Map(long seed, int WIDTH, int HIGHT, TETile[][] world){
        this.RANDOM = new Random(seed);
        this.WIDTH = WIDTH;
        this.HIGHT = HIGHT;
        this.world = world;
        this.mazeWidth = WIDTH-20;
        this.mazeHeight = HIGHT-10;
        this.mazeBaseBound = 5;
        this.mazeUpBound = HIGHT-5;
        this.mazeLeftBound = 10;
        this.mazeRightBound = WIDTH-10;
    }
    public class Position{
        int x;
        int y;
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public class Room{
        Position middlePosition ;
        boolean connected = false;
        Room pre = null;
        Room next = null;
        int width;
        int height;
        int leftBound;
        int rightBound;
        int upBound;
        int baseBound;
        public Room(Position middlePosition, int width, int height){
            this.middlePosition = middlePosition;
            this.width = width;
            this.height = height;
            this.leftBound = (width % 2 == 0) ? middlePosition.x - width/2 + 1 : middlePosition.x - width/2;
            this.rightBound = middlePosition.x + width/2;
            this.upBound = middlePosition.y + height/2;
            this.baseBound = (height % 2 == 0) ? middlePosition.y - height/2 + 1 : middlePosition.y - height/2;
        }
        private void connect(Room room){
            int x_loss = this.middlePosition.x - room.middlePosition.x; // x_loss <0 if current room is on the left of next room;
            int y_loss = this.middlePosition.y - room.middlePosition.y; // y_loss <0 if current room is on the bottom of next room;
            int x_step = (x_loss < 0) ? 1 : -1;
            int y_step = (y_loss < 0) ? 1 : -1;
            for(int x = this.middlePosition.x; x != room.middlePosition.x+x_step;x+=x_step){
                world[x][this.middlePosition.y] = Tileset.FLOOR;
            }
            for(int y = this.middlePosition.y; y != room.middlePosition.y+y_step;y+=y_step){
                world[room.middlePosition.x][y] = Tileset.FLOOR;
            }
            room.connected = true;
        }
        private Room closestRoom(Room[] rooms){
            int closestDistance = 100;
            Room closestRoom = rooms[0];
            for(Room room : rooms){
                if(this == room){
                    continue;
                }// skip itself;
                int distance = Math.abs(room.middlePosition.x - this.middlePosition.x) + Math.abs(room.middlePosition.y - this.middlePosition.y);
                if(distance < closestDistance && ! room.connected){
                    closestDistance = distance;
                    closestRoom = room;
                }
            }
            return closestRoom;
        }
        private boolean inMaze(){
            return this.leftBound >= mazeLeftBound && this.rightBound <= mazeRightBound && this.baseBound >= mazeBaseBound && this.upBound <= mazeUpBound;
        }
        private boolean overLap(){
            for(int x = leftBound; x < rightBound; x+=1){
                for(int y = baseBound; y < upBound; y+=1){
                    if(world[x][y].equals(Tileset.FLOOR)){
                        return true;
                    }
                }
            }
            return false;
        }
        private void fill(){
            for(int x = leftBound; x < rightBound; x+=1){
                for(int y = baseBound; y < upBound; y+=1){
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }
    private boolean isBound(Position position){
        TETile[] a = new TETile[]{world[position.x-1][position.y],world[position.x+1][position.y],world[position.x][position.y-1],world[position.x][position.y+1]};
        return Arrays.asList(a).containsAll(Arrays.asList(Tileset.WALL,Tileset.FLOOR,Tileset.NOTHING));
    }
    private Position addExit(){
        while(true){
            int x = RANDOM.nextInt(mazeWidth)+mazeLeftBound;
            int y = RANDOM.nextInt(mazeHeight)+mazeBaseBound;
            Position exit = new Position(x,y);
            if(isBound(exit)){
                world[x][y] = Tileset.LOCKED_DOOR;
                return new Position(x,y);
            }
        }
    }
    private Position addEntrance(){
        while(true){
            int x = RANDOM.nextInt(mazeWidth)+mazeLeftBound;
            int y = RANDOM.nextInt(mazeHeight)+mazeBaseBound;
            Position entrance = new Position(x,y);
            if(isBound(entrance)){
                world[x][y] = Tileset.UNLOCKED_DOOR;
                return entrance;
            }
        }
    }
    private boolean allConnected(Room[] rooms){
        for(Room room : rooms){
            if(room.connected == false){
                return false;
            }
        }
        return true;
    }
    private Room randomRoom(){
        while(true){
            Position roomPos = new Position(RANDOM.nextInt(mazeWidth)+mazeLeftBound,RANDOM.nextInt(mazeHeight)+mazeBaseBound);
            int width = RANDOM.nextInt(5)+3;
            int height = RANDOM.nextInt(5)+3;
            Room room = new Room(roomPos,width,height);
            if(room.inMaze() && ! room.overLap()){
                room.fill();
                return room;
            }
        }
    }
    public int[] addObjectPos(){
        int[] object = new int[2];
        while(true){
            int x = RANDOM.nextInt(mazeWidth)+mazeLeftBound;
            int y = RANDOM.nextInt(mazeHeight)+mazeBaseBound;
            if( ! world[x][y].equals(Tileset.FLOOR)){
                continue;
            }
            object[0] = x;
            object[1] = y;
            return object;
        }
    }
    private Room[] creatRooms(){
        int roomNumber = RANDOM.nextInt(10)+20;
        Room[] rooms = new Room[roomNumber];
        for(int i=0;i <roomNumber;i+=1){
            rooms[i] = randomRoom();
        }
        return rooms;
    }
    private void  buildWall(){
        /** for every single position that is floor, build wall around it, unless the position around itself is floor */
        for(int i=mazeLeftBound;i<mazeRightBound;i+=1){
            for(int j=mazeBaseBound;j<mazeUpBound;j+=1){
                if(world[i][j].equals(Tileset.FLOOR)){
                    world[i-1][j] = world[i-1][j].equals(Tileset.FLOOR) ? world[i-1][j] : Tileset.WALL;
                    world[i+1][j] = world[i+1][j].equals(Tileset.FLOOR) ? world[i+1][j] : Tileset.WALL;
                    world[i][j-1] = world[i][j-1].equals(Tileset.FLOOR) ? world[i][j-1] : Tileset.WALL;
                    world[i][j+1] = world[i][j+1].equals(Tileset.FLOOR) ? world[i][j+1] : Tileset.WALL;
                    world[i+1][j+1] = world[i+1][j+1].equals(Tileset.FLOOR) ? world[i+1][j+1] : Tileset.WALL;
                    world[i-1][j-1] = world[i-1][j-1].equals(Tileset.FLOOR) ? world[i-1][j-1] : Tileset.WALL;
                    world[i+1][j-1] = world[i+1][j-1].equals(Tileset.FLOOR) ? world[i+1][j-1] : Tileset.WALL;
                    world[i-1][j+1] = world[i-1][j+1].equals(Tileset.FLOOR) ? world[i-1][j+1] : Tileset.WALL;
                }
            }
        }
    }
    public void addMaze(){
        Room[] rooms = creatRooms();
        Room cur = rooms[0];
        cur.connected = true;
        while(! allConnected(rooms)){
            cur.next = cur.closestRoom(rooms);
            cur.connect(cur.next);
            cur = cur.next;
        }
        buildWall();
        this.entrance = addEntrance();
        this.exit = addExit();
    }
    public static void main(String[] args) {
        /** initialize the tile rendering engine with a window of size WIDTH x HEIGHT */
        TETile[][] world = new TETile[80][40];
        Map map = new Map(4282387,80,40,world);
        TERenderer ter = new TERenderer();
        ter.initialize(map.WIDTH, map.HIGHT);
//         initialize tiles
        for (int x = 0; x < map.WIDTH; x += 1) {
            for (int y = 0; y < map.HIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        map.addMaze();
        ter.renderFrame(world);
    }
}

package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40,seed);
        game.startGame();
    }

    public MemoryGame(int width, int height,long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }
    //TODO: Initialize random number generator
    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        char[] randchars = new char[n];
        for(int i=0;i<n;i+=1){
            randchars[i] = CHARACTERS[rand.nextInt(26)];
        }
        String randString = new String(randchars);
        return randString;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.setPenRadius(0.02);
        Font font = new Font("Large",Font.BOLD,30);
        StdDraw.setFont(font);
        StdDraw.text(width/2,height/2,s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        StdDraw.clear();
         char[] characters = letters.toCharArray();
         for(int i=0;i<letters.length();i+=1){
             StdDraw.pause(750);
             drawFrame(String.valueOf(characters[i]));
             StdDraw.pause(750);
         }
         System.out.println("Flashing Done");
         StdDraw.clear();
         System.out.println("cleared");
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String typedString = "";
        drawFrame(typedString);
//        while(StdDraw.hasNextKeyTyped()){
//            char key = StdDraw.nextKeyTyped();
//            typedString += String.valueOf(key);
//            drawFrame(typedString);
//        }
        while (typedString.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            typedString += String.valueOf(key);
            drawFrame(typedString);
        }
        return typedString;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
        round = 1;
        gameOver = false;
        while(! gameOver){
            StdDraw.text(width/2,width/2,"Round" + round);
            StdDraw.pause(1000);
            String letters = generateRandomString(round);
            flashSequence(letters);
            String typedinString = solicitNCharsInput(round);
            System.out.println("the typed in letters is " + typedinString);
            if(typedinString.equals( letters)){
                drawFrame("Good job !");
                StdDraw.pause(100);
            }else{
                drawFrame("Error !");
                StdDraw.pause(100);
                gameOver = true;
            }
            round +=1;
        }
        System.out.println("Done");
    }

}

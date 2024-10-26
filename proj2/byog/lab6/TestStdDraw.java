package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
public class TestStdDraw {
    public static void main(String[] args) {
        StdDraw.setScale(-2.0, +2.0);
        StdDraw.enableDoubleBuffering();

        for (double t = 0.0; true; t += 0.02) {
            double x = Math.sin(t);
            double y = Math.cos(t);
            StdDraw.clear();
            StdDraw.filledCircle(x, y, 0.1);
            StdDraw.filledCircle(-x, -y, 0.1);
            StdDraw.show();
            StdDraw.pause(20);
        }
//        StdDraw.setPenRadius(0.0);
//        StdDraw.setPenColor(StdDraw.BLUE);
////        StdDraw.point(0.6, 0.5);
//        StdDraw.setPenColor(StdDraw.MAGENTA);
////        StdDraw.line(0.2, 0.2, 0.8, 0.4);
////        StdDraw.circle(0.3,0.4,0.2);
////        StdDraw.filledCircle(0.5,0.5,0.3);
////        StdDraw.arc(0.5,0.5,0.2,90,180);
//        double[] x = {0.1,0.2,0.3,0.4};
//        double[] y = {0.3,0.3,0.2,0.5};
//        StdDraw.polygon(x,y);
//        StdDraw.textLeft(0.2,0.3,"hello world !");
    }
}

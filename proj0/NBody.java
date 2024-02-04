public class NBody {
    public static double readRadius(String string ){
        In in = new In(string);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String string ){
        In in = new In(string);
        int nums=in.readInt();
        in.readDouble();
        Planet[] allPlanets= new Planet[nums];
        for(int i = 0 ; i<nums;i++){
            allPlanets[i]=new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return allPlanets;
    }
    public static void main(String [] args){
        double T=Double.parseDouble(args[0]);
        double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        double radius=readRadius(filename);
        Planet[] allPlanets=readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        double current_t=0;
        while(current_t <= T){
            double[] xForce= new double[allPlanets.length];
            double[] yForce=new double[allPlanets.length];
            for(int i=0; i<allPlanets.length;i++){
                xForce[i]=allPlanets[i].calcNetForceExertedByX(allPlanets);
                yForce[i]=allPlanets[i].calcNetForceExertedByX(allPlanets);
            }
            for(int i=0; i<allPlanets.length;i++){
                allPlanets[i].update(dt,xForce[i],yForce[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet planet: allPlanets){
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            current_t=current_t+dt;
        }
        StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < allPlanets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
            allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName); 
        }

    }
}

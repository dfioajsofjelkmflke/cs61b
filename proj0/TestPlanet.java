public class TestPlanet {
    public static void main(String [] args){
        Planet planet_a= new Planet(1.0,0.0,2.0,3.0,4.0,"jupiter.gif");
        Planet planet_b=new Planet(1.5,2.5,3.5,4.5,5.5,"jupiter.gif");
        System.out.println(planet_a.calcDistance(planet_b));
        System.out.println(planet_a.calcForceExertedByX(planet_b));
        System.out.println(planet_a.calcForceExertedByY(planet_b));

    }
}

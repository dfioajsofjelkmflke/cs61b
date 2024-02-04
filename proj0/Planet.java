public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public Planet(double xP,double yP,double xV,double yV,double m, String img){
        this.xxPos=xP;
        this.yyPos=yP;
        this.xxVel=xV;
        this.yyVel=yV;
        this.mass=m;
        this.imgFileName=img;

    }
    public Planet(Planet b){
        this.xxPos=b.xxPos;
        this.yyPos=b.yyPos;
        this.xxVel=b.xxVel;
        this.yyVel=b.yyVel;
        this.mass=b.mass;
        this.imgFileName=b.imgFileName;
    }
    public double calcDistance(Planet target){
        double dx=target.xxPos-this.xxPos;
        double dy=target.yyPos-this.yyPos;
        return Math.sqrt(dx*dx+dy*dy);
    }
    public double calcForceExertedBy(Planet given_planet){
        return 6.67e-11*this.mass*given_planet.mass/(Math.pow(this.calcDistance(given_planet),2));
    }
    public double calcForceExertedByX(Planet given_planet){
        return this.calcForceExertedBy(given_planet)*(given_planet.xxPos-this.xxPos)/this.calcDistance(given_planet);
    }
    public double calcForceExertedByY(Planet given_planet){
        return this.calcForceExertedBy(given_planet)*(given_planet.yyPos-this.yyPos)/this.calcDistance(given_planet);
    }
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double total_force_x=0;
        for(Planet planet: allPlanets){
            if(this.equals(planet)){
                continue;
            }else{
                total_force_x+=this.calcForceExertedByX(planet);
            }
        }
        return total_force_x;

    }
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double total_force_y=0;
        for(Planet planet: allPlanets){
            if(this.equals(planet)){
                continue;
            }else{
                total_force_y+=this.calcForceExertedByY(planet);
            }
        }
        return total_force_y;
    }
    public void update(double dt,double fx,double fy){
        double a_x=fx/this.mass;
        double a_y=fy/this.mass;
        this.xxVel=this.xxVel+a_x*dt;
        this.yyVel=this.yyVel+a_y*dt;
        this.xxPos=this.xxPos+this.xxVel*dt;
        this.yyPos=this.yyPos+this.yyVel*dt;
    }
    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"images/"+this.imgFileName);
        StdDraw.show();
    }
}
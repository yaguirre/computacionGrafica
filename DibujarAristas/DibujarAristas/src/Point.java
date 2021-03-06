/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Keeps the information of a 2D point
 * @author htrefftz
 */
public class Point {
    double x;
    double y;
    double w;

    public Point(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getW(){
        return w;
    }
    
}

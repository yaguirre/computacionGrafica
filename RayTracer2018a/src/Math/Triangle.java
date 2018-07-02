/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

import Scene.Material;
import Scene.Colour;
import Scene.Shader;

/**
 *
 * @author Yorman Andres Aguirre Martinez
 * @author Santiago Baena Rivera
 */
public class Triangle implements Intersectable{
    Material material;
    Point p1,p2,p3;
    
    public Triangle(Material material, Point p1, Point p2, Point p3){
        this.material = material;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    public static void main(String [] args) {
        Material material = new Material();
        Triangle triangle = new Triangle(material,new Point(10, 10, -200d), new Point(10, -200d, 10), new Point(-200d, 10, 10));
        
        Ray ray1 = new Ray(new Point(0, 0, 0), new Point(0, 0, -10));
        System.out.println(triangle.intersect(ray1));
        
        Ray ray2 = new Ray(new Point(10, 0, 0), new Point(10, 0, -10));
        System.out.println(triangle.intersect(ray2));
        
        Ray ray3 = new Ray(new Point(5, 0, 0), new Point(5, 0, -10));
        System.out.println(triangle.intersect(ray3));
        
        Point punto = new Point(10, -200d, 10);
        System.out.println(triangle.computeNormal(punto));
    }
    
    @Override
    public Solutions intersect(Ray ray){
        double p1X = p1.getX();
        double p1Y = p1.getY();
        double p1Z = p1.getZ();
        
        double p2X = p2.getX();
        double p2Y = p2.getY();
        double p2Z = p2.getZ();
        
        double p3X = p3.getX();
        double p3Y = p3.getY();
        double p3Z = p3.getZ();
        
        ThreeByThreeSystem equ = new ThreeByThreeSystem( new double[][] {
            {ray.u.getX(), p1X-p2X, p1X-p3X},{ray.u.getY(), p1Y-p2Y, p1Y,p3Y},{ray.u.getZ(), p1Z-p2Z, p1Z-p3Z}},   
            new double[] {p1X-ray.p0.getX(),p1Y-ray.p0.getY(),p1Z-ray.p0.getZ()});
    	double [] resultados = equ.computeSystem();
    	double Sol = resultados[0];
    	double res1 = 1 - resultados[1] - resultados[2];
    	double res2 = resultados[1];
    	double res3 = resultados[2];

    	if(res1 >=0 && res1 <= 1 && res2 >= 0 && res2 <= 1 && res3 >= 0 && res3 <= 1)
            return new Solutions(1, Sol, 0);
    	else 
            return new Solutions(0, 0, 0);
    	
    }
    
    @Override
    public Material getMaterial() {
        return material;
    } 
    
    @Override
    public Colour callShader(Ray ray, double minT) {
        Vector4 vector2 = new Vector4(p1.getX(),p1.getY(),p1.getZ(),p3.getX(),p3.getY(),p3.getZ());
        Vector4 vector1 = new Vector4(p1.getX(),p1.getY(),p1.getZ(),p2.getX(),p2.getY(),p2.getZ());
        Vector4 normal = Vector4.crossProduct(vector1,vector2);
        normal.normalize();
        Point punto = ray.evaluate(minT);
        return Shader.computeColor(punto, normal, material);
    }
    
    @Override
    public Vector4 computeNormal(Point p) {
        Vector4 vector2 = new Vector4(p1.getX(),p1.getY(),p1.getZ(),p3.getX(),p3.getY(),p3.getZ());
        Vector4 vector1 = new Vector4(p1.getX(),p1.getY(),p1.getZ(),p2.getX(),p2.getY(),p2.getZ());
        Vector4 normal = Vector4.crossProduct(vector1,vector2);        
        return normal;
    }
}

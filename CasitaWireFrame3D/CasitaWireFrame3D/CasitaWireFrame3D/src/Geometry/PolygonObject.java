package Geometry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import Math.Matrix4x4;
import Math.Vector4;

/**
 * This class handles a collection of edges that form the object to be drawn
 * @author htrefftz
 */
public class PolygonObject {
    ArrayList<Edge> edges;
    
    boolean DEBUG = false;

    /**
     * Constructor
     */
    public PolygonObject() {
        edges = new ArrayList<>();
    }
    
    /**
     * Add an edge to the collection of edges
     * @param edge edge to be added
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    
    public ArrayList<Double> getDimensions(){
        double minimoX = edges.get(0).start.getX();
        double maximoX = edges.get(0).end.getX();
        double minimoY = edges.get(0).start.getY();
        double maximoY = edges.get(0).end.getY();
        double minimoZ = edges.get(0).start.getZ();
        double maximoZ = edges.get(0).end.getZ();
        ArrayList<Double> dimensiones = new ArrayList<>();
        for(int i = 0; i < edges.size();i++){
            
            if(edges.get(i).start.getX() < minimoX)
                minimoX = edges.get(i).start.getX();
            if(edges.get(i).end.getX() < minimoX)
                minimoX = edges.get(i).end.getX();
            if(edges.get(i).end.getX() > maximoX)
                maximoX = edges.get(i).end.getX();
            if(edges.get(i).start.getX() > maximoX)
                maximoX = edges.get(i).start.getX();
            
            if(edges.get(i).start.getY() < minimoY)
                minimoY = edges.get(i).start.getY();
            if(edges.get(i).end.getY() < minimoY)
                minimoY = edges.get(i).end.getY();
            if(edges.get(i).end.getY() > maximoY)
                maximoY = edges.get(i).end.getY();
            if(edges.get(i).start.getY() > maximoY)
                maximoY = edges.get(i).start.getY();
            
            if(edges.get(i).start.getZ() < minimoZ)
                minimoZ = edges.get(i).start.getZ();
            if(edges.get(i).end.getZ() < minimoZ)
                minimoZ = edges.get(i).end.getZ();
            if(edges.get(i).end.getZ() > maximoZ)
                maximoZ = edges.get(i).end.getZ();
            if(edges.get(i).start.getZ() > maximoZ)
                maximoZ = edges.get(i).start.getZ();
        }
        dimensiones.add(minimoX);
        dimensiones.add(maximoX);
        dimensiones.add(minimoY);
        dimensiones.add(maximoY);
        dimensiones.add(minimoZ);
        dimensiones.add(maximoZ);
        
        return dimensiones;
    }
    
    public void getMaximumX(){
        ArrayList<Edge> bordes = new ArrayList<>();
        for(Edge bordecito: edges){
            bordes.add(bordecito);
            System.out.println("Este es el borde: " + bordecito.toString());
        }
        //return bordes;
    }
    
    /**
     * Draw the lines composing the polygon
     * @param dc JPanel to draw the object on
     */
    public void drawObject(DibujarCasita3D dc) {
        for(Edge edge: edges) {
            drawOneLine(dc, edge);
        }
    }

    /**
     * Ask the JPanel to draw one of the edges composing the object
     * @param dc JPanel to draw on
     * @param edge Edge to draw
     */
    public void drawOneLine(DibujarCasita3D dc, Edge edge) {
        int x1 = (int)edge.start.getX();
        int y1 = (int)edge.start.getY();
        int x2 = (int)edge.end.getX();
        int y2 = (int)edge.end.getY();
        
        dc.drawOneLine(x1, y1, x2, y2);
    }
    
    /**
     * Transform an object given a transformation.
     * The vertices of the object are transformed.
     * @param transformation 
     */
    public void transformObject(Matrix4x4 transformation) {
        for(Edge e: edges) {
            if(DEBUG)
                System.out.println("Before: " + e);
            e.start = Matrix4x4.times(transformation, e.start);
            e.end   = Matrix4x4.times(transformation, e.end);
            if(DEBUG)
                System.out.println("After: " + e);
        }
    }
    
    public static PolygonObject transformObject(PolygonObject po, 
            Matrix4x4 transformation) {
        
        PolygonObject newObject = new PolygonObject();
        for(Edge e: po.edges) {
            Vector4 newStart = Matrix4x4.times(transformation, e.start);
            Vector4 newEnd = Matrix4x4.times(transformation, e.end);
            Edge newEdge = new Edge(newStart, newEnd);
            newObject.addEdge(newEdge);
        }
        return newObject;
    }
}

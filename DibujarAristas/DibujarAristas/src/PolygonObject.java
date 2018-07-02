/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * Keeps the information of a wire-frame object.
 * Edges are stored in an ArrayList
 * @author htrefftz
 */
public class PolygonObject {
    ArrayList<Edge> edges;
    Point[] vertexArray;
    
    public PolygonObject(Point [] vertexArray) {
        edges = new ArrayList<>();
        this.vertexArray = vertexArray;
    }
    
    public void add(Edge edge) {
        edges.add(edge);
    }
    
}

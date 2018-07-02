/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Project 1
 * Students have to replace method drawBresenhamLine with an implementation
 * of the Bresenham algorithm.
 * @author htrefftz
 */
public class BresenhamLine extends JPanel {

    public static int N = 0;
    public static final int R = 300;
    private final int pixelSize = 2;

    public static int width;
    public static int height;

    /**
     * Draw the lines. This is called by Java whenever it is necessary to draw
     * (or redraw) the panel
     * @param g Graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.magenta);

        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        width = w;
        height = h;
        int STEP = 360/N;

        for (int angle = 0; angle < 360; angle += STEP) {
            int others = angle + STEP;
            while(others < 360){
                double angleA = angle * Math.PI / 180d;
                double angleB = others * Math.PI /180d;
                MyPoint p1 = new MyPoint((int) (R * Math.cos(angleA)),(int) (R * Math.sin(angleA)));
                MyPoint p2 = new MyPoint((int) (R * Math.cos(angleB)),(int) (R * Math.sin(angleB)));
                drawBresenhamLine3(g2d, p1, p2);
                others+=STEP;
            }
        }
    }

    /** 
     * This has to be changed to an implementation of the Bresenham line
     * @param g2d graphics context
     * @param p1 beginning point of the line
     * @param p2 end point of the line
     */
    public void drawBresenhamLine3(Graphics2D g2d, MyPoint p1, MyPoint p2) {

        // Transform p1 and p2
        viewportTransf(p1);
        viewportTransf(p2);

        // draw the line
        BresenhamMethod(g2d,p1.x, p1.y, p2.x, p2.y);
    }
    
    /**
     * El algoritmo de Bresenham para dibujar una linea dado un punto inicial y un punto final descrito
     * durante la clase
     * @param g Grafico en el que sera dibujado la linea
     * @param x1 Coordenada en x del punto inicial
     * @param y1 Coordenada en y del punto incial
     * @param x2 Coordenada en x del punto final
     * @param y2 Coordenada en y del punto final
     */
    public void BresenhamMethod(Graphics g, int x1, int y1, int x2, int y2){
        int d = 0;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
        int x = x1;
        int y = y1;
        if (dx >= dy) {
            while (true) {
                g.drawOval(x, y, pixelSize, pixelSize);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                g.drawOval(x, y, pixelSize, pixelSize);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }

    /**
     * Transforms a point and then draws it on the panel
     * @param p point to be drawn
     * @param g2d graphics context
     */
    public void drawPoint(MyPoint p, Graphics2D g2d) {
        viewportTransf(p);
        g2d.drawLine(p.x, p.y, p.x, p.y);
    }

    /**
     * Transform a point to java coordinates: X grows from left to right and
     * Y grows from top to bottom
     * @param p Point to be transformed
     */
    public void viewportTransf(MyPoint p) {
        p.x += width / 2;
        p.y = height / 2 - p.y;
    }

    /**
     * Main program
     * @param args Not used in this case
     */
    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Bresenham");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Pedir al usuario el numero de puntos
        while(N<3){
            System.out.println("Ingrese el numero de puntos: ");
            Scanner numero = new Scanner(System.in);
            N = Integer.parseInt(numero.nextLine());
        }
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new BresenhamLine());
        // Asignarle tamaño
        frame.setSize(800, 800);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}

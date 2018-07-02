/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta es la Tarea para aplicar el algoritmo de Liang-Barsky
 * @author helmuthtrefftz
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JFrame;


/**
 *
 * @author helmuthtrefftz
 */
public class Clipping extends JPanel implements KeyListener  {

    int w = 0;
    int h = 0;
    Graphics2D g2d;
    int radius = 100;
    private final int pixelSize = 2;
    // Clipping Rectangle
    int minX = 50;
    int minY = -50;
    int maxX = 150;
    int maxY = 50;
    int xwMin = 0;
    int xwMax = 0;
    int ywMin = 0;
    int ywMax = 0;
    
    double theta = 0;
    double step = 1.0 * Math.PI / 180d;
    
    boolean DEBUG = true;
    
    public Clipping() {
        // El panel, por defecto no es "focusable". 
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);        
    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;


        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        w = size.width - insets.left - insets.right;
        System.out.println("El ancho es de: " + w);
        h = size.height - insets.top - insets.bottom;
        System.out.println("El alto es de: " + h);
        
        //drawConnectionGraph();
        drawClippingRectangle(minX, minY, maxX, maxY);
        drawTransformedLine(0, 0, (int)(radius * Math.cos(theta)), 
                (int)(radius * Math.sin(theta)));
    }
    
    public void drawClippingRectangle(int minX, int minY, int maxX, int maxY) {
        g2d.setColor(Color.RED);
        int x1 = transformX(minX);
        int y1 = transformY(maxY);
        int w1 = maxX - minX;
        int h1 = maxY - minY;
        xwMin = x1;
        xwMax = x1 + w1;
        ywMin = y1;
        ywMax = y1 + h1;
        g2d.drawRect(x1, y1, w1, h1);
    }
    
    public void drawTransformedLine(int x1, int y1, int x2, int y2) {
        g2d.setColor(Color.blue);

        int x1t = transformX(x1);
        int y1t = transformY(y1);
        int x2t = transformX(x2);
        int y2t = transformY(y2);
        liangBarskyAlgorithm(g2d,x1t,y1t,x2t,y2t);
    }
    
    public void liangBarskyAlgorithm(Graphics g,int x1,int y1,int x2, int y2){
        float Dx = x2 - x1;
        float Dy = y2 - y1;
        float p1 = -Dx;
        float p2 = Dx;
        float p3 = -Dy;
        float p4 = Dy;
        float q1 = x1 - xwMin;
        float q2 = xwMax - x1;
        float q3 = y1 - ywMin;
        float q4 = ywMax - y1;
        float [] posValues = new float[5];
        float [] negValues = new float[5];
        posValues[0] = 1;
        negValues[0] = 0;
        int negativeIndex = 1;
        int positiveIndex = 1;
        
        if(p1 != 0){
            float u1 = q1/p1;
            float u2 = q2/p2;
            if(p1 < 0){
                negValues[negativeIndex] = u1;
                posValues[positiveIndex] = u2;
                negativeIndex++;
                positiveIndex++;
            }else{
                negValues[negativeIndex] = u2;
                posValues[positiveIndex] = u1;
                negativeIndex++;
                positiveIndex++;
            }
        }
        if(p3 != 0){
            float u3 = q3/p3;
            float u4 = q4/p4;
            if(p3 < 0){
                negValues[negativeIndex] = u3;
                posValues[positiveIndex] = u4;
                negativeIndex++;
                positiveIndex++;
            }else{
                negValues[negativeIndex] = u4;
                posValues[positiveIndex] = u3;
                negativeIndex++;
                positiveIndex++;
            }
        }
        
        float t1 = maximoValor(negValues,negativeIndex);
        float t2 = minimoValor(posValues, positiveIndex);
        
        float xn1 = x1 + p2 * t1;
        float yn1 = y1 + p4 * t1;
        float xn2 = x1 + p2 * t2;
        float yn2 = y1 + p4 * t2;
        
        if(t1 > t2 || ((p1 == 0 && q1 < 0) || (p3 == 0 && q3 < 0))){
            g.setColor(Color.RED);
            BresenhamMethod(g,x1, y1, x2, y2);
        }else{
            g.setColor(Color.red);
            BresenhamMethod(g, Math.round(x1), Math.round(y1), Math.round(xn1), Math.round(yn1));
            g.setColor(Color.GREEN);
            BresenhamMethod(g, Math.round(xn1), Math.round(yn1), Math.round(xn2), Math.round(yn2));
            g.setColor(Color.red);
            BresenhamMethod(g, Math.round(xn2), Math.round(yn2), Math.round(x2), Math.round(y2));
        }       
        System.out.println("Xn1: " + xn1 + " Yn1: " + yn1 + " Xn2: " + xn2 + " Yn2: " + yn2);
    }
    
    public float maximoValor(float[] negativos, int n){
        float maximo = 0;
        for (int i = 1; i < n; i++) 
            maximo = Math.max(maximo, negativos[i]);
        return maximo;
    }
    
    public float minimoValor(float[] positivos, int n){
        float minimo = 1;
        for (int i = 1; i < n; i++) 
            minimo = Math.min(minimo, positivos[i]);
        return minimo;
    }
    
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
    
    public int transformX(int x) {
        return x + w/2;
    }
    
    public int transformY(int y) {
        return h/2 - y;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int tecla = e.getKeyCode();
        if(tecla == KeyEvent.VK_W) {
            theta += step;
        } else if (tecla == KeyEvent.VK_S) {
            theta -= step;
        }
        if(DEBUG) {
            System.out.println(theta + " ");
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    
    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Punto de partida clipping");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Clipping());
        // Asignarle tamaño
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

    
}


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author htrefftz
 */
public class Main extends JPanel  implements KeyListener {

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    public static double CENTER_OBJECT_X;
    public static double CENTER_OBJECT_Y;
    public static double STEPXFORWARD = 0;
    public static double STEPYFORWARD = 9;
    public static double STEPXBACKWARD = 0;
    public static double STEPYBACKWARD = -9;
    
    public int numVertices;
    
    PolygonObject po;
    
    boolean DEBUG = false;

    public Main() {
        super();
        // El panel, por defecto no es "focusable". 
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }
    /**
     * Draw the axis and the object
     * @param g 
     */
        @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawObject(g2d);
    }
    
    /**
     * Draw the wire-frame object
     * @param g2d Graphics2D context
     */
    private void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        for(Edge e: po.edges) {
            //Point p0 = e.p1;
            //Point p1 = e.p2;
            Point p0 = po.vertexArray[e.p1];
            Point p1 = po.vertexArray[e.p2];
            drawEdge(g2d, p0, p1);
        }
    }
    
    public PolygonObject translationObject(double dx, double dy){
        Point[] newVertexArray = new Point[numVertices];
        Matrix3x3 matrix = new Matrix3x3();
        matrix = matrix.setValuesTranslation(dx, dy);
        for(int i = 0; i < numVertices; i++){
            Point punto = Matrix3x3.times(matrix,po.vertexArray[i]);
            newVertexArray[i] = punto; 
        }
        PolygonObject transformedObject = new PolygonObject(newVertexArray);
        for(Edge edge: po.edges){
            transformedObject.add(edge);
        }
        return transformedObject;
    }
    
    public PolygonObject rotationObject(double theta){
        Point[] newVertexArray = new Point[numVertices];
        Matrix3x3 matrix = new Matrix3x3();
        matrix = matrix.setValuesRotation(theta);
        for(int i = 0; i < numVertices; i++){
            Point punto = Matrix3x3.times(matrix,po.vertexArray[i]);
            newVertexArray[i] = punto; 
        }
        PolygonObject transformedObject = new PolygonObject(newVertexArray);
        for(Edge edge: po.edges){
            transformedObject.add(edge);
        }
        return transformedObject;
    }
    
    public void foundCenterObject(){
        double minimoX = po.vertexArray[0].getX();
        double maximoX = po.vertexArray[0].getX();
        double minimoY = po.vertexArray[0].getY();
        double maximoY = po.vertexArray[0].getY();
        for(int i = 0; i < po.vertexArray.length;i++){
            if(po.vertexArray[i].getX() < minimoX) 
                minimoX = po.vertexArray[i].getX();
            if(po.vertexArray[i].getX() > maximoX) 
                maximoX = po.vertexArray[i].getX();
            if(po.vertexArray[i].getY() < minimoY) 
                minimoY = po.vertexArray[i].getY();                
            if(po.vertexArray[i].getY() > maximoY) 
                maximoY = po.vertexArray[i].getY();
        }
        CENTER_OBJECT_X = (minimoX + maximoX) / 2;
        CENTER_OBJECT_Y = (minimoY + maximoY) / 2;
    }

    /**
     * Draw an edge from p0 to p1
     * p0 and p1 are in world coordinates, need to be tranformed
     * to the viewpoint.
     * @param g2d Graphics2D context
     * @param p0 first point
     * @param p1 second point
     */
    private void drawEdge(Graphics2D g2d,Point p0, Point p1) {
        double x0 = p0.x + FRAME_WIDTH/2;
        double y0 = FRAME_HEIGHT/2 - p0.y ;
        double x1 = p1.x + FRAME_WIDTH/2;
        double y1 = FRAME_HEIGHT/2 - p1.y;
        g2d.drawLine(Math.round((float) x0), Math.round((float)y0), Math.round((float)x1), Math.round((float)y1));
    }

    /**
     * Read the object description:
     * n (number of vertices)
     * n vertices
     * m (number of edges)
     * m edges (index of first and second point to be linked)
     * @param fileName Name of the file to read the object description from
     */
    public void readObjectDescription(String fileName) {
        Scanner in;
        //po = new PolygonObject();
        try {
            in = new Scanner(new File(fileName));
            // Read the number of vertices
            numVertices = in.nextInt();
            Point[] vertexArray = new Point[numVertices];
            // Read the vertices
            for (int i = 0; i < numVertices; i++) {
                // Read a vertex
                int x = in.nextInt();
                int y = in.nextInt();
                vertexArray[i] = new Point(x, y, 1d);
            }
            po = new PolygonObject(vertexArray);
            // Read the number of edge  s
            int numEdges = in.nextInt();
            // Read the edges
            for (int i = 0; i < numEdges; i++) {
                // Read an edge
                int start = in.nextInt();
                int end = in.nextInt();
                //Edge edge = new Edge(vertexArray[start], vertexArray[end]);
                Edge edge = new Edge(start, end);
                po.add(edge);
            }
            // Read clipping area
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();
        
        if(tecla == KeyEvent.VK_D) {
            System.out.println("El usuario presiona d");
        } else if (tecla == KeyEvent.VK_UP) {
            po = translationObject(STEPXFORWARD,STEPYFORWARD);
        } else if (tecla == KeyEvent.VK_DOWN) {
            po = translationObject(STEPXBACKWARD,STEPYBACKWARD);
        } else if (tecla == KeyEvent.VK_LEFT) {
            //BAENA MODIFICA ESTO PARA QUE QUEDE GOOD
            foundCenterObject();
            if(STEPXFORWARD <= 0 && STEPYFORWARD >= 0){
                STEPXFORWARD--;
                STEPYFORWARD--;
            }else if(STEPXFORWARD <= 0 && STEPYFORWARD <= 0){
                STEPXFORWARD++;
                STEPYFORWARD--;
            }else if(STEPXFORWARD >= 0 && STEPYFORWARD <= 0){
                STEPXFORWARD++;
                STEPYFORWARD++;
            }else if(STEPXFORWARD >= 0 && STEPYFORWARD >=0){
                STEPXFORWARD--;
                STEPYFORWARD++;
            }
            
            STEPXBACKWARD++;
            STEPYBACKWARD++;
            po = translationObject(-CENTER_OBJECT_X, -CENTER_OBJECT_Y);
            po = rotationObject(10d * Math.PI / 180d);
            po = translationObject(CENTER_OBJECT_X, CENTER_OBJECT_Y);
        } else if (tecla == KeyEvent.VK_RIGHT) {
            //Esto Tambien
            foundCenterObject();
            STEPXFORWARD++;
            STEPYFORWARD--;
            STEPXBACKWARD--;
            STEPYBACKWARD++;
            po = translationObject(-CENTER_OBJECT_X, -CENTER_OBJECT_Y);
            po = rotationObject(-10d * Math.PI / 180d);
            po = translationObject(CENTER_OBJECT_X, CENTER_OBJECT_Y);
        } else if (tecla == KeyEvent.VK_X) {
            System.out.println("El usuario presiona x");
        } else if (tecla == KeyEvent.VK_O) {
            System.out.println("El usuario presiona o");
        } else if (tecla == KeyEvent.VK_L) {
            System.out.println("El usuario presiona l");
        }
        if (DEBUG) System.out.println(po);
        if (DEBUG) System.out.println("Aplicando una transformación");
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    
    
    /**
     * Main program
    */
    public static void main(String[] args) {
        Main m = new Main();

        // Read the file with the object description
        m.readObjectDescription("objeto.txt");

        // Create a new Frame
        JFrame frame = new JFrame("Wire Frame Object");
        // Upon closing the frame, the application ends
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a panel called DibujarCasita3D
        frame.add(m);

        // Asignarle tamaño
        frame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        // Put the frame in the middle of the window
        frame.setLocationRelativeTo(null);
        // Show the frame
        frame.setVisible(true);
    }

}

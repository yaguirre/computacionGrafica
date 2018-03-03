
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yorman Andres Aguirre Martinez
 * @author Santiago Baena Rivera
 */
public class Matrix3x3 {
    
    protected double[][] matrix = new double[3][3];
    
    public Matrix3x3(){
        matrix = new double[3][3];
        for(int i = 0; i < 3;i++){
            matrix[i][i] = 1d;
        }
    }
    
    public static Point times(Matrix3x3 matrix, Matrix3x3 ){  
        double componente;
        double componenteX = 0;
        double componenteY = 0;
        for(int i = 0; i < 3; i++){
            componente = 0;
            for(int j = 0; j < 3; j++){
                switch (j) {
                    case 0:
                        componente += matrix.getPos(i, j) * punto.getX();
                        break;
                    case 1:
                        componente += matrix.getPos(i, j) * punto.getY();
                        break;
                }
            }
            switch (i){
                case 0:
                    componenteX = componente;
                    break;
                case 1:
                    componenteY = componente;
                    break;
            }
        }
        Point puntico = new Point(componenteX, componenteY);
        return puntico;
    }
    
    public double getPos(int fila, int col){
        return matrix[fila][col];
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Matrix3x3 {
    protected double[][] matrix;
    
    /**
     * 
     */
    public Matrix3x3(){
        matrix = new double[3][3];
        for(int i = 0; i < 3; i++) {
            matrix[i][i] = 1d;
        }
    }
    
    public Matrix3x3 setValuesTranslation(double dx, double dy){
        this.matrix[0][2] = dx;
        this.matrix[1][2] = dy;
        return this;
    }
    
    public Matrix3x3 setValuesRotation(double theta){
        this.matrix[0][0] = Math.cos(theta);
        this.matrix[0][1] = -Math.sin(theta);
        this.matrix[1][0] = Math.sin(theta);
        this.matrix[1][1] = Math.cos(theta);
        return this;
    }
    
    /**
     * 
     * @param matrix 
     */
    public Matrix3x3(double [][] matrix) {
        this.matrix = matrix;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return 
     */
    public double get(int row, int col) {
        return matrix[row][col];
    }
    
    /**
     * 
     * @param m1
     * @param m2
     * @return 
     */
    public static Matrix3x3 times(Matrix3x3 m1, Matrix3x3 m2) {
        double [][] newMatrix = new double[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                double acum = 0;
                for(int k = 0; k < 4; k++) {
                    acum += m1.get(i, k) * m2.get(k, j);
                }
                newMatrix[i][j] = acum;
            }
        }
        return new Matrix3x3(newMatrix);
    }
    
    public static Point times(Matrix3x3 matrix, Point punto){  
        double componente;
        double componenteX = 0;
        double componenteY = 0;
        double componenteW = 0;
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
                    default:
                        componente += matrix.getPos(i, j) * punto.getW();
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
                default:
                    componenteW = componente;
                    break;
            }
        }
        Point puntico = new Point(componenteX, componenteY, componenteW);
        return puntico;
    }
    
    public double getPos(int fila, int col){
        return matrix[fila][col];
    }
}

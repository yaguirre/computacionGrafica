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
public class Scaling extends Matrix3x3{
    
    public Scaling() {
        super();
    }
    
    public Scaling(double dx, double dy){
        super();
        matrix[0][0] = dx;
        matrix[1][1] = dy;        
        matrix[2][2] = 1;
    }
}

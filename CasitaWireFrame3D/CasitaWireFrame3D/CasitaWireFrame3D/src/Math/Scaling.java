/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author Yorman Andres Aguirre Martinez
 */
public class Scaling extends Matrix4x4{
    
    public Scaling() {
        super();
    }
    
    public Scaling(double dx, double dy, double dz) {
        super();
        matrix[0][0] = dx;
        matrix[1][1] = dy;
        matrix[2][2] = dz;
        matrix[3][3] = 1;
    }
}

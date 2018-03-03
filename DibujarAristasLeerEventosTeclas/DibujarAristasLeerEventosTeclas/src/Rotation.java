/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Rotation extends Matrix3x3{
    
    public Rotation(){
        super();
    }
    
    public void RotationX(double theta){
        double [][] matricita = {
            {Math.cos(theta), -Math.sin(theta), 0d},
            {Math.sin(theta), Math.cos(theta), 0d},
            {0d, 0d, 1d},
        };
        matrix = matricita;
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model.elements;

/**
 *
 * @author mugarov
 */
public class Coordinates implements Comparable{
    private final double x;
    private final double y;
    private final boolean isLog;
    
    
    public Coordinates(double x, double y){
        this.x = x;
        this.y = y;
        this.isLog = false;
    }
    
    public Coordinates(double x, double y, boolean isLog){
        this.x = x;
        this.y = y;
        this.isLog = isLog;
    }
    
    
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public double getAbsolutX(){
        if(!this.isLog){
            return this.x;
        }
        else{
            return this.getExp(this.x);
        }
    }
    
    public double getAbsolutY(){
        if(!this.isLog){
            return this.y;
        }
        else{
            return this.getExp(this.y);
        }
    }
    
    public double getLogX(){
         if(this.isLog){
            return this.x;
        }
        else{
            return this.getLog(this.x);
        }
    }
    
     public double getLogY(){
         if(this.isLog){
            return this.y;
        }
        else{
            return this.getLog(this.y);
        }
    }
    
    
    public boolean isLog(){
        return this.isLog;
    }
    
    private double getExp(double logVal){
        return Math.pow(Dot.BASE, logVal);
    }
    
    private double getLog(double realVal){
        return Math.log(realVal)/Math.log(Dot.BASE);
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Coordinates){
            if(this.x>((Coordinates)o).getAbsolutX() && this.y<((Coordinates)o).getAbsolutY()){
                return -1;
            }
            else if(this.x==((Coordinates)o).getAbsolutX() && this.y==((Coordinates)o).getAbsolutY()){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            return 0;
        }
    }
}

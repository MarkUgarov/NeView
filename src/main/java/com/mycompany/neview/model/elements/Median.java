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
public class Median {
    
    private Coordinates start;
    private Coordinates end;
    private String name;
    private double gradient;
    private double antiGradient;
    
    
//    /**
//     * Creates a line with gradient 1.
//     * @param b
//     * @param maxCoverage
//     * @param name
//     * @param factor 
//     */
//    public Median(double b, double maxCoverage, double maxLength, String name, int factor){
//        this(b*factor, maxCoverage, maxLength, name);
//    }
    
    public Median(double averageX, double averageY, double maxX, double maxY, String name, int factor){
        this(averageX,averageY,maxX,maxY,name,factor,averageY/averageX);
    }
    
    public Median(double averageX, double averageY, double maxX, double maxY, String name, double factor, double gradient){
        this.name = name;
        double factorisedY = factor*averageY;
        this.gradient = gradient;
        this.antiGradient = 1/gradient;
        System.out.println("Factor "+factor);
        System.out.println("\tGradient: "+this.gradient);
        System.out.println("\tAverage X: "+averageX);
        System.out.println("\tAverage Y: "+averageY+", factorized x"+factor+"="+factorisedY);
        
        double xA = averageX;
        double yA = factorisedY;
        double x0 = xA-(yA/this.gradient); // intersection of the line with x-axis
        double y0 = yA-(this.gradient * xA); // intersection of the line  with y-axis
        double xS = Math.max(0, x0); // actual start of the median
        double yS = Math.max(0, y0); 
        
        double xM = maxX;
        double yM = maxY;
        double xE = xA +((yM -yA)/this.gradient); // intersection of the line with maxY
        double yE = yA +((xM-xA)*this.gradient); // intersection of the line with maxX

        this.start = new Coordinates(xS,yS);
        this.end = new Coordinates(Math.min(xE, xM), Math.min(yE, yM));
    }
    
    
    
    private double getXOn(double y){
        return (Math.abs(y-this.start.getY()) /this.gradient)+this.start.getX();
    }
    
    private double getYOn(double x){
        return (Math.abs(x-this.start.getX())*this.gradient)+this.start.getY();
    }
    
    private double log(int base, double value){
        return Math.log(value)/Math.log(base);
    }
    
    
    public Coordinates getStart(){
        return this.start;
    }
    
    
    public Coordinates getEnd(){
        return this.end;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean isOutOfBounds(double maxLength, double maxCoverage){
        if(this.start.getY() > maxCoverage ||this.start.getX() > maxLength){
            System.out.println("Median of bounds: "+this.start.getX()+">"+maxLength+" or "+this.start.getY()+">"+maxCoverage);
            return true;
        }
//        if(this.end.getY() > maxCoverage || this.end.getX() > maxLength){
//            return true;
//        }
        return false;
    }
    
    public boolean isUnderLine(Dot dot){
        double deltaX = this.getDeltaX(dot);
        double deltaY = this.getDeltaY(dot);
        return (deltaX >= 0 && deltaY>=0);
    }
    
    private double getDeltaX(Dot dot){
        return dot.getLogX()-this.getXOn(dot.getLogY());
       
    }
    
    private double getDeltaY(Dot dot){
        return this.getYOn(dot.getLogX()-dot.getLogY());
    }
    
}

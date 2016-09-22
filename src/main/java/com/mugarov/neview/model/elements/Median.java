/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.model.elements;

import com.mugarov.neview.model.MathHelp;

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
    
    private final double factor;
    private final double averageX;
    private final double averageY;
    private final double factorisedY;
    
    
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
        this.factor = factor;
        this.averageX = averageX;
        this.averageY = averageY;
        this.factorisedY = factor*averageY;
        this.gradient = gradient;
        this.antiGradient = 1/gradient;
        
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

        this.start = new Coordinates(xS,yS, true);
        this.end = new Coordinates(Math.min(xE, xM), Math.min(yE, yM), true);
    }
    
    
    /** 
     * Calculating the x- Position on the line on the same high as the dot.
     * @param x X-Coordinate of the dot
     * @param y Y-Coordinate of the dot
     * @return a positive or negative value
     */
    private double getXOn(double x, double y){
        double yPos = Math.abs(x-this.start.getX())*gradient+this.start.getY();
        double deltaOfY = Math.abs(yPos-y); // not a delta as in getDeltaY
        return x-(deltaOfY/gradient);
    }
    
    /** 
     * Calculating the y- Position on the line on the same high as the dot.
     * @param x X-Coordinate of the dot
     * @param y Y-Coordinate of the dot
     * @return 
     */
    private double getYOn(double x, double y){
        return (Math.abs(x-this.start.getX())*gradient)+this.start.getY();
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
//            System.out.println("Median of bounds: "+this.start.getX()+">"+maxLength+" or "+this.start.getY()+">"+maxCoverage);
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
        // 122 157
    }
    
    private double getDeltaX(Dot dot){
        return dot.getLogX()-this.getXOn(dot.getLogX(), dot.getLogY());
       
    }
    
    private double getDeltaY(Dot dot){
        return this.getYOn(dot.getLogX(), dot.getLogY())-dot.getLogY();
    }
    
    public double getFactor(){
        return this.factor;
    }
    
    public double getFacorisedX(){
        return (this.averageX*this.factor*this.antiGradient);
    }
    
    
    public double getFactorisedY(){
        return this.factorisedY;
    }
    
    public double getExponentialFactorisedX(){
        return (MathHelp.getExponential(this.averageX)*this.factor*this.antiGradient);
    }
    
    public double getExponentialFactorisedY(){
        return (MathHelp.getExponential(this.averageY)*this.factor);
    }
    
    public Coordinates getStartCoordinates(){
        return this.start;
    }
    
    public Coordinates getEndCoordinates(){
        return this.end;
    }
    
 
    
}

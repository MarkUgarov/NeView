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
    
    /**
     * Creates a line with gradient 1.
     * @param averageCoverage
     * @param maxCoverage
     * @param name
     * @param factor 
     */
    public Median(double averageCoverage, double maxCoverage, double maxLength, String name, int factor){
        this(averageCoverage*factor, maxCoverage, maxLength, name);
    }
    
    /**
     * Creates a line with gradient 1 starting from the average y-Value.
     * @param averageCoverage
     * @param maxCoverage
     * @param name 
     */
    public Median(double averageCoverage, double maxCoverage, double maxLength, String name){
        double xEnd = maxCoverage-averageCoverage;
        double yEnd = maxCoverage;
        if(xEnd <= maxLength){
            this.start = new Coordinates(0,averageCoverage);
            this.end = new Coordinates(xEnd, yEnd);
        }
        else{
            double shorteningFactor= xEnd / maxLength;
            yEnd = averageCoverage + (xEnd*shorteningFactor);
            xEnd = maxLength;
            this.start = new Coordinates(0,averageCoverage);
            this.end = new Coordinates(xEnd, yEnd);
            
        }
        this.name = name;
//        System.out.println("Created line with gradient "+(xEnd/(yEnd-averageCoverage)));
        
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
            return true;
        }
        if(this.end.getY() > maxCoverage || this.end.getY() > maxLength){
            return true;
        }
        return false;
    }
    
    public boolean isUnderLine(Dot dot){
        if(dot.getY()<this.start.getY()){
            return true;
        }
        double deltaX = this.getDeltaX(dot);
        double deltaY = this.getDeltaY(dot);
        return (deltaX >= 0 && deltaY>=0);
    }
    
    private double getDeltaX(Dot dot){
        double yStart = this.getStart().getY();
        double yEnd = this.getEnd().getY();
        double comparisonHigh = dot.getY();
        double comparisonWidth = comparisonHigh - yStart;
        return dot.getX() - comparisonWidth;
        
       
    }
    
    private double getDeltaY(Dot dot){
        double yStart = this.getStart().getY();
        double yEnd = this.getEnd().getY();
        double comparisonWidth = dot.getX();
        double comparisonHigh = comparisonWidth + yStart;
        return comparisonHigh - dot.getY();
    }
    
}

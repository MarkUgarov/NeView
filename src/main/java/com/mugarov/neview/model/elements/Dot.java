/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.model.elements;

import org.jfree.data.xy.XYDataItem;

/**
 *
 * @author mugarov
 */
public class Dot{
    public static final int BASE= 2;
    
    private final Coordinates coordinates;
    private final String name;
    
    private String scaffold;
    private String median;
    
    private Dot(Coordinates location, String name){
        this.coordinates = location;
        int base = Dot.BASE;
       
        this.name = name;
        this.scaffold = null;
    }
    
    /**
     * 
     * @param x should be the length 
     * @param y should be the coverage
     * @param name should be something like "contig1" or so
     */
    public Dot(double x, double y, String name){
        this(new Coordinates(x,y), name);
    }
    
    public double getX(){
        return this.coordinates.getX();
    }
    
    public double getY(){
        return this.coordinates.getY();
    }
    
    public double getLogX(){
        return this.coordinates.getLogX();
    }
    
    public double getLogY(){
        return this.coordinates.getLogY();
    }
    
    public Coordinates getCoordinates(){
        return this.coordinates;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setScaffold(String name){
        this.scaffold = name;
    }
    
    public String getScaffoldName(){
        return this.scaffold;
    }
    
    public void setMedian(String name){
        this.median = name;
    }
    
    public String getMedian(){
        return this.median;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Dot){
            return this.name.equals(((Dot) o).getName());
        }
        else if(o instanceof String){
            return this.name.equals((String)o);
        }
        else{
            return super.equals(o);
        }
    }
    
}

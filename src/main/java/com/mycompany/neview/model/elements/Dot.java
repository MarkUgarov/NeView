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
public class Dot {
    
    private final Coordinates coordinates;
    private final String name;
    
    private Dot(Coordinates location, String name){
        this.coordinates = location;
        this.name = name;
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
    
}

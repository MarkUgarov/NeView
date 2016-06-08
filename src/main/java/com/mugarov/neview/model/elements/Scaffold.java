/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.model.elements;

import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class Scaffold {
    
    private final String name;
    private final ArrayList<String> contigNames;
    private final ArrayList<Dot> dots;
    
    public Scaffold(String name){
        this.name = name;
        this.contigNames = new ArrayList<>();
        this.dots = new ArrayList<>();
    }
    
    public Scaffold(String name, String initEntry){
        this(name);
        this.contigNames.add(initEntry);
    }
    
    public String getName(){
        return this.name;
    }
    
    public void add(String contigName){
        this.contigNames.add(contigName);
    }
    
    public void add(Dot dot){
        this.dots.add(dot);
    }
    
    public boolean containsName(String contigName){
        return this.contigNames.contains(contigName);
    }
    
    public boolean containsDot(Dot d){
        return this.dots.contains(d);
    }
    
}

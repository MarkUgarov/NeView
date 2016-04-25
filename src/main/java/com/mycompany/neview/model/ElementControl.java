/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model;

import com.mycompany.neview.model.elements.Median;
import com.mycompany.neview.model.elements.Dot;
import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public class ElementControl {
    
    
    private final ArrayList<Dot> dots;
    private final ArrayList<Median> lines;
    
    public ElementControl(){
        this.dots = new ArrayList<>();
        this.lines = new ArrayList<>();
    }
    
    public void addDot(Dot dot){
        this.dots.add(dot);
    }
    
    public void addLine(Median line){
        this.lines.add(line);
    }
    
    public ArrayList<Dot> getDots(){
        return this.dots;
    }
    
    public ArrayList<Median> getLines(){
        return this.lines;
    }
    
    public void sort(){
        this.sortDots();
        this.sortLines();
    }
    
    public void sortDots(){
        
    }
    
    public void sortLines(){
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mugarov
 */
public class ColorGenerator {
    
    private final ArrayList<Color> colors;
    private final int current;
    private final Random rand;
     
    public ColorGenerator(){
        this.colors = new ArrayList<>();
        this.current = 0;
        this.rand = new Random();
        this.colors.add(Color.red);
        this.colors.add(Color.blue);
        this.colors.add(Color.green);
        this.colors.add(Color.CYAN);
        this.colors.add(Color.lightGray);
        this.colors.add(Color.black);
    }
    
    public void add(Color color){
        this.colors.add(color);
    }
    
    public Color get(int index){
        while(this.colors.size() <= index){
            this.colors.add(this.createRandom());
        }
        return this.colors.get(index);
    }
    
    private Color createRandom(){
        return new Color(this.rand.nextInt(256), this.rand.nextInt(256), this.rand.nextInt(256));
    }
    
    
    
}

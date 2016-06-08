/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view.datarepresentation;

import com.mugarov.neview.model.elements.Coordinates;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author mugarov
 */
public class CoordinateSeries extends XYSeries{
    
    private final String name;
    private final ArrayList<Coordinates> coordinates;
    private final ArrayList<String> names;
    private final ArrayList<String> scaffolds;

    public CoordinateSeries(Comparable key) {
        super(key,false);
        this.name = key.toString();
        this.coordinates = new ArrayList<>();
        this.names = new ArrayList<>();
        this.scaffolds = new ArrayList<>();
    }
    
    
    public void add(Coordinates coordinates, String name, String scaffold){
        super.add(coordinates.getLogX(), coordinates.getLogY());
        this.coordinates.add(coordinates);
        this.names.add(name);
        if(scaffold != null){
            this.scaffolds.add(scaffold);
        }
        else{
            this.scaffolds.add("");
        }
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getXValue(int category){
        return this.coordinates.get(category).getAbsolutX();
    }
    
    public double getYValue(int category){
        return this.coordinates.get(category).getAbsolutY();
    }
    
    public String getName(int category){
        return this.names.get(category);
    }
    
    public String getScaffold(int category){
        return this.scaffolds.get(category);
    }
    
}

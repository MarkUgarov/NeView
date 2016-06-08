/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view;

import com.mugarov.neview.model.elements.Median;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class StatisticsPanel extends JPanel{

    
    
    public StatisticsPanel(){
        this.setLayout(new GridLayout(0,3));
    }
    
    public void addValues(ArrayList<Median> medians){
        for(Median m:medians){
            this.add(new JLabel("Median with factor "+m.getFactor()+":"));
            this.add(new JLabel("Associated Length: "+m.getExponentialFactorisedX()));
            this.add(new JLabel("\u2205 Reads count: "+m.getExponentialFactorisedY()));
            
        }
    }
    
    
    
}

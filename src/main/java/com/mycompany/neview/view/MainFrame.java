/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import com.mycompany.neview.model.elements.Dot;
import com.mycompany.neview.model.elements.Median;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author mugarov
 */
public class MainFrame extends JFrame {
    
    private final SplitetPane split;
    private final ListPanel list;
    private final GraphPanel graph;
    
    public MainFrame(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.graph = new GraphPanel();
        this.list = new ListPanel();
        this.split = new SplitetPane(graph,list);
        this.add(split);
        this.setSize(500, 600);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        this.setVisible(true);
    }
    
    public void setValues(ArrayList<Dot> dots, ArrayList<Median> lines, String name){
        this.graph.setValues(dots, lines, name);
        this.list.setValues(dots, lines);
        this.split.checkWidth();
    }
}

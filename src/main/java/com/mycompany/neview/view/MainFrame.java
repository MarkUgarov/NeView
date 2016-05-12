/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import com.mycompany.neview.model.elements.DotBag;
import com.mycompany.neview.model.elements.Median;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author mugarov
 */
public class MainFrame extends JFrame {
    
    private final SplitetPane split;
    private final ListPanel list;
    private final GraphPanel graph;
    private final JPanel right;
    private final StatisticsPanel statistics;
    
    public MainFrame(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("NeView");
        this.graph = new GraphPanel();
        this.right = new JPanel();
        this.right.setLayout(new BorderLayout());
        this.statistics = new StatisticsPanel();
        this.list = new ListPanel();
        this.right.add(this.statistics, BorderLayout.NORTH);
        this.right.add(this.list, BorderLayout.CENTER);
        this.split = new SplitetPane(graph,right);
        this.add(split);
        this.setSize(500, 600);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        this.setVisible(true);
    }
    
    public void setValues(ArrayList<DotBag> dotBags, ArrayList<Median> lines, String name){
        this.graph.setValues(dotBags, lines, name);
        this.list.setValues(dotBags, lines, true);
        this.statistics.addValues(lines);
        this.split.checkWidth();
        this.setTitle("NeView - "+name);
    }
}

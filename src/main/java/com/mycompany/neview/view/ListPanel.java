/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import com.mycompany.neview.model.elements.Dot;
import com.mycompany.neview.model.elements.Median;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author mugarov
 */
public class ListPanel extends JPanel{
    
    private Object[][] values;
    private final String[] header = new String[]{"Length", "Coverage"};
    private final JScrollPane scroll;
    private JTable table;

  

    
    public ListPanel(){
        this.setBackground(Color.red);
        this.scroll = new JScrollPane();
        this.add(this.scroll);
    }
    
    public void setValues(ArrayList<Dot> dots, ArrayList<Median> lines){
        boolean add = false;
        if(dots != null){
            this.values = new Object[dots.size()][2];
            int i = 0;
            
            for(Dot dot: dots){
                this.values[i][0] = dot.getX();
                this.values[i][1] = dot.getY();
//                System.out.println("Added Dot "+values[i][0]+", "+values[i][1]);
                i++;
            }
        }
//        else{
//            this.values = new String[][]{{"Empty", "Empty"}};
//        }
        if(this.table == null){
            add = true;
        }
        this.table = new JTable(this.values, this.header);
        if(add){
            this.scroll.add(this.table);
        }
        this.table.setEnabled(false);
        
        TableRowSorter rowSorter = new TableRowSorter(new DefaultTableModel(this.values,this.header));
        rowSorter.setComparator(0, new DoubleComperator());
        this.table.setRowSorter(rowSorter);
        
        this.scroll.setViewportView(this.table);
        
        
        this.setBackground(Color.GREEN);
        this.updateUI();
    }
    
    private class DoubleComperator implements Comparator {
            @Override
            public int compare(Object o1, Object o2) {
                double d1 = (double)o1;
                double d2 = (double)o2;
                return Double.compare(d1, d2);
            } 
        }
    
    @Override
    public void paintComponent(Graphics g){
        if(this.table != null){
            this.scroll.setPreferredSize(new Dimension(
                                                    Math.max(this.table.getPreferredScrollableViewportSize().width, 500),
                                                    Math.max(this.table.getPreferredScrollableViewportSize().height, this.getSize().height)
                                        )
            );
        }
        
        super.paintComponent(g);
    }


    
}

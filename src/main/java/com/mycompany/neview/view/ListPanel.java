/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import com.mycompany.neview.model.elements.Dot;
import com.mycompany.neview.model.elements.DotBag;
import com.mycompany.neview.model.elements.Median;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author mugarov
 */
public class ListPanel extends JPanel{
    
    private Object[][] values;
    
    private final JScrollPane scroll;
    private JTable table;

    private final String[] header = new String[5];
    public static final int POSITION_NAME = 0;
    public static final int POSITION_LENGTH = 1;
    public static final int POSITION_READS = 2;
    public static final int POSITION_SCAFFOLD = 3; 
    public static final int POSITION_MEDIAN = 4;
  

    
    public ListPanel(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
        this.scroll = new JScrollPane();
        this.add(this.scroll, BorderLayout.CENTER);
        this.header[POSITION_NAME] = "Name";
        this.header[POSITION_LENGTH] = "Length";
        this.header[POSITION_READS] = "Reads";
        this.header[POSITION_SCAFFOLD] = "Scaffold";
        this.header[POSITION_MEDIAN] = "Median";
        
    }
    
    public void setValues(ArrayList<DotBag> dotBags, ArrayList<Median> median, boolean mix){
        ArrayList<Dot> dots = new ArrayList<>();
        for(DotBag db:dotBags){
            dots.addAll(db);
        }
        this.setValues(dots, median);
    }
    
    public void setValues(ArrayList<Dot> dots, ArrayList<Median> lines){
        boolean add = false;
        if(dots != null){
            this.values = new Object[dots.size()][this.header.length];
            int i = 0;
            
            for(Dot dot: dots){
                this.values[i][POSITION_NAME] = (String) dot.getName();
                this.values[i][POSITION_LENGTH] = (long) dot.getX();
                this.values[i][POSITION_READS] = (long) dot.getY();
                this.values[i][POSITION_SCAFFOLD] = (String) dot.getScaffoldName();
                this.values[i][POSITION_MEDIAN] = (String) dot.getMedian();
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
        rowSorter.setComparator(POSITION_LENGTH, new LongComparator());
        rowSorter.setComparator(POSITION_READS, new LongComparator());
        this.table.setRowSorter(rowSorter);
        
        this.scroll.setViewportView(this.table);
        
        
        this.setBackground(Color.GREEN);
        this.updateUI();
    }
    
    public TableModel getTableModel(){
        return this.table.getModel();
    }
    
    private class DoubleComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            double d1 = (double)o1;
            double d2 = (double)o2;
            return Double.compare(d1, d2);
        } 
    }
    
    private class LongComparator implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            long d1 = (long)o1;
            long d2 = (long)o2;
            return Long.compare(d1, d2);
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

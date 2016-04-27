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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;

 import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
 import org.jfree.data.xy.XYSeries;
 import org.jfree.data.xy.XYSeriesCollection;
 import org.jfree.ui.ApplicationFrame;
 import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author mugarov
 */
public class GraphPanel extends JPanel {
    
    private JScrollPane scroll;
    private JPanel content;
    private ArrayList<XYSeries> dotSeries;
    private ArrayList<XYSeries> lineSeries;
    private XYSeriesCollection dataSet;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    
    public GraphPanel(){
        super(new BorderLayout());
        super.setBackground(Color.blue);
       
        
        this.content = new JPanel(new BorderLayout());
        this.scroll = new JScrollPane(this.content);
        
        this.add(this.scroll, BorderLayout.CENTER);
        this.dotSeries = new ArrayList<>();
        this.lineSeries = new ArrayList<>();
    }
    
    public void setValues(ArrayList<DotBag> dotBags, ArrayList<Median> lines, String name){
        
        XYSeries ser;
        for(DotBag db:dotBags){
            
            ser = new XYSeries(db.getName());
            for(Dot d:db){
                ser.add(d.getX(), d.getY());
            }

            this.dotSeries.add(ser);
        }
        
        
        for(Median med:lines){
            ser = new XYSeries(med.getName());
            ser.add(med.getStart().getX(), med.getStart().getY());
            ser.add(med.getEnd().getX(), med.getEnd().getY());
            this.lineSeries.add(ser);
        }
        
        this.dataSet = new XYSeriesCollection();
        
        
        for(XYSeries dot:this.dotSeries){
            this.dataSet.addSeries(dot);
        }
        for(XYSeries line:this.lineSeries){
            this.dataSet.addSeries(line);
        }
        
        
        
        
        this.chart = ChartFactory.createXYLineChart(
                name,
                "Length",
                "Coverage",
                this.dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        this.chart.setBackgroundPaint(Color.white);
        
        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        for(int i = 0; i< this.dotSeries.size(); i++){
            renderer.setSeriesLinesVisible(i, false);
        }
        
        for(int i =this.dotSeries.size(); i< this.lineSeries.size()+this.dotSeries.size(); i++){
            renderer.setSeriesShapesVisible(i, false);
        }
        
        renderer.setBaseToolTipGenerator(new ItemGenerator());
//        renderer.setSeriesToolTipGenerator(0, new ItemGenerator());
        
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
 
        plot.setDomainGridlinePaint(Color.MAGENTA);
        plot.setRangeGridlinePaint(Color.MAGENTA);

        
//        XYDotRenderer dotRenderer = new XYDotRenderer(); 
//        dotRenderer.setDotWidth(3); 
//        dotRenderer.setDotHeight(3); 
//        dotRenderer.setSeriesPaint(0, Color.blue);
//        this.chart.getXYPlot().setRenderer(dotRenderer); 

        boolean add = (this.chartPanel == null);
        this.chartPanel = new ChartPanel(chart);
        
        
        
        
        
       
        if(add){
            this.content.add(this.chartPanel, BorderLayout.CENTER);
            this.scroll.setViewportView(this.chartPanel);
        }
      
        this.setBackground(Color.green);
        this.chartPanel.setBackground(Color.MAGENTA);
        this.updateUI();
    }
    
    private  class ItemGenerator implements XYToolTipGenerator {
  
        public ItemGenerator(){
            
        }
        
        @Override
        public String generateToolTip(XYDataset dataset, int series, int category) {
           String coorX = null;   
           String coorY = null;   

           //Number value = dataset.getValue(series, category);
           Number value = (int)dataset.getXValue(series, category);
           Number value1 = (int)dataset.getYValue(series, category);
           String name = dataset.getSeriesKey(series).toString();

           if (value != null && value1!=null) {          
              coorX  = value.toString();       
              coorY = value1.toString();      
           }
           return name+", Length: "+coorX +", Coverage:"+coorY;
        }

    }
    
}

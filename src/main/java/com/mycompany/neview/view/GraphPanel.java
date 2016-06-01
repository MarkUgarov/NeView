/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.view;

import com.mycompany.neview.model.elements.Coordinates;
import com.mycompany.neview.model.elements.Dot;
import com.mycompany.neview.model.elements.DotBag;
import com.mycompany.neview.model.elements.Median;
import com.mycompany.neview.view.datarepresentation.CoordinateSeries;
import com.mycompany.neview.view.datarepresentation.CoordinateSeriesCollection;
import com.mycompany.neview.view.datarepresentation.ExpAxis;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.AttributedString;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.XYToolTipGenerator;

 import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;


/**
 *
 * @author mugarov
 */
public class GraphPanel extends JPanel {
    
    private final JScrollPane scroll;
    private final JPanel content;
    private final ArrayList<CoordinateSeries> dotSeries;
    private final ArrayList<CoordinateSeries> lineSeries;
    private final ArrayList<Coordinates> coordinates;
    private CoordinateSeriesCollection seriesCollection;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private final ColorGenerator colorGen;
    
    public static final String XAxis = "Length";
    public static final String YAxis = "Reads";
    
    
    public GraphPanel(){
        super(new BorderLayout());
        super.setBackground(Color.blue);
       
        this.colorGen = new ColorGenerator();
        
        this.content = new JPanel(new BorderLayout());
        this.scroll = new JScrollPane(this.content);
        
        this.add(this.scroll, BorderLayout.CENTER);
        this.dotSeries = new ArrayList<>();
        this.lineSeries = new ArrayList<>();
        this.coordinates = new ArrayList<>();
    }
    
    public void setValues(ArrayList<DotBag> dotBags, ArrayList<Median> lines, String name){
        
        CoordinateSeries ser;
        for(DotBag db:dotBags){
            
            ser = new CoordinateSeries(db.getName());
            for(Dot d:db){

                ser.add(d.getCoordinates(), d.getName(), d.getScaffoldName());
//                System.out.println("New dot:"+d.getLogX()+", "+d.getLogY());
            }

            this.dotSeries.add(ser);
        }
        
        
        for(Median med:lines){
            ser = new CoordinateSeries(med.getName());

            ser.add(med.getStart(), med.getName(), null);
            ser.add(med.getEnd(), med.getName(), null);
//            System.out.println("New line from " + med.getStart().getX()+","+ med.getStart().getY()+" to "+med.getEnd().getX()+","+ med.getEnd().getY());
            this.lineSeries.add(ser);
        }
        
        this.seriesCollection = new CoordinateSeriesCollection();
        
        
        for(CoordinateSeries dotSeries:this.dotSeries){
            this.seriesCollection.addSeries(dotSeries);
        }
        for(CoordinateSeries line:this.lineSeries){
            this.seriesCollection.addSeries(line);
        }
        

        this.chart = ChartFactory.createXYLineChart(
                name,
                GraphPanel.XAxis,
                GraphPanel.YAxis,
                this.seriesCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        this.chart.setBackgroundPaint(Color.white);
        
        XYPlot plot = chart.getXYPlot();
        
       
        
        ExpAxis x1Axis = new ExpAxis(GraphPanel.XAxis);
        ExpAxis y1Axis = new ExpAxis(GraphPanel.YAxis);
        
        NumberAxis x2Axis  = new NumberAxis("Proportion to max of "+GraphPanel.XAxis);
        NumberAxis y2Axis = new NumberAxis("Proportion to max of "+GraphPanel.YAxis);
                
        plot.setDomainAxis(0, x1Axis);
        plot.setDomainAxis(1, x2Axis);
        plot.setRangeAxis(0, y1Axis);
        plot.setRangeAxis(1, y2Axis);
//        plot.setDomainAxis(1, yAxis);

        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        for(int i = 0; i< this.dotSeries.size(); i++){
            renderer.setSeriesLinesVisible(i, false);
            renderer.setSeriesPaint(i, this.colorGen.get(i));
        }
        
        for(int i =this.dotSeries.size(); i< this.lineSeries.size()+this.dotSeries.size(); i++){
            renderer.setSeriesShapesVisible(i, false);
//             renderer.setSeriesPaint(i, Color.black);
            renderer.setSeriesPaint(i, this.colorGen.get(i-this.dotSeries.size()));
        }
        
        renderer.setBaseToolTipGenerator(new ItemGenerator());
        
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
 
        plot.setDomainGridlinePaint(Color.MAGENTA);
        plot.setRangeGridlinePaint(Color.MAGENTA);

        

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
           String name = ((CoordinateSeries)seriesCollection.getSeries(series)).getName(category);
           int v1 = (int)((CoordinateSeries)seriesCollection.getSeries(series)).getXValue(category);
           int v2 = (int)((CoordinateSeries)seriesCollection.getSeries(series)).getYValue(category);
           String scaffold = ((CoordinateSeries)seriesCollection.getSeries(series)).getScaffold(category);
                   
           return "\t"+name+": "+GraphPanel.XAxis+": "+v1 +", "+GraphPanel.YAxis+":"+v2+"  \t "+scaffold;
        }

    }
    
 
    
}

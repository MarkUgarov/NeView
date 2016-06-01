/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model;

import com.mycompany.neview.model.elements.DotBag;
import com.mycompany.neview.model.elements.Median;
import com.mycompany.neview.view.ListPanel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author mugarov
 */
public class FileOutParser {
    
    private File file;
    private boolean writeMedians;
    private boolean valuesInLog;
    private ArrayList<String> medianData;
    private ArrayList<String[]> dotBagData;
    
    public FileOutParser(){
        
    }
    
    public void write(File exportFile){
        this.file = exportFile;
        if(this.dotBagData == null){
            return;
        }
        StringBuilder content = new StringBuilder();
        StringBuilder bagContent;
        for(int i = 0; i<this.dotBagData.size() && (this.medianData == null || i<this.medianData.size()); i++){
            bagContent = new StringBuilder();
            if(this.writeMedians && this.medianData != null){
                bagContent.append(this.medianData.get(i));
                for(String line:this.dotBagData.get(i)){
                    bagContent.append("\n");
                    bagContent.append("\t");
                    bagContent.append(line);
                }
                bagContent.append("\n");
                
            }
            else{
                for(int j = 0; j<this.dotBagData.get(i).length; j++){
                    bagContent.append(this.dotBagData.get(i)[j]);
                    bagContent.append("\n");
                }
            }
            content.append(bagContent);
        }
        
        while (this.file.exists()){
            this.file = new File(this.file.getParent(), this.file.getName()+"_avoidOverwrite");
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.file));
        } catch (IOException ex) {
            Logger.getLogger(FileOutParser.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        try {
            writer.write(content.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOutParser.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        
    }
    
    
    public void setData(ArrayList<Median> medians, ArrayList<DotBag> bags, boolean writeMedians){
        this.dotBagData = new ArrayList<>();
        this.writeMedians = writeMedians;
        if(medians !=null){
            this.medianData = new ArrayList<>();
            for(Median median: medians){
                this.medianData.add(this.getMedianToString(median));
            }
        }
        else{
            this.medianData = null;
            this.writeMedians = false;
        }
        for(DotBag bag: bags){
            this.dotBagData.add(this.getDotBagString(bag));
        }
    }
    
    public void setData(TableModel model){
        this.medianData = null;
        this.writeMedians = false;
        this.dotBagData = new ArrayList<>();
        String[] entries = new String[model.getRowCount()];
        String name;
        String length;
        String reads;
        String scaffold;
        String median;
        for(int i =0 ; i<model.getRowCount(); i++){
            name = (String) model.getValueAt(i, ListPanel.POSITION_NAME);
            length = (String) model.getValueAt(i, ListPanel.POSITION_LENGTH);
            reads = (String) model.getValueAt(i,ListPanel.POSITION_READS);
            scaffold = (String) model.getValueAt(i, ListPanel.POSITION_SCAFFOLD);
            median = (String) model.getValueAt(i, ListPanel.POSITION_MEDIAN);
            entries[i]=this.getDotToStrong(name, length, reads, scaffold, median);
        }
        this.dotBagData.add(entries);
    }
    
    
    
    public void setWriteDataInLog(boolean asLog){
        this.valuesInLog = asLog;
    }
    
    private String[] getDotBagString(DotBag bag){
        String[] ret = new String[bag.size()];
        String curr;
        for(int i=0; i< bag.size(); i++){
            if(this.valuesInLog){
                curr= this.getDotToString(bag.get(i).getName(), bag.get(i).getLogX(), bag.get(i).getLogY(), bag.get(i).getScaffoldName(), bag.get(i).getMedian());
            }
            else{
                curr= this.getDotToString(bag.get(i).getName(), bag.get(i).getX(), bag.get(i).getY(), bag.get(i).getScaffoldName(), bag.get(i).getMedian());
            }
            ret[i] = curr;
        }
        return ret;
       
    }
    
    private String getDotToStrong(String name, String length, String coverage, String scaffold, String median){
        return name+": "+length+", "+ coverage+"\t"+scaffold+"\t"+median;
    }
    
    private String getDotToString(String name, double length, double coverage, String scaffold, String median){
        return this.getDotToStrong(name,""+(int)length, ""+(int)coverage, scaffold, median);
    }
    
    private String getMedianToString(Median median){
        if(this.valuesInLog){
            return median.getName()+": Associated Length "+median.getFacorisedX()+", Reads "+median.getFactorisedY();
        }
        else{
            return median.getName()+": Associated Length "+median.getExponentialFactorisedX()+", Reads "+median.getExponentialFactorisedY();
        }

    }
    
    
    
    
}

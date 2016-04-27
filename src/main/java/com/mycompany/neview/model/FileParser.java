/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.model;

import com.mycompany.neview.model.elements.Dot;
import com.mycompany.neview.model.elements.DotBag;
import com.mycompany.neview.model.elements.Median;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class FileParser {
    
    private File file;
    private ArrayList<String> uncutContent;
    private ArrayList<String> cutContent;
    private final String linePrefix = ">";
    private final ArrayList<Dot> baglessDots;
    private final ArrayList<DotBag> dotBags;
    private int maxMeridianLines = 5;
    private String name;
    
    
    private final ArrayList<Median> lines;
    private double coverageSum;
    private double lengthSum;
    private double maxCoverage;
    private double maxLength;
    
    
    public FileParser(String path){
        this();
        this.addDataFromFile(new File(path));
        this.create();
    }
    
    public FileParser(String[] nameAndPaths){
        this();
        if(nameAndPaths.length>1){
            this.name = nameAndPaths[0];
            for(String path:Arrays.copyOfRange(nameAndPaths, 1, nameAndPaths.length)){
                this.addDataFromFile(new File(path));
            }
        }
        else{
            for(String path:nameAndPaths){
                this.addDataFromFile(new File(path));
            }
        }
        this.create();
    }
    
    public FileParser(File[] files){
        this();
        for(File file:files){
            this.addDataFromFile(file);
        }
        this.create();
    }
    
    
    private void create(){
        this.createDots();
        this.createLines();
        this.createDotBags();
    }
    
    public FileParser(){
        this.baglessDots = new ArrayList<>();
        this.dotBags = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.coverageSum = 0;
        this.lengthSum = 0;
        this.maxCoverage = 0;
        this.maxLength = 0;
        this.cutContent = new ArrayList<>();
    }
    
    private void addDataFromFile(File file){
        if(this.name == null){
            this.name = file.getName();
        }
        else if(this.file == null){
            this.name = this.name +": "+file.getName();
        }
        else{
            this.name = this.name +"; "+file.getName();
        }
        this.file = file;
        this.read();
        this.cut();
    }
    
    
    public ArrayList<DotBag> getDots(){
        return this.dotBags;
    }
    
    public ArrayList<Median> getLines(){
        return this.lines;
    }
    
    /**
     * Reads the file if it exists.
     */
    private void read(){
        if(!this.file.exists()){
            System.err.println("This file does not exist:"+file.toPath());
        }
        try {
            this.uncutContent = new ArrayList<>(Files.readAllLines(this.file.toPath(), Charset.defaultCharset()));
        } catch (IOException ex) {
            Logger.getLogger(FileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cuts out every line not starting with the linePrefix; overleaps the next
     * line if the current line starts with linePrefix
     */
    private void cut(){
        String str;
        int i=0;
        while(i<this.uncutContent.size()){
            str = this.uncutContent.get(i).trim();
            if(str.startsWith(this.linePrefix)){
//                System.out.println("Add read line "+str);
                this.cutContent.add(str.substring(1));
                
                i = i+2;
            }
            else{
                i++;
            }
        }
    }
    
    private void createDots(){
        String[] arr;
        double length;
        double coverage;
        for(String str:this.cutContent){
            arr = str.split("\\s+");
            if(arr.length<2){
               System.err.println("Could not split content"+str);
            }
            else{
                length = this.getValue(arr[1]);
                coverage = this.getValue(arr[2]);
                this.baglessDots.add(new Dot(length, coverage, arr[0]));
                this.coverageSum += coverage;
                this.lengthSum += length;
                if(this.maxCoverage<coverage){
                    this.maxCoverage = coverage;
                }
                if(this.maxLength < length){
                    this.maxLength = length;
                }
            }
        }
    }
    
    private void createDotBags(){
        for(Median line:this.lines){
            this.dotBags.add(new DotBag(line.getName()+"Data"));
        }
        this.dotBags.add(new DotBag("Above"));
        int medianIndex;
        boolean found;
        for(Dot currentDot:this.baglessDots){
           found = false;
           for(medianIndex = 0; medianIndex<this.lines.size()&&!found; medianIndex++){
               if(this.lines.get(medianIndex).isUnderLine(currentDot)){
                   this.dotBags.get(medianIndex).add(currentDot);
                   found = true;
               }
           }
           if(!found){
               this.dotBags.get(this.lines.size()).add(currentDot);
           }
        }
        System.out.println("Finished creating bags");
    }
    
    private void createLines(){
        double average = this.coverageSum / this.baglessDots.size();
        Median line;
        int factor = 1;
        boolean end = false;
        int meridianLines =0;
        while(!end && meridianLines<this.maxMeridianLines){
            line = new Median(average, this.maxCoverage, this.maxLength, factor+"x", factor);
            if(line.isOutOfBounds(this.maxLength, this.maxCoverage)){
                end = true;
            }
            else{
                this.lines.add(line);
                meridianLines++;
                factor = factor *2;
            }
        }
        
    }
    
    private double getValue(String arg){
        String[] arr = arg.split("=");
        double ret = -1;
        if(arr.length<2){
            System.err.println("Could not split value"+arg);
        }
        else{
            ret = Double.valueOf(arr[1]);

        }
        return ret;
    }
    
    public String getName(){
        return this.name;
    }
    
    
    

        
}

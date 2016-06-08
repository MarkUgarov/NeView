/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.model;

import com.mugarov.neview.model.elements.Dot;
import com.mugarov.neview.model.elements.DotBag;
import com.mugarov.neview.model.elements.Median;
import com.mugarov.neview.model.elements.Scaffold;
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
public class FileInParser {
    
    private File currentFile;
    private final ArrayList<String> uncutFastaContent;
    private final ArrayList<String> cutFastaContent;
    private final ArrayList<String> uncutScaffoldContent;
    private final ArrayList<Scaffold> scaffolds;
    private final String linePrefix = ">";
    private final ArrayList<Dot> baglessDots;
    private final ArrayList<Dot> bagedDots;
    private ArrayList<Dot> statisticRelevantDots;
    private final ArrayList<DotBag> dotBags;
    private int dotNumber;
    private int maxMeridianLines = 5;
    private String name;
    
    
    private final ArrayList<Median> lines;
    private double readsSum;
    private double lengthSum;
    private double maxLogReads;
    private double maxLogLength;
    
    private final double minRepresentativeLength = 10000;
    
    private final String[] fastaEndings = new String[]{"fna","fa", "fasta"};
    private final String[] scaffoldEndings = new String[]{"txt"};
    
    
    public FileInParser(String path){
        this();
        this.addDataFromFile(new File(path));
        this.create();
    }
    
    public FileInParser(String[] nameAndPaths){
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
    
    public FileInParser(File[] files){
        this();
        for(File file:files){
            this.addDataFromFile(file);
        }
        this.create();
    }
    
    
    private void create(){
        this.createScaffold();
        this.createDots();
        this.eliminateDwarfs();
        this.createLines();
        this.createDotBags();
    }
    
    public FileInParser(){
        this.baglessDots = new ArrayList<>();
        this.bagedDots = new ArrayList<>();
        this.statisticRelevantDots = new ArrayList<>();
        this.dotBags = new ArrayList<>();
        this.dotNumber = 0 ;
        this.lines = new ArrayList<>();
        this.readsSum = 0;
        this.lengthSum = 0;
        this.maxLogReads = 0;
        this.maxLogLength = 0;
        this.cutFastaContent = new ArrayList<>();
        this.uncutFastaContent = new ArrayList<>();
        this.uncutScaffoldContent = new ArrayList<>();
        this.scaffolds = new ArrayList<>();
    }
    
    private void addDataFromFile(File file){
        if(this.name == null){
            this.name = file.getName();
        }
        else if(this.currentFile == null){
            this.name = this.name +": "+file.getName();
        }
        else{
            this.name = this.name +"; "+file.getName();
        }
        this.currentFile = file;
        boolean isFasta = false;
        for(int i=0; i<this.fastaEndings.length && !isFasta; i++){
            if(this.currentFile.getName().endsWith(this.fastaEndings[i])){
                isFasta = true;
            }
        }
        if(isFasta){
            this.readFastaFile();
            this.cutFastaContent();
        }
        else{
            boolean isScaffold = false;
            for(int i=0; i<this.scaffoldEndings.length && !isScaffold; i++){
                if(this.currentFile.getName().endsWith(this.scaffoldEndings[i])){
                    isScaffold = true;
                }
            }
            if(isScaffold){
                this.readScaffoldFile();
               
            }
            
        }
        
    }
    
    
    public ArrayList<DotBag> getDotBags(){
        return this.dotBags;
    }
    
    public ArrayList<Median> getLines(){
        return this.lines;
    }
    
    /**
     * Reads the file if it exists.
     */
    private void readFastaFile(){
        if(!this.currentFile.exists()){
            System.err.println("This file does not exist:"+currentFile.toPath());
        }
        try {
            this.uncutFastaContent.addAll(new ArrayList<>(Files.readAllLines(this.currentFile.toPath(), Charset.defaultCharset())));
        } catch (IOException ex) {
            Logger.getLogger(FileInParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cuts out every line not starting with the linePrefix; overleaps the next
     * line if the current line starts with linePrefix
     */
    private void cutFastaContent(){
        String str;
        int i=0;
        while(i<this.uncutFastaContent.size()){
            str = this.uncutFastaContent.get(i).trim();
            if(str.startsWith(this.linePrefix)){
//                System.out.println("Add read line "+str);
                this.cutFastaContent.add(str.substring(1));
                i = i+2;
            }
            else{
                i++;
            }
        }
    }
    
    private void readScaffoldFile(){
        if(!this.currentFile.exists()){
            System.err.println("This file does not exist:"+currentFile.toPath());
        }
        try {
            this.uncutScaffoldContent.addAll(new ArrayList<>(Files.readAllLines(this.currentFile.toPath(), Charset.defaultCharset())));
        } catch (IOException ex) {
            Logger.getLogger(FileInParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createScaffold(){
        if(this.uncutScaffoldContent == null || this.uncutScaffoldContent.isEmpty()){
            return;
        }
        int contigNamePos = 5;
        int scaffoldNamePos = 0;
        String contigName;
        String scaffoldName;
        String[] line;
        boolean found;
        Scaffold currentScaff;
        for(int i = 0; i<this.uncutScaffoldContent.size(); i++){
            found = false;
            line = this.uncutScaffoldContent.get(i).split("\\s+");
            contigName = line[contigNamePos];
            scaffoldName = line[scaffoldNamePos];
            for(int j = this.scaffolds.size()-1; j>=0 && !found; j--){
                currentScaff = this.scaffolds.get(j);
                if(currentScaff.getName().equals(scaffoldName)){
                    currentScaff.add(contigName);
                    found = true;
                }
            }
            if(!found){
                this.scaffolds.add(new Scaffold(scaffoldName, contigName));
            }
        }
    }
    
    private void createDots(){
        String[] arr;
        double length;
        double coverage;
        double logCoverage;
        double logLength;
        Dot dot;
        for(String str:this.cutFastaContent){
            arr = str.split("\\s+");
            if(arr.length<2){
               System.err.println("Could not split content"+str);
            }
            else{
                length = this.getValue(arr[1]);
                coverage = this.getValue(arr[2]);
                dot = new Dot(length, coverage, arr[0]);
                this.dotNumber++;
                logLength = dot.getLogX();
                logCoverage = dot.getLogY();
                this.addToScaffoldAndBaglessDots(dot);
                this.readsSum += logCoverage;
                this.lengthSum += logLength;
                if(this.maxLogReads<logCoverage){
                    this.maxLogReads = logCoverage;
                }
                if(this.maxLogLength < logLength){
                    this.maxLogLength = logLength;
                }
            }
        }
    }
    
    private void addToScaffoldAndBaglessDots(Dot dot){
        if(this.scaffolds != null && !this.scaffolds.isEmpty()){
             boolean found = false;
            Scaffold currentScaffold;
            for(int i = 0; i<this.scaffolds.size() && !found; i++){
                currentScaffold = this.scaffolds.get(i);
                if(currentScaffold.containsName(dot.getName())){
                    currentScaffold.add(dot);
                    dot.setScaffold(currentScaffold.getName());
                    found = true;
                }
            }
//            if(!found){
//                System.out.println("Did not found scaffold for "+dot.getName());
//            }
        }
        this.baglessDots.add(dot);
    }
    
    private void eliminateDwarfs(){
        if(MathHelp.getExponential(this.maxLogLength)<this.minRepresentativeLength){
            System.out.println("Maximum size is < "+this.minRepresentativeLength+". Using all dots to create medians.");
            this.statisticRelevantDots = new ArrayList<Dot>(this.baglessDots);
            return;
        }
        else{
//            this.maxCoverage = 0;
            this.readsSum = 0;
            this.dotNumber = 0;
            this.lengthSum = 0;
            Dot d;
            int index = 0;
            while(index<this.baglessDots.size()){
                d = this.baglessDots.get(index);
                if(d.getX()<this.minRepresentativeLength){
//                    this.baglessDots.remove(index);
                }
                else{
//                    if(this.maxCoverage<d.getLogY()){
//                        this.maxCoverage = d.getLogY();
//                    }
                    this.lengthSum += d.getLogX();
                    this.readsSum += d.getLogY();
                    this.dotNumber++;
                    this.statisticRelevantDots.add(d);
                }
                index++;
            } 
        }
    }
    
    private void createDotBags(){
        DotBag above = null;
        for(Median line:this.lines){
            this.dotBags.add(new DotBag(line.getName()+"Data"));
        }
       
        int medianIndex;
        boolean found;
        for(Dot currentDot:this.baglessDots){
           found = false;
           for(medianIndex = 0; medianIndex<this.lines.size()&&!found; medianIndex++){
               if(this.lines.get(medianIndex).isUnderLine(currentDot)){
                   this.dotBags.get(medianIndex).add(currentDot);
                   currentDot.setMedian(this.dotBags.get(medianIndex).getName());;
                   found = true;
               }
           }
           if(!found){
               if(above == null){
                   above = new DotBag("Above");
                   this.dotBags.add(above);
               }
               above.add(currentDot);
               currentDot.setMedian(above.getName());
           }
           this.bagedDots.add(currentDot);
        }
//        System.out.println("Finished creating bags");
    }
    
    private void createLines(){
        double logAverageCoverage = (this.readsSum / this.dotNumber);
        double logAverageLength = (this.lengthSum / this.dotNumber);

        
        Median line;
        int factor = 1;
        boolean end = false;
        int meridianLines =0;
        while(!end && meridianLines<this.maxMeridianLines && this.dotNumber != 0){
            line = new Median(logAverageLength, logAverageCoverage, this.maxLogLength, this.maxLogReads, factor+"x", factor, this.getLinearRegressionFactor());
            if(line.isOutOfBounds(this.maxLogLength, this.maxLogReads)){
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
    
    private double getLinearRegressionFactor(){
        double n = this.dotNumber;
        double factorSumXiYi = 0;
        double squareSumXi = 0;
        double sumXi = this.lengthSum;
        double sumYi = this.readsSum;
                
        for(Dot d: this.statisticRelevantDots){
            factorSumXiYi += d.getLogX()*d.getLogY();
            squareSumXi += (d.getLogX()*d.getLogX());
        }
        
        return ((n *factorSumXiYi) - (sumXi*sumYi)) / ((n*squareSumXi)-(sumXi*sumXi));
        
        
        
    }
    
    

        
}

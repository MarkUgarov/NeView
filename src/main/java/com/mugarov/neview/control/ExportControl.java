/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.control;

import com.mugarov.neview.model.FileInParser;
import com.mugarov.neview.model.FileOutParser;
import com.mugarov.neview.model.elements.DotBag;
import com.mugarov.neview.view.ExportPanel;
import com.mugarov.neview.view.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

/**
 *
 * @author mugarov
 */
public class ExportControl implements ActionListener, ItemListener{
    
    public static final String LABEL_OPTIONS = "Export";
    
    public static final String SAVE_BUTTON_TEXT = "Save";
    public static final String SAVE_BUTTON_COMMAND = "saveDial";
    
    public static final String CHECKBOX_SHOW_MEDIAN_TEXT = "Group by medians";
    public static final String CHECKBOX_SHOW_MEDIAN_COMMAND = "writeMedians";
    
    public static final String RADIO_EXPORT_TABLE_TEXT = "Table";
    public static final String RADIO_EXPORT_TABLE_COMMAND = "expTable";
    
    public static final String RADIO_EXPORT_BAG_BY_LENGTH_TEXT = "Medians (sorted by length)";
    public static final String RADIO_EXPORT_BAG_BY_LENGTH_COMMAND = "expMedLength";
   
    public static final String RADIO_EXPORT_BAG_BY_COVERAGE_TEXT = "Medians (sorted by readscount)";
    public static final String RADIO_EXPORT_BAG_BY_COVERAGE_COMMAND = "expMedCover";
    
    
    private final MainFrame mainFrame;
    private final FileInParser data;
    private final JFileChooser chooser;
    private ExportPanel panel;
    
    private boolean writeTable;
    private boolean sortByCoverage;
    private boolean writeMedians;
    
    ExportControl(MainFrame frame, FileInParser data){
        this.mainFrame = frame;
        this.data = data;
        
        this.chooser = new JFileChooser(".");
        this.writeTable = false;
        this.writeMedians = false;
        this.sortByCoverage = false;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ExportControl.SAVE_BUTTON_COMMAND)){
            // write 
            if(this.chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                this.parseAndWrite(this.chooser.getSelectedFile());
            }
        }
        else if(e.getActionCommand().equals(ExportControl.RADIO_EXPORT_TABLE_COMMAND)){
            this.writeTable = true;
            this.mainFrame.enableMedianOption(false);
        }
        else if(e.getActionCommand().equals(ExportControl.RADIO_EXPORT_BAG_BY_COVERAGE_COMMAND)){
            this.writeTable = false;
            this.sortByCoverage = true;
            this.mainFrame.enableMedianOption(true);
        }
        else if(e.getActionCommand().equals(ExportControl.RADIO_EXPORT_BAG_BY_LENGTH_COMMAND)){
            this.writeTable = false;
            this.sortByCoverage = false;
            this.mainFrame.enableMedianOption(true);
        }
        else{
            System.out.println("UNKNOWN EVENT: " +e.getActionCommand());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox box = (JCheckBox) e.getSource();
        if(box.getActionCommand().equals(ExportControl.CHECKBOX_SHOW_MEDIAN_COMMAND)){
            if(e.getStateChange() == ItemEvent.SELECTED){
//                System.out.println("Selected "+box.getActionCommand());
                this.writeMedians = true;
            }
            else if(e.getStateChange() == ItemEvent.DESELECTED){
//                System.out.println("Unselected "+box.getActionCommand());
                this.writeMedians = false;
            }
        }
        
    }
    
    public void setExportPanel(ExportPanel export){
        this.panel = export;
    }
    
    private void parseAndWrite(File outputFile){
        FileOutParser parser = new FileOutParser();
        if(this.writeTable){
            parser.setData(this.mainFrame.getTableModel());
        }
        else{
            for(DotBag bag:this.data.getDotBags()){
                bag.sort(this.sortByCoverage);
            }   
            parser.setData(this.data.getLines(), this.data.getDotBags(), this.writeMedians);
        }
        parser.write(outputFile);
    }
}

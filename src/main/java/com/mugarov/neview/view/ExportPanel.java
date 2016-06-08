/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view;

import com.mugarov.neview.control.ExportControl;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author mugarov
 */
public class ExportPanel extends JPanel{
    
    private final JPanel selectionPanel;
    private final JPanel checkPanel;
    private final JCheckBox writeMedians;
    private final JPanel radioPanel;
    private final ButtonGroup group;
    private final JRadioButton exportTable;
    private final JRadioButton exportByLength;
    private final JRadioButton exportByCoverage;
    
    
    private final JPanel buttonPanel;
    private final JButton fileSaveButton;
    
    public ExportPanel(){
        this.setLayout(new BorderLayout());
        // north

        
        //center
        this.selectionPanel = new JPanel();
        this.selectionPanel.setLayout(new BorderLayout());
        
        this.radioPanel = new JPanel();
        this.group = new ButtonGroup();
        this.exportTable = new JRadioButton();
        this.exportByLength = new JRadioButton();
        this.exportByCoverage = new JRadioButton();
        this.selectionPanel.add(this.radioPanel, BorderLayout.NORTH);
        
        this.checkPanel = new JPanel();
        this.writeMedians = new JCheckBox();
        this.selectionPanel.add(this.checkPanel, BorderLayout.CENTER);
        
        this.add(this.selectionPanel, BorderLayout.CENTER);
        
        //south
        this.buttonPanel = new JPanel();
        this.fileSaveButton = new JButton();
        this.add(this.buttonPanel, BorderLayout.SOUTH);
    } 
    
    public void init(ExportControl listener){
        listener.setExportPanel(this);
        
        this.writeMedians.setText(ExportControl.CHECKBOX_SHOW_MEDIAN_TEXT);
        this.writeMedians.setActionCommand(ExportControl.CHECKBOX_SHOW_MEDIAN_COMMAND);
        this.writeMedians.addItemListener(listener);
        this.checkPanel.add(this.writeMedians);
        
        this.exportTable.setText(ExportControl.RADIO_EXPORT_TABLE_TEXT);
        this.exportTable.setActionCommand(ExportControl.RADIO_EXPORT_TABLE_COMMAND);
        this.exportTable.addActionListener(listener);
        this.group.add(this.exportTable);
        this.radioPanel.add(this.exportTable);
        
        this.exportByLength.setText(ExportControl.RADIO_EXPORT_BAG_BY_LENGTH_TEXT);
        this.exportByLength.setActionCommand(ExportControl.RADIO_EXPORT_BAG_BY_LENGTH_COMMAND);
        this.exportByLength.addActionListener(listener);
        this.group.add(this.exportByLength);
        this.radioPanel.add(this.exportByLength);
        
        this.exportByCoverage.setText(ExportControl.RADIO_EXPORT_BAG_BY_COVERAGE_TEXT);
        this.exportByCoverage.setActionCommand(ExportControl.RADIO_EXPORT_BAG_BY_COVERAGE_COMMAND);
        this.exportByCoverage.addActionListener(listener);
        this.group.add(this.exportByCoverage);
        this.radioPanel.add(this.exportByCoverage);
        
        this.fileSaveButton.setText(ExportControl.SAVE_BUTTON_TEXT);
        this.fileSaveButton.setActionCommand(ExportControl.SAVE_BUTTON_COMMAND);
        this.fileSaveButton.addActionListener(listener);
        this.buttonPanel.add(this.fileSaveButton);
        
        this.group.setSelected(this.exportByLength.getModel(), true);
    }
    
    public void enableCheckbox(boolean enable){
        if(!enable){
            this.writeMedians.setSelected(false);
        }
        this.writeMedians.setEnabled(enable);
        
    }
    
    
}

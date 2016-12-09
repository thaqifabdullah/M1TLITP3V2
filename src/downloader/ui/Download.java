/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader.ui;

import downloader.fc.ThreadDownloader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author abdullam
 */
public class Download extends JPanel{
    JProgressBar bar;
    JLabel label;
    JButton cancel, pause;
    Main main;
    ThreadDownloader thread;
    String url;
    JPanel left, right;
    
    public Download(Main main){
        this.setLayout(new GridLayout(1,2));
        this.main = main;
        this.left = new JPanel();
        this.left.setLayout(new GridLayout(2,1,5,5));
        this.right = new JPanel();
        this.right.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.bar = new JProgressBar(SwingConstants.HORIZONTAL,0, 100);
        this.bar.setValue(0);
        this.bar.setStringPainted(true);
        this.initButton();
        this.url = this.main.getUrl();
        this.label = new JLabel(this.url);
        this.thread = new ThreadDownloader(this);
        this.left.add(this.label);
        this.left.add(this.bar);
        this.right.add(this.pause);
        this.right.add(this.cancel);
        this.add(this.left);
        this.add(this.right);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.white);
        this.main.panelDownload.add(this);
        this.thread.start();
    }
    
    private void initButton(){
        this.cancel = new JButton(" X ");
        this.cancel.setActionCommand("Delete");
        this.cancel.addActionListener(new EcouteurDeBouton(this.main, this));
        
        this.pause = new JButton(" II ");
        this.pause.setActionCommand("Pause");
        this.pause.addActionListener(new EcouteurDeBouton(this.main, this));
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public JProgressBar getProgressBar(){
        return this.bar;
    }
    
    public ThreadDownloader getThread(){
        return this.thread;
    }
    
    
}

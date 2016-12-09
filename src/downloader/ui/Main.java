/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader.ui;

import downloader.fc.Downloader;
import downloader.fc.ThreadDownloader;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.*;

/**
 *
 * @author thaqif
 */
public class Main extends JFrame{
    ArrayList<Download> listDownload;
    ListIterator<Download> iter;
    JTextField textUrl;
    StackLayout layout;
    JPanel panelDownload, panelUrl;
    JButton buttonAdd;
    static final int width = 500, height = 300;
    
    public Main(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.layout = new StackLayout();
        this.panelDownload = new JPanel();
        this.panelDownload.setLayout(this.layout);
        this.listDownload = new ArrayList<Download>();
        this.iter = this.listDownload.listIterator();
        this.textUrl = new JTextField(30);
        this.panelUrl = new JPanel();
        this.panelUrl.add(this.textUrl);
        this.initButton();
        this.panelUrl.add(this.buttonAdd);
        this.add(this.panelDownload, BorderLayout.CENTER);
        this.add(this.panelUrl, BorderLayout.SOUTH);
        this.setSize(width, height);
    }
    
    public String getUrl(){
        return this.textUrl.getText();
    }
    
    public void addThread(){
        Download d = new Download(this);
        this.iter.add(d);
        this.revalidate();
    }
    
    public void removeDownload(Download d){
        if(this.listDownload.contains(d)){
            this.panelDownload.remove(d);
            this.panelDownload.repaint();
            this.listDownload.remove(this.listDownload.indexOf(d));
            this.iter = this.listDownload.listIterator();
            this.validate();
        }
    }
    
    
    private JProgressBar initJpb() {
        JProgressBar jpb = new JProgressBar(SwingConstants.HORIZONTAL,0, 100);
        jpb.setValue(0);
        jpb.setStringPainted(true);
        return jpb;
    }
    
    private void initButton(){
        this.buttonAdd = new JButton("Add");
        this.buttonAdd.setActionCommand("Ajouter");
        this.buttonAdd.addActionListener(new EcouteurDeBouton(this));
    }
    
    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
                new Main("downloader").setVisible(true); 
            }
        });
    }
}

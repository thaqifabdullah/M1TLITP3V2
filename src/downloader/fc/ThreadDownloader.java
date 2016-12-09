/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader.fc;

import downloader.ui.Download;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author thaqif
 */
public class ThreadDownloader extends Thread{
    Download download;
    Downloader downloader;
    public ThreadDownloader(Download d){
        this.download = d;
        this.downloader = null;
    }
    
    class ProgressBarListener implements PropertyChangeListener{
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            System.out.print(".");
            System.out.flush();
            if ("progress".equals(evt.getPropertyName())) {
                download.getProgressBar().setValue((Integer)evt.getNewValue());
            }  
        } 
    }
    
    public void run(){
        try {
                downloader = new Downloader(this.download.getUrl());
        }
        catch(RuntimeException e) {
                System.err.format("skipping %s %s\n", this.download.getUrl(), e);
        }
        System.out.format("Downloading %s:", downloader);

        downloader.addPropertyChangeListener(new ProgressBarListener());

        downloader.execute();
        String filename = null;
        try {
            filename = downloader.get();
        }
        catch(Exception e) {
                System.err.println("failed!");
        }
        System.out.format("into %s\n", filename);
    }
    
    public void deleteDownloader(){
        boolean cancel = this.downloader.cancel(true);
    }
}

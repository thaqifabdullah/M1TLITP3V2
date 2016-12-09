/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EcouteurDeBouton implements ActionListener{
	Main main;
        Download download;
        String url;
	public EcouteurDeBouton(Main fenetre, Download download) {
		this.main = fenetre;
                this.download = download;
                this.url = null;
	}
        
        public EcouteurDeBouton(Main fenetre) {
		this.main = fenetre;
                this.download = null;
                this.url = null;
	}
        
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().compareTo("Ajouter") == 0){
                    this.main.addThread();
		}
		
		if(e.getActionCommand().compareTo("Delete") == 0){
                    this.download.getThread().deleteDownloader();
                    this.main.removeDownload(this.download);
		}
		
		if(url == "pause"){
			
		}
	}

}


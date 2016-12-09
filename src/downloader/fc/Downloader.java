package downloader.fc;

import java.util.List;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.SwingWorker;


public class Downloader extends SwingWorker<String,String>{
    public static final int CHUNK_SIZE = 1024;

    URL url;
    int content_length;
    BufferedInputStream in;

    String filename;
    File temp;
    FileOutputStream out;

    private int _progress;
    //private PropertyChangeSupport pcs;

    public Downloader(String uri) {
        //this.pcs = this.getPropertyChangeSupport();
        try {
                url = new URL(uri);

                URLConnection connection = url.openConnection();
                content_length = connection.getContentLength();

                in = new BufferedInputStream(connection.getInputStream());

                String[] path = url.getFile().split("/");
                filename = path[path.length-1];
                temp = File.createTempFile(filename, ".part");
                out = new FileOutputStream(temp);
        }
        catch(MalformedURLException e) { throw new RuntimeException(e); }
        catch(IOException e) { throw new RuntimeException(e); }
    }

    public String toString() {
            return url.toString();
    }

    @Override
    protected String doInBackground() throws Exception {
        byte buffer[] = new byte[CHUNK_SIZE];
        int size = 0;
        int count = 0;

        while(count >= 0) {
                try{
                        out.write(buffer, 0, count);
                }
                catch(IOException e) { continue; }

                size += count;
                this.publish("old value");
                setProgress(100*size/content_length);
                this.publish("new value");
                Thread.sleep(1000);

                try{
                        count = in.read(buffer, 0, CHUNK_SIZE);
                }
                catch(IOException e) { continue; }
        }

        if(size < content_length) {
                temp.delete();
                throw new InterruptedException();
        }

        temp.renameTo(new File(filename));
        return filename;
    }
    
    @Override
    protected void process(List<String> chunks){
        int old_progress = 0;
        for(String s:chunks){
            if(s.compareTo("old value") == 0){
                 old_progress = _progress;
            }
            if(s.compareTo("new value") == 0){
                this.getPropertyChangeSupport().firePropertyChange("progress", old_progress, _progress);
            }
        }
    }
}

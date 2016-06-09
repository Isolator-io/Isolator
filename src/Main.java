import Iso14496.Iso;
import Iso14496.IsoFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mac
 */
public class Main {
    
    
    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        File videoFile = new File("sample.mp4");
        
        if (!videoFile.exists()) {
            try {
                throw new FileNotFoundException("File not found");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!videoFile.canRead()) {
            throw new IllegalStateException("No read permissions to file not found");
        }
        
        
        //Iso iso = new Iso(videoFile);//Iso(new FileInputStream(videoFilePath).getChannel());
        
        IsoFile isoFile = new IsoFile(videoFile);
        //IsoFile rippedAudio = IsoFile.ripAudio(isoFile);
    }
}

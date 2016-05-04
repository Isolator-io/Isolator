/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;
import Iso14496.IsoFile;
import Iso14496.IsoReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class TKHD extends FullBox{
    int creation_time = 0; //0x7C25B080;
    int modification_time = 0;//0x7C25B080;
    int track_ID = 1;

    int duration = 0x000034C0;

    int layer = 0;
    short alternate_group = 0;
    short volume = 0x0100;

   int[]  matrix = { 0x00010000,0,0,0,0x00010000,0,0,0,0x40000000 };
      // unity matrix
   int width;
   int height;
   
   
    public TKHD() {
        super(Box.TKHD);
    }

    @Override
    public byte[] toBinary() {
        byteStream.reset();
        
        try {
            byteStream.write(intToByteArray(92));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0)); //Full Box Headers
            
            byteStream.write(intToByteArray(creation_time));
            byteStream.write(intToByteArray(modification_time));
            byteStream.write(intToByteArray(track_ID));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(duration));
            
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(0));
            
            byteStream.write(shortToByteArray((short)0)); //layer
            byteStream.write(shortToByteArray((short)1)); //alternative group
            byteStream.write(shortToByteArray((short)0x0100));
            byteStream.write(shortToByteArray((short)0)); //reserved
            
            
            for(int n = 0 ; n < 9; n++){
                byteStream.write(intToByteArray(matrix[n])); //matrix
            }
            
            byteStream.write(intToByteArray(0)); //length
            byteStream.write(intToByteArray(0)); //width
            
        } catch (IOException ex) {
            Logger.getLogger(TKHD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return byteStream.toByteArray();
    }

    @Override
    public void loadData() {
        
        int boxType;
        int offset = 0;
        int boxSize;
        Box box = null;
        Class boxClass = null;

        internalSize = IsoReader.readIntAt(fileData, internalOffset + offset); //get box size
        
        creation_time = IsoReader.readIntAt(fileData, internalOffset +12); //get box size
        modification_time = IsoReader.readIntAt(fileData, internalOffset +16); //get box size
        track_ID = IsoReader.readIntAt(fileData, internalOffset +20);

        duration = IsoReader.readIntAt(fileData, internalOffset +28);

    }
    
    public String toString() {

        return IsoFile.toASCII(type) + " track ID : " + track_ID;

    }
    
    public int getTrackID(){
        return track_ID;
    }
    
    public int getChunkCount(){
        
        for (Box box : children) {

            if (box.getBoxType() == Box.MDIA) {

                return ((MDIA) box).getChunkCount();

            }

        }
    
        return 0;
    }
    
}

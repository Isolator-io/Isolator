/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import Iso14496.IsoFile;
import Iso14496.IsoReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class MDAT extends Box{
    byte[] data;
    int dataLength;
    int dataOffset;
    
    public MDAT() {
        super(Box.MDAT);
    }
    
    public void setData(byte[] data){
        this.data = data;
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(8 + data.length)); //type 
            byteStream.write(intToByteArray(type)); //type 
            byteStream.write(data); //type 
        } catch (IOException ex) {
            Logger.getLogger(MDAT.class.getName()).log(Level.SEVERE, null, ex);
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
        dataOffset = internalOffset + headerOffset;
        dataLength = internalSize - headerOffset;
        
        
    }
    
    
    public String toString(){
        
        return IsoFile.toASCII(type) + " data length : " + dataLength;
  
    }
    
}

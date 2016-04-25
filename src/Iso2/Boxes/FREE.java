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
public class FREE extends Box{
    private byte[] data;
    int dataOffset;
    int dataLength;
    
    public FREE() {
        super(Box.FREE);

    }
    
    public void setData(byte[] data){
        this.data = data; 
    }

    @Override
    public byte[] toBinary() {
        byteStream.reset();
        byte[] binaryData = null;
        try {
            
            if(data != null){
                byteStream.write(intToByteArray(8 + data.length)); //size
                byteStream.write(intToByteArray(type)); //type 
                byteStream.write(data); //type 
            }else{
               byteStream.write(intToByteArray(8)); //size
               byteStream.write(intToByteArray(type)); //type 
            }
            
            //byteStream.write(intToByteArray(8)); //size
            //byteStream.write(intToByteArray(type)); //type
            byteStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(FREE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        binaryData = byteStream.toByteArray();
        
        return binaryData;
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

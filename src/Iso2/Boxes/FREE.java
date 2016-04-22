/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class FREE extends Box{
    private byte[] data;
    
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
    
}

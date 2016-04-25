/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;
import Iso14496.IsoReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class MDHD extends FullBox{
    int creation_time = 0;// 0x7C25B080;
    int modification_time = 0;// 0x7C25B080;
    int timescale = 0x0000BB80;
    int duration = 0x0009E400;
    short language = 0x55C4;
      

    public MDHD() {
        super(Box.MDHD);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(32));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0)); //Full Box Headers
            
            byteStream.write(intToByteArray(creation_time));
            byteStream.write(intToByteArray(modification_time));
            byteStream.write(intToByteArray(timescale));
            byteStream.write(intToByteArray(duration));
            
            byteStream.write(shortToByteArray(language));
            byteStream.write(shortToByteArray((short) 0));
            
            
        } catch (IOException ex) {
            Logger.getLogger(MDHD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("total mdhd header size :" + byteStream.size());
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
    }
    
}

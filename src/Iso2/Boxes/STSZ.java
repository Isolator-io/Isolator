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
public class STSZ extends FullBox{
    int sample_size = 0;
    int sample_count = 633;
    int[] sample_table;
   
    public STSZ(int[] sample_table) {
        super(Box.STSZ);
        this.sample_table = sample_table;
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12 + 4 * sample_count + 8 ) );
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(sample_size));
            byteStream.write(intToByteArray(sample_count));
            for(int n = 0 ; n < sample_count ; n++){
                byteStream.write(intToByteArray(sample_table[n]));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(STSZ.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
}

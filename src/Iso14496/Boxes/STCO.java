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
public class STCO extends FullBox{
    int entry_count = 1;
    int[] chunk_offset;
    
    public STCO() {
        super(Box.STCO);
    }
    

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12 + 4 * entry_count + 4 ) );
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(entry_count));
            
            byteStream.write(intToByteArray(40)); // first offset
            
        } catch (IOException ex) {
            Logger.getLogger(STSZ.class.getName()).log(Level.SEVERE, null, ex);
        }
    return byteStream.toByteArray();
    }

    @Override
    public void loadData() {

        internalSize = IsoReader.readIntAt(fileData, internalOffset); //get box size
        entry_count = IsoReader.readIntAt(fileData, internalOffset + 12);
        
        chunk_offset= new int[entry_count];
        
        for(int n = 0; n < entry_count; n++){
            chunk_offset[n] = IsoReader.readIntAt(fileData, internalOffset + 12 + (n*4));
        }
    }
    
    
    public String toString() {

        return IsoFile.toASCII(type) + " chunk count : " + entry_count;

    }
    
    public int getChunkCount(){
        return entry_count;
    }
    
    public int[] getChunkOffsets(){
        return chunk_offset;
    }
    
}

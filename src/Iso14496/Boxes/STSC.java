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
public class STSC extends FullBox{
    int entry_count = 1;
    int[][] sample_chunk_table;
    int[] chunkRuns;
    
    
    public STSC() {
        super(Box.STSC);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12+ 4 + (3 * entry_count * 4)));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(entry_count));
            
            byteStream.write(intToByteArray(1)); //first chunk
            byteStream.write(intToByteArray(633)); //samples per chunk
            byteStream.write(intToByteArray(1)); //first chunk index
            
            /*
            for(){
                
            }*/
        } catch (IOException ex) {
            Logger.getLogger(STSC.class.getName()).log(Level.SEVERE, null, ex);
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
        
        entry_count = IsoReader.readIntAt(fileData, internalOffset + 12); 
        sample_chunk_table = new int[entry_count][3];
        int[] chunkRuns = new int[entry_count];
        
        for(int n = 0; n < entry_count; n++){
            sample_chunk_table[n][0] = IsoReader.readIntAt(fileData, internalOffset + 12 + (n * 12));
            sample_chunk_table[n][1] = IsoReader.readIntAt(fileData, internalOffset + 12 + (n * 12) + 4);
            sample_chunk_table[n][2] = IsoReader.readIntAt(fileData, internalOffset + 12 + (n * 12) + 8);
            
            if(n >0){
                chunkRuns[n-1] = sample_chunk_table[n][0] - sample_chunk_table[n-1][0];
            }
                        
        }
        
        chunkRuns[entry_count-1] = getChunkCount() - sample_chunk_table[entry_count-1][0];
        //Runs calculated
                
    }
    
    
    public String toString() {

        return IsoFile.toASCII(type) + " entry count : " + entry_count;

    }
    
    private int getChunkCount(){
        
        
        return ((STBL)container).getChunkCount();
    }
    
    public int[] getChunkRuns(){
        return chunkRuns;
    }
    
}

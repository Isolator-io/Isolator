/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class Iso extends Box {
    
    public static final int FTYP = 0x66747970;
    public static final int FREE = 0x66726565;
    public static final int MDAT = 0x6D646174;
    public static final int MOOV = 0x6D6F6F76;
    
    
    byte[] fullData;

    public Iso(File videoFile) throws FileNotFoundException, IOException {

         // Testing only! 
        
        fullData = Files.readAllBytes(videoFile.toPath());
        size = IsoReader.readIntAt(fullData , 0);
        
        /*
        int box1size = IsoReader.readIntAt(fullData , size);
        int nextCode = IsoReader.readIntAt(fullData , box1size + size -4);
        
        
        int box2size = IsoReader.readIntAt(fullData , size + box1size);
        System.out.println(box2size);
        
        int box3size = IsoReader.readIntAt(fullData , size + box1size + box2size);
        System.out.println(size + box1size + box2size);
        System.out.println(box3size);
        */
        int fullBoxHeaderSize = 8;
        int stszLocation = 2103830 + 9 + 4;
        //int stszSampleCount = 2103830 + 9 + 4;
        
        

        
        ///CHUNK COUNT
        int stco = 2106382;
        int stcoChunkCountLoc = stco + 8;
        int stcoChunkCount = IsoReader.readIntAt(fullData , stcoChunkCountLoc) ;
        int stcoOffsetsLoc = stcoChunkCountLoc + 4;
        int[] offsets = new int[stcoChunkCount];
        for(int n = 0 ; n < stcoChunkCount ; n ++){
            offsets[n] = IsoReader.readIntAt(fullData , stcoOffsetsLoc + (n * 4)) ;
            //System.out.println(offsets[n]);
        }
        System.out.println("Chunk count " + stcoChunkCount);
 
        
        //SAMPLE COUNT , Total Samples and bytes per sample
        int stsz = 2103830;
        int stszSampleSizeLoc = 2103830 + 8;
        int stszSampleCountLoc = 2103830 + 8 + 4;
        int stszSampleSizesLoc = 2103830 + 8 + 8;
        int stszSampleSize = IsoReader.readIntAt(fullData , stszSampleSizeLoc) ;
        int stszSampleCount = IsoReader.readIntAt(fullData , stszSampleCountLoc) ;
        System.out.println("Sample Size " + stszSampleSize);
        System.out.println("Sample Count " + stszSampleCount);
        //System.out.println("Sample size loc " + stszSampleSizesLoc);
        int[] sampleSizes = new int[stszSampleCount];
        
        for(int n = 0 ; n < stszSampleCount ; n ++){
            sampleSizes[n] = IsoReader.readIntAt(fullData , stszSampleSizesLoc + (n * 4)) ;
            //System.out.println(sampleSizes[n]);
        }
       
        
        //[index][offset, number of samples, sample size ]  
        int[][] totalData = new int[stcoChunkCount][3];  
        for (int n =0; n < stcoChunkCount; n++){
            totalData[n][0] = offsets[n];
        }
        
        
        int totalTestChunks = 0;
        int totalTestSamples = 0;
        
        
        //Sample To Chunk
        int stsc = 2102782;
        int stscEntryCountLoc = stsc + 8;
        int stscEntriesLoc = stsc + 12;
        int stscEntryCount = IsoReader.readIntAt(fullData , stscEntryCountLoc) ;
        System.out.println("Entry Count " + stscEntryCount);
        int[][] sampleTableBox = new int[stscEntryCount][3];
        int[] chunkRuns = new int[stscEntryCount];

        
        for(int n = 0 ; n < stscEntryCount ; n ++){
            sampleTableBox[n][0] = IsoReader.readIntAt(fullData , stscEntriesLoc + (n * 4 * 3) ) -1;
            sampleTableBox[n][1] = IsoReader.readIntAt(fullData , stscEntriesLoc + (n * 4 * 3) + 4) ;
            sampleTableBox[n][2] = IsoReader.readIntAt(fullData , stscEntriesLoc + (n * 4 * 3) + 8) ;
            //System.out.println("first chunk: " +sampleTableBox[n][0]);
            //System.out.println("samples per chunk " + sampleTableBox[n][1]);
            //System.out.println(sampleTableBox[n][2]);
            if(n >0){
                chunkRuns[n-1] = sampleTableBox[n][0] - sampleTableBox[n-1][0];
            }
       
        }
        chunkRuns[stscEntryCount-1] = stcoChunkCount- sampleTableBox[stscEntryCount-1][0];
        //Runs calculated
        
        //Now add samples to chunk for each run
        
        int currentIndex =0;
        for(int n = 0; n< stscEntryCount; n++){
            //chunkRuns[n]
            for(int i = 0; i < chunkRuns[n]; i++ ){
                totalData[currentIndex][1] = sampleTableBox[n][1];
                currentIndex++;
            }      
            //totalData[n][1] = 0; //Set num samples per chunk
        }
        
        //System.out.println("Total test chunks " + totalTestChunks);
        
        
        
        
        
        //now sum up the count
        int testSampleCount = 0;
        int testSampleSizeCount = 0;
        for (int n = 0; n < stcoChunkCount; n++) {
            //testChunkCount += totalData[n][0];
            testSampleCount += totalData[n][1];
        }
        System.out.println("Total sample tally " + testSampleCount);
        //System.out.println("Total testSample " + totalTestSamples);
        
        //stsz
        //stco
    }

}

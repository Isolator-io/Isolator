/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class Iso {
    
    public static final int FTYP = 0x66747970;
    public static final int FREE = 0x66726565;
    public static final int MDAT = 0x6D646174;
    public static final int MOOV = 0x6D6F6F76;
    public static final int MVHD = 0x6D766864;
    
    int size;
    FileOutputStream fop = null;
    File file;
    
    
    byte[] fullData;

    public Iso(File videoFile) throws FileNotFoundException, IOException {

         // Testing only! 
        byte[] header = {0x00, 0x00, 0x00, 0x18, 0x66, 0x74 ,0x79 ,0x70 ,0x4D, 0x34, 0x41, 0x20, 0x00 ,0x00 ,0x02 ,0x00 ,0x69, 0x73, 0x6F, 0x6D ,0x69 ,0x73, 0x6F ,0x32 ,0x00, 0x00 ,0x00, 0x08, 0x66 ,0x72 ,0x65, 0x65};
        //byte[] moovHeader = {0x6D 0x6F 0x6F 0x76 0x00 0x00 0x00 0x6C 0x6D 0x76 0x68 0x64 0x00 0x00 0x00 0x00 0x7C 0x25 0xB0 0x80 0xCF 0xF0 0x5A 0x35 0x00 0x00 0x03 0xE8 0x00 0x00 0x34 0xC0 0x00 0x01 0x00 0x00 0x01 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x01 0x00 0x00 0x00 0x00 0x00 0x00 00 00 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 40 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 03};
        
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
        int totalByteCount = 0;
        int stszSampleSize = IsoReader.readIntAt(fullData , stszSampleSizeLoc) ;
        int stszSampleCount = IsoReader.readIntAt(fullData , stszSampleCountLoc) ;
        System.out.println("Sample Size " + stszSampleSize);
        System.out.println("Sample Count " + stszSampleCount);
        //System.out.println("Sample size loc " + stszSampleSizesLoc);
        int[] sampleSizes = new int[stszSampleCount];
        
        for(int n = 0 ; n < stszSampleCount ; n ++){
            sampleSizes[n] = IsoReader.readIntAt(fullData , stszSampleSizesLoc + (n * 4)) ;
            totalByteCount += sampleSizes[n];
            //System.out.println(sampleSizes[n]);
        }
        System.out.println("total Byte count " + totalByteCount);
        
        //[index][offset, number of samples, byte size ]  
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
        int currentSampleIndex = 0;
        for(int n = 0; n< stscEntryCount; n++){
            //chunkRuns[n]
            for(int i = 0; i < chunkRuns[n]; i++ ){
                totalData[currentIndex][1] = sampleTableBox[n][1];
                totalData[currentIndex][2] = 0;
                for(int m = 0; m < sampleTableBox[n][1]; m++){
                    totalData[currentIndex][2] += sampleSizes[currentSampleIndex];
                    currentSampleIndex++;
                }
                
                currentIndex++;
            }      
            //totalData[n][1] = 0; //Set num samples per chunk
        }
        

     
        
        
        
        
        //now sum up the count
        int testSampleCount = 0;
        int testByteCount = 0;
        for (int n = 0; n < stcoChunkCount; n++) {
            testByteCount += totalData[n][2];
            testSampleCount += totalData[n][1];
        }
        System.out.println("Total sample tally " + testSampleCount);
        System.out.println("Total Bytes " + testByteCount);
        
        //stsz
        //stco
        try {
            file = new File("./testoutput.mp4a");
            fop = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            //byte[] contentInBytes = content.getBytes();

            //fop.write(contentInBytes);
            fop.write(header);
            fop.write(intToByteArray(totalByteCount));
            fop.write(MDAT);
            for (int n = 0; n < stcoChunkCount; n++) {
                //[n][0] offset
                //[n][2] byte count
                
                fop.write(fullData, totalData[n][0] , totalData[n][2]);
                //fop.write(fullData, 0 , 20);
            }
            
            
            fop.flush();
            fop.close();

        } catch (IOException e) {

        }
    }

    public byte[] intToByteArray(int input){
        return ByteBuffer.allocate(4).putInt(input).array();
    }
    
    

}

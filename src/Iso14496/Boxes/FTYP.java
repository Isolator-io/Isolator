/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

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
public class FTYP extends Box{
    int major_brand;
    int major_brand_offset;
    int minor_version;
    int minor_version_offset;
    int[] compatible_brands; // to end of the box
    int compatible_brands_offset;
    


    public FTYP() {
        super(Box.FTYP);
        headerOffset = 8; // default for the normal size
    }
    
    public void setMajorBrand(int brand){
        major_brand = brand;
    }
    
    public void setMinorVersion(int minor){
        minor_version = minor;
    }
    
    public void setCompatibleBrands(int[] brands){
        compatible_brands = brands;
    }

    @Override
    public byte[] toBinary() {
        
        byteStream.reset();
        byte[] binaryData;
        
        
        try {
            System.out.println("major brand : " + major_brand);
            if (compatible_brands != null) {
               byteStream.write(intToByteArray(16 + (compatible_brands.length * 4))); //size
               byteStream.write(intToByteArray(type)); //type 
               byteStream.write(intToByteArray(major_brand)); //type
               byteStream.write(intToByteArray(minor_version)); //type
               
               for(int n = 0; n < compatible_brands.length; n++){
                   byteStream.write(intToByteArray(compatible_brands[n]));
               }
               
            } else {
               byteStream.write(intToByteArray(16)); //size
               byteStream.write(intToByteArray(type)); //type 
               byteStream.write(intToByteArray(major_brand)); //type
               byteStream.write(intToByteArray(minor_version)); //type
            }
            
            //byteStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(FTYP.class.getName()).log(Level.SEVERE, null, ex);
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
        if(internalSize == 1){
            isExtendedSize = true;
            headerOffset = 8 + 16; // total bytes with extended header
        }
        
        major_brand = IsoReader.readIntAt(fileData, internalOffset + headerOffset); //get box size
        minor_version = IsoReader.readIntAt(fileData, internalOffset + headerOffset + 4);
        
        int totalCompatibleBrands = (internalSize - headerOffset - 8) / 4;
        compatible_brands = new int[totalCompatibleBrands];
        for(int n = 0; n < totalCompatibleBrands; n++){
            compatible_brands[n] = IsoReader.readIntAt(fileData, internalOffset + headerOffset + 8);
        }
        
        
        
        
        
        
      
    }
    
        
    public String toString(){
        return IsoFile.toASCII(type) + " major brand: " + IsoFile.toASCII(major_brand)
                + ", minor version : " + minor_version;
  
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;

/**
 *
 * @author mac
 */
public class FileTypeBox extends Box{
    int majorBrand;
    int minorVersion;
    int[] compatibleBrands;

    public FileTypeBox(int boxtype) {
        super(boxtype);
    }
    
 
    
}

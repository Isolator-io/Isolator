/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

/**
 *
 * @author mac
 */
public abstract class FullBox extends Box{
    byte version;
    byte[] flags;

    public FullBox(int boxtype) {
        super(boxtype);
    }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifradordes;

/**
 *
 * @author josep
 */
public class CifradorDES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Procesamiento pro = new Procesamiento("holamundo","11223344");
        pro.descifrar("OFB", "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_ofb.bmp");
    }
    
}

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
        Procesamiento pro = new Procesamiento("holamundo");
        pro.descifrar("ECB", "C:\\Users\\josep\\OneDrive\\Escritorio\\salida.bmp");
    }
    
}

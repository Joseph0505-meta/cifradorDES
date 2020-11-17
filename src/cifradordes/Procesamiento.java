/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifradordes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;

public class Procesamiento {

    private SecretKey clave; //llave
    private IvParameterSpec vector;

    public Procesamiento(String llave, String vi) throws Exception {
        byte vo[] = vi.getBytes();
        if (vo.length!=8) {
            System.out.println("ERROR el tamaño del vector de incialización debe de ser de 8 bytes");
            System.exit(0);
        }
        vector = new IvParameterSpec(vo);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec(llave.getBytes());
        clave = skf.generateSecret(kspec);
    }

    public void cifrar(String mOperacion, String ruta) throws Exception {
        //Se indica el modo de operacion
        Cipher cifrador = Cipher.getInstance("DES/" + mOperacion + "/PKCS5Padding");   
        String ruta_salida = "";
        
        if (mOperacion.equals("ECB")) {
            ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_ecb.bmp";
            cifrador.init(Cipher.ENCRYPT_MODE, clave);
        }else if (mOperacion.equals("CBC")) {
             ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_cbc.bmp";
             cifrador.init(Cipher.ENCRYPT_MODE, clave,vector);
        }else if (mOperacion.equals("CFB")) {
             ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_cfb.bmp";
             cifrador.init(Cipher.ENCRYPT_MODE, clave,vector);
        }else if (mOperacion.equals("OFB")) {
            ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_ofb.bmp";
            cifrador.init(Cipher.ENCRYPT_MODE, clave,vector);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage img = ImageIO.read(new File(ruta));
        ImageIO.write(img, "BMP", baos);
        baos.flush();

        Base64.Encoder encoder = Base64.getEncoder();
        String base64String = encoder.encodeToString(baos.toByteArray());
        baos.close();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] arreglo = decoder.decode(base64String);

        byte[] textCifrado;
        
        byte[] texto = new byte[arreglo.length];
        
        int z =0;
        for (int i = 54; i <arreglo.length ; i++) {
            texto[z] = arreglo[i];
            z++;
        }
        textCifrado = cifrador.update(texto);

        byte[] imagenCifrada = new byte[arreglo.length];

        int j = 0;
        for (int i = 0; i < arreglo.length; i++) {
            if (i < 54) {
                imagenCifrada[i] = arreglo[i];
            } else {
                imagenCifrada[i] = textCifrado[j];
                j++;
            }
        }

        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(imagenCifrada));
        ImageIO.write(imag, "BMP", new File(ruta_salida));
        

    }

    public void descifrar(String mOperacion, String ruta) throws Exception {
        Cipher cifrador = Cipher.getInstance("DES/" + mOperacion + "/PKCS5Padding");
        
        String ruta_salida = "";
        
        if (mOperacion.equals("ECB")) {
            ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_decb.bmp";
            cifrador.init(Cipher.DECRYPT_MODE, clave);
        }else if (mOperacion.equals("CBC")) {
             ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_dcbc.bmp";
             cifrador.init(Cipher.DECRYPT_MODE, clave,vector);
        }else if (mOperacion.equals("CFB")) {
             ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_dcfb.bmp";
             cifrador.init(Cipher.DECRYPT_MODE, clave,vector);
        }else if (mOperacion.equals("OFB")) {
            ruta_salida = "C:\\Users\\josep\\OneDrive\\Escritorio\\thundercats_dofb.bmp";
            cifrador.init(Cipher.DECRYPT_MODE, clave,vector);
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage img = ImageIO.read(new File(ruta));
        ImageIO.write(img, "BMP", baos);
        baos.flush();

        Base64.Encoder encoder = Base64.getEncoder();
        String base64String = encoder.encodeToString(baos.toByteArray());
        baos.close();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] arreglo = decoder.decode(base64String);
        
         byte[] texto = new byte[arreglo.length];
        
        int z =0;
        for (int i = 54; i <arreglo.length ; i++) {
            texto[z] = arreglo[i];
            z++;
        }

        byte[] textDescifrado;
        textDescifrado = cifrador.update(texto);

        byte[] imagenCifrada = new byte[arreglo.length];

        int j = 0;
        for (int i = 0; i < arreglo.length; i++) {
            if (i < 54) {
                imagenCifrada[i] = arreglo[i];
            } else {
                imagenCifrada[i] = textDescifrado[j];
                j++;
            }
        }

        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(imagenCifrada));
        ImageIO.write(imag, "BMP", new File(ruta_salida));
         
    }

}

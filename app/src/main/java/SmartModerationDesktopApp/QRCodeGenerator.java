/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SmartModerationDesktopApp;


import java.io.File;  
import java.io.IOException;  
import java.util.HashMap;  
import java.util.Map;  
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.NotFoundException;  
import com.google.zxing.WriterException;  
import com.google.zxing.client.j2se.MatrixToImageWriter;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel; 
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
        
/**
 *
 * @author andreas
 */

public class QRCodeGenerator {
    
    private final String charset = "UTF-8";  
    private final int width = 200;
    private final int height = 200;
 

//static function that creates QR Code  
public  BufferedImage generateQRcode(String data) throws WriterException, UnsupportedEncodingException  
{  
BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);  
return MatrixToImageWriter.toBufferedImage(matrix);  
}  

} 
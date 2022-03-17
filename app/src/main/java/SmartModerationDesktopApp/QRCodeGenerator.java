/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SmartModerationDesktopApp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import javax.swing.ImageIcon;

/**
 *
 * @author andreas
 */
public class QRCodeGenerator {

    private final String charset = "UTF-8";
    private final int width = 200;
    private final int height = 200;

//static function that creates QR Code  
    public ImageIcon StringToQRCodeToIcon(String data) throws WriterException, UnsupportedEncodingException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        return new ImageIcon(bufferedImage);
    }

}

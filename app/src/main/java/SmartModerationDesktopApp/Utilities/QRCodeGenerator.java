package SmartModerationDesktopApp.Utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.UnsupportedEncodingException;
import javax.swing.ImageIcon;

public class QRCodeGenerator {
    
    private final String charset = "ISO-8859-1";
    private final int width = 400;
    private final int height = 400;
    
    public ImageIcon stringToQRCodeToIcon(String data) throws WriterException, UnsupportedEncodingException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        return new ImageIcon(MatrixToImageWriter.toBufferedImage(matrix));
    }
}
package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.MainWindow;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.UnsupportedEncodingException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class QRCodeGenerator {
    
    private final String charset = "ISO-8859-1";
    private final int width = 400;
    private final int height = 400;
    
    private ImageIcon StringToQRCodeToIcon(String data) throws WriterException, UnsupportedEncodingException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        return new ImageIcon(MatrixToImageWriter.toBufferedImage(matrix));
    }
    
    public Icon createLoginQRCode(MainWindow mainWindow) throws WriterException, UnsupportedEncodingException {
        return StringToQRCodeToIcon(mainWindow.getJsonWriter().getLoginInformationJson(mainWindow.getServer()));
    }
}

package SmartModerationDesktopApp;

import SmartModerationDesktopApp.MainWindow.MainWindow;
import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import SmartModerationDesktopApp.Utilities.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.awt.GraphicsDevice;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartModerationDesktopApp {

    public static void main(String[] args) {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        JsonWriter jsonWriter = new JsonWriter();
        MainWindow mainWindow = MainWindow.getInstance();

        Server server = new Server();
        server.initObserver(mainWindow);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            GraphicsDevice device = mainWindow.getGraphicsEnvironment().getDefaultScreenDevice();
            device.setFullScreenWindow(mainWindow);
            mainWindow.setVisible(true);
            try {
                try {
                    mainWindow.setQRCodeLabel(qrCodeGenerator.stringToQRCodeToIcon(jsonWriter.getLoginInformationJson(server)));
                } catch (WriterException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        server.createServer();
    }
}

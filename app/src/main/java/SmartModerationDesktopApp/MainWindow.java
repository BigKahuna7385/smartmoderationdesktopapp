package SmartModerationDesktopApp;

import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Utilities.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.UnsupportedEncodingException;
import javax.swing.Icon;

public class MainWindow extends javax.swing.JFrame {

    private Server server;
    private StringBuilder qrString;

    public MainWindow() {
        setExtendedState(MAXIMIZED_BOTH);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        QRCode = new javax.swing.JLabel();
        QRCodeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setSize(new java.awt.Dimension(1920, 1080));

        QRCode.setToolTipText("Scan QRCode to start session");
        QRCode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        QRCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QRCodeLabel.setText("Scan QR-code to start session");
        QRCodeLabel.setFocusable(false);
        QRCodeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(760, 760, 760)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(QRCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(760, 760, 760))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(QRCodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        MainWindow mainWindow = new MainWindow();
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        mainWindow.createNewServer();
        mainWindow.qrString = new StringBuilder(mainWindow.getServer().getIpAddressAndPortAsString());
        mainWindow.qrString.append("\n");
        mainWindow.qrString.append(mainWindow.server.getApiKey());

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
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = graphics.getDefaultScreenDevice();
            device.setFullScreenWindow(mainWindow);
            mainWindow.placeModerationCard();

            try {
                mainWindow.setQRCodeLabel(qrCodeGenerator.StringToQRCodeToIcon(mainWindow.qrString.toString()));
            } catch (UnsupportedEncodingException | WriterException ex) {
            }
        });

        mainWindow.getServer().createServer();
    }

    private void placeModerationCard() {
        ModerationCard moderationCard = new ModerationCard(50, 50);
        getContentPane().add(moderationCard);
        moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
    }

    public void setQRCodeLabel(Icon icon) {
        QRCode.setIcon(icon);
    }

    public void createNewServer() {
        server = new Server();
    }

    public Server getServer() {
        return server;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QRCode;
    private javax.swing.JLabel QRCodeLabel;
    // End of variables declaration//GEN-END:variables
}

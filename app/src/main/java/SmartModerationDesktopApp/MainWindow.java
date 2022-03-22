package SmartModerationDesktopApp;

import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Utilities.JsonReader;
import SmartModerationDesktopApp.Utilities.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.swing.Icon;

public class MainWindow extends javax.swing.JFrame {

    private Server server;
    private StringBuilder qrString;
    private boolean isLineDrawn = false;
    private boolean hasLineDistance = false;
    private final ArrayList<ModerationCard> moderationCardList;

    public MainWindow() {
        setExtendedState(MAXIMIZED_BOTH);
        moderationCardList = new ArrayList<>();
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
        JsonReader jsonReader = new JsonReader();
        jsonReader.parseJson();
        MainWindow mainWindow = new MainWindow();
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        mainWindow.createNewServer();
        if (mainWindow.getServer().getIpAddressAndPortAsString() != null) {
            mainWindow.qrString = new StringBuilder(mainWindow.getServer().getIpAddressAndPortAsString());
            mainWindow.qrString.append("\n");
            mainWindow.qrString.append(mainWindow.server.getApiKey());
            System.out.println("Server");
        } else {
            mainWindow.qrString = new StringBuilder("No server setup possible");
        }

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
        // dummy moderation cards are placed
        ModerationCard moderationCard = new ModerationCard(50, 50, this);
        getContentPane().add(moderationCard);
        moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
        moderationCardList.add(moderationCard);
        moderationCard.setModerationCardList(moderationCardList);

        ModerationCard moderationCard2 = new ModerationCard(100, 100, this);
        getContentPane().add(moderationCard2);
        moderationCard2.setBounds(moderationCard2.getX(), moderationCard2.getY(), moderationCard2.getPreferredSize().width, moderationCard2.getPreferredSize().height);
        moderationCardList.add(moderationCard2);
        moderationCard2.setModerationCardList(moderationCardList);

        ModerationCard moderationCard3 = new ModerationCard(150, 150, this);
        getContentPane().add(moderationCard3);
        moderationCard3.setBounds(moderationCard3.getX(), moderationCard3.getY(), moderationCard3.getPreferredSize().width, moderationCard3.getPreferredSize().height);
        moderationCardList.add(moderationCard3);
        moderationCard3.setModerationCardList(moderationCardList);

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

    public void drawDottedLineBetween(ModerationCard movingCard, ModerationCard magneticCard) {
        if (movingCard.isDistancedMagnet() != hasLineDistance) {
            clearBackground();
            hasLineDistance = movingCard.isDistancedMagnet();
        }

        Graphics2D g2d = (Graphics2D) getGraphics();
        float dashPhase = 0f;
        float dash[] = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.5f, dash, dashPhase));
        int distance = hasLineDistance ? magneticCard.getMAGNETRANGE() : 0;
        switch (movingCard.getSnapDirection()) {
            case EAST:
                g2d.drawLine(magneticCard.getBounds().x - distance, 0, magneticCard.getBounds().x - distance, getContentPane().getMaximumSize().height);
                break;
            case WEST:
                g2d.drawLine(magneticCard.getBounds().x + magneticCard.getBounds().width + distance, 0, magneticCard.getBounds().x + magneticCard.getBounds().width + distance, getContentPane().getMaximumSize().height);
                break;
            case NORTH:
                g2d.drawLine(0, magneticCard.getY() + 35 - distance, getContentPane().getMaximumSize().width, magneticCard.getY() + 35 - distance);
                break;
            case SOUTH:
                g2d.drawLine(0, magneticCard.getY() + magneticCard.getBounds().height + 35 + distance, getContentPane().getMaximumSize().width, magneticCard.getY() + magneticCard.getBounds().height + 35 + distance);
                break;
        }
        isLineDrawn = true;
    }

    //TOCO: clear only drawn line
    public void clearBackground() {
        if (!isLineDrawn) {
            return;
        }
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.clearRect(0, 0, this.getHeight(), this.getWidth());
        revalidate();
        repaint();
        isLineDrawn = false;
    }
}

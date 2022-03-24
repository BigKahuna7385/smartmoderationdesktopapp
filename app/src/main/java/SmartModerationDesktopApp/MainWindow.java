package SmartModerationDesktopApp;

import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Server.Client;
import SmartModerationDesktopApp.Utilities.JsonReader;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import SmartModerationDesktopApp.Utilities.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;

public class MainWindow extends javax.swing.JFrame {

    private final Server server;
    private final Client client;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;
    private final ArrayList<ModerationCard> moderationCardList;
    //TODO: fetch meeting ID in login process to load moderation cards
    private final long meetingId = 3570151905752727837L;
    private boolean isLineDrawn = false;
    private boolean hasLineDistance = false;
    private StringBuilder qrString;

    public MainWindow() {
        setExtendedState(MAXIMIZED_BOTH);
        client = new Client();
        server = new Server();
        jsonReader = new JsonReader();
        jsonWriter = new JsonWriter();
        moderationCardList = new ArrayList<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        QRCode = new javax.swing.JLabel();
        QRCodeLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setSize(new java.awt.Dimension(1920, 1080));

        QRCode.setToolTipText("Scan QRCode to start session");
        QRCode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        QRCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QRCodeLabel.setText("Scan QR-code to start session");
        QRCodeLabel.setFocusable(false);
        QRCodeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(760, 760, 760)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(QRCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1770, 1770, 1770)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200)
                .addComponent(QRCodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        jsonWriter.saveMeetingStatus(meetingId, moderationCardList);
    }//GEN-LAST:event_saveButtonActionPerformed

    public static void main(String args[]) throws IOException {
        MainWindow mainWindow = new MainWindow();

        //TODO: move to login procedure
        mainWindow.moderationCardList.addAll(mainWindow.jsonReader.parseModerationCardJson(mainWindow.client.getModerationCards()));

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();

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
            mainWindow.placeModerationCards();

            try {
                mainWindow.setQRCodeLabel(qrCodeGenerator.StringToQRCodeToIcon(mainWindow.qrString.toString()));
            } catch (UnsupportedEncodingException | WriterException ex) {
            }
        });

        mainWindow.getServer().createServer();
    }

    //TODO: move to factory
    //TODO: Add logic if moderation cards are loaded from cache
    private void placeModerationCards() {
        
        File moderationCardCacheFile = new File("./cache/" + meetingId + ".json");
        final boolean cacheExists = moderationCardCacheFile.exists();
        
        
        //TODO check if cards have been deleted in app and dont load them from cache
        if(cacheExists){
            HashMap<Long, Point> cachedModerationCardPositions = jsonReader.parseCacheJson(moderationCardCacheFile);
            moderationCardList.forEach((moderationCard) ->{
                if(cachedModerationCardPositions.containsKey(moderationCard.getCardId())){
                    Point point = cachedModerationCardPositions.get(moderationCard.getCardId());
                    moderationCard.setX(point.x);
                    moderationCard.setY(point.y);
                }
            });
        }
        
        moderationCardList.forEach((moderationCard) -> {
            moderationCard.setMainWindow(this);
            getContentPane().add(moderationCard);
            moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
            moderationCard.setModerationCardList(moderationCardList);
        });
    }

    public void setQRCodeLabel(Icon icon) {
        QRCode.setIcon(icon);
    }

    public Server getServer() {
        return server;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QRCode;
    private javax.swing.JLabel QRCodeLabel;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    //TODO: move to own class 
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
                g2d.drawLine(0, magneticCard.getY() - distance, getContentPane().getMaximumSize().width, magneticCard.getY() - distance);
                break;
            case SOUTH:
                g2d.drawLine(0, magneticCard.getY() + magneticCard.getBounds().height + distance, getContentPane().getMaximumSize().width, magneticCard.getY() + magneticCard.getBounds().height + distance);
                break;
        }
        isLineDrawn = true;
    }

    //TODO: clear only drawn line
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

package SmartModerationDesktopApp;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.ModerationCards.ModerationCardFactory;
import SmartModerationDesktopApp.Observer.ServerObserver;
import SmartModerationDesktopApp.Server.Client;
import SmartModerationDesktopApp.Server.LoginInformation;
import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.Utilities.JsonReader;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import SmartModerationDesktopApp.Utilities.LineDrawer;
import SmartModerationDesktopApp.Utilities.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import org.json.simple.parser.ParseException;

public class MainWindow extends javax.swing.JFrame implements ServerObserver {

    private final Server server;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;
    private final LineDrawer lineDrawer;
    private final GraphicsEnvironment graphicsEnvironment;
    private final ArrayList<ModerationCard> moderationCards;
    private final ModerationCardFactory moderationCardFactory;
    private Client client;
    private long meetingId;
    private boolean isLineDrawn = false;
    private boolean hasLineDistance = false;
    private boolean isClientLoggedIn = false;

    public MainWindow() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        server = new Server();
        jsonReader = new JsonReader();
        jsonWriter = new JsonWriter();
        lineDrawer = new LineDrawer(this);
        moderationCards = new ArrayList<>();
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        moderationCardFactory = new ModerationCardFactory(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        QRCode = new javax.swing.JLabel();
        QRCodeLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
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
                .addContainerGap(760, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(QRCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(760, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(QRCodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        jsonWriter.saveMeetingStatus(meetingId, moderationCards);
    }//GEN-LAST:event_saveButtonActionPerformed

    public static void main(String args[]) throws IOException {
        MainWindow mainWindow = new MainWindow();
        mainWindow.getServer().initObserver(mainWindow);
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
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
                    mainWindow.setQRCodeLabel(qrCodeGenerator.createLoginQRCode(mainWindow));
                } catch (WriterException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mainWindow.getServer().createServer();
    }

    public void processLogin(String loginString) throws ParseException, IOException {
        readLoginInformation(jsonReader.readLoginInformationJson(loginString));
        QRCode.setVisible(false);
        QRCodeLabel.setVisible(false);
        moderationCards.addAll(jsonReader.parseModerationCardJson(client.getModerationCards()));
        moderationCardFactory.placeModerationCards();
        isClientLoggedIn = true;
    }

    private void readLoginInformation(LoginInformation loginInformation) {
        setMeetingId(loginInformation.getMeetingId());
        client = new Client(loginInformation);
    }

    public void setQRCodeLabel(Icon icon) {
        QRCode.setIcon(icon);
    }

    public Server getServer() {
        return server;
    }

    public ArrayList<ModerationCard> getModerationCards() {
        return this.moderationCards;
    }

    public long getMeetingId() {
        return meetingId;
    }

    public boolean isIsLineDrawn() {
        return isLineDrawn;
    }

    public boolean isHasLineDistance() {
        return hasLineDistance;
    }

    public void setHasLineDistance(boolean hasLineDistance) {
        this.hasLineDistance = hasLineDistance;
    }

    public void setIsLineDrawn(boolean isLineDrawn) {
        this.isLineDrawn = isLineDrawn;
    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public GraphicsEnvironment getGraphicsEnvironment() {
        return graphicsEnvironment;
    }

    public void setMeetingId(long meetingId) {
        this.meetingId = meetingId;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QRCode;
    private javax.swing.JLabel QRCodeLabel;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void receiveLogin(String message) {
        try {
            System.out.println("ReveiceLogin: " + message);
            processLogin(message);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void receivePutModerationCard(String message) {
        if (!isClientLoggedIn) {
            System.out.println("Client not logged in!");
            return;
        }
        try {
            System.out.println("Try to put new moderation card.");
            moderationCards.add(jsonReader.parseSingleModerationCardJson(message));
            moderationCardFactory.placeModerationCards();
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
        repaint();
    }

    @Override
    public void receiveDeleteModerationCard(String message) {

        if (!isClientLoggedIn) {
            System.out.println("Client not logged in!");
            return;
        }
        try {
            System.out.println("Try to delete moderation card.");
            ModerationCard tempModerationCard = jsonReader.parseSingleModerationCardJson(message);
            for (ModerationCard moderationCard : moderationCards) {
                if (moderationCard.getCardId() == tempModerationCard.getCardId()) {
                    System.out.println("Found card to delete.");
                    moderationCards.remove(moderationCard);
                    getContentPane().remove(moderationCard);
                    break;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
        repaint();
    }

    @Override
    public void receiveUpdateModerationCard(String message) {
        if (!isClientLoggedIn) {
            return;
        }
        try {
            ModerationCard tempModerationCard = jsonReader.parseSingleModerationCardJson(message);
            for (ModerationCard moderationCard : moderationCards) {
                if (moderationCard.getCardId() == tempModerationCard.getCardId()) {
                    moderationCard.updateProperties(tempModerationCard);
                    break;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
        repaint();
    }   
}

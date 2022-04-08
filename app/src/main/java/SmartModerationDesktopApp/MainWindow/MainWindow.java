package SmartModerationDesktopApp.MainWindow;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.ModerationCards.ModerationCardFactory;
import SmartModerationDesktopApp.Observer.ServerObserver;
import SmartModerationDesktopApp.Server.Client;
import SmartModerationDesktopApp.Server.LoginInformation;
import SmartModerationDesktopApp.Server.Server;
import SmartModerationDesktopApp.Utilities.JsonReader;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import SmartModerationDesktopApp.Utilities.LineDrawer;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
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
    private ModerationCardFactory moderationCardFactory;
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

    private void processLogin(String loginString) throws ParseException, IOException {
        readLoginInformation(jsonReader.readLoginInformationJson(loginString));
        QRCode.setVisible(false);
        QRCodeLabel.setVisible(false);
        isClientLoggedIn = true;
        initializeModerationCards();
    }

    private void initializeModerationCards() throws IOException {
        String loginString = client.getModerationCards();
        ArrayList<ModerationCard> inputCards = jsonReader.parseModerationCardJson(loginString);
        moderationCards.addAll(inputCards);
        moderationCardFactory = new ModerationCardFactory(moderationCards, meetingId);
        moderationCardFactory.loadModerationCardPositionsFromCache();
        for (ModerationCard moderationCard : moderationCards) {
            moderationCard.setMainWindow(this);
            getContentPane().add(moderationCard);
            moderationCardFactory.setFanout(moderationCard);
        }
        revalidate();
        repaint();
    }

    private void readLoginInformation(LoginInformation loginInformation) {
        setMeetingId(loginInformation.getMeetingId());
        client = new Client(loginInformation);
    }

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
            System.out.println("Message: " + message);
            ModerationCard moderationCard = jsonReader.parseSingleModerationCardJson(message);
            moderationCard.setMainWindow(this);
            moderationCards.add(moderationCard);
            getContentPane().add(moderationCard);
            moderationCardFactory.setFanout(moderationCard);
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
        repaint();
    }

    @Override
    public void receiveDeleteModerationCard(long cardId) {
        if (!isClientLoggedIn) {
            System.out.println("Client not logged in!");
            return;
        }
        System.out.println("Try to delete moderation card.");
        for (ModerationCard moderationCard : moderationCards) {
            if (moderationCard.getCardId() == cardId) {
                System.out.println("Found card to delete.");
                moderationCards.remove(moderationCard);
                getContentPane().remove(moderationCard);
                break;
            }
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
}

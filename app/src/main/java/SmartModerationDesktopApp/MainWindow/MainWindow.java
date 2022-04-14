package SmartModerationDesktopApp.MainWindow;

import SmartModerationDesktopApp.ModerationCards.ModerationCardsController;
import SmartModerationDesktopApp.Observer.ServerObserver;
import SmartModerationDesktopApp.Server.Client;
import SmartModerationDesktopApp.Utilities.LineDrawer;
import SmartModerationDesktopApp.Server.LoginController;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import org.json.simple.parser.ParseException;

public class MainWindow extends javax.swing.JFrame implements ServerObserver {

    private final LineDrawer lineDrawer;
    private final LoginController loginController;
    private final ModerationCardsController moderationCardsController;
    private static MainWindow mainWindow;
    private Client client;

    public MainWindow() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        lineDrawer = new LineDrawer();
        loginController = new LoginController();
        moderationCardsController = new ModerationCardsController();
    }

    public static MainWindow getInstance() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
        }
        return mainWindow;
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
        moderationCardsController.saveMeetingStatus();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void initializeModerationCards() throws IOException {
        moderationCardsController.initializeModerationCards(client.getModerationCards());
        revalidate();
        repaint();
    }

    private void readLoginInformation(String loginInformation) throws ParseException {
        loginController.readLoginInformation(loginInformation);
        setMeetingId(loginController.getMeetingId());
        client = new Client(loginController.getLoginInformation());
    }

    @Override
    public void receiveLogin(String message) {
        try {
            System.out.println("ReveiceLogin: " + message);
            readLoginInformation(message);
            QRCode.setVisible(false);
            QRCodeLabel.setVisible(false);
            initializeModerationCards();
        } catch (ParseException | IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void receivePutModerationCard(String message) {
        moderationCardsController.putModerationCard(message);
        revalidate();
        repaint();
    }

    @Override
    public void receiveDeleteModerationCard(long cardId) {
        moderationCardsController.deleteModerationCard(cardId);
        revalidate();
        repaint();
    }

    @Override
    public void receiveUpdateModerationCard(String message) {
        moderationCardsController.updateModerationCard(message);
        revalidate();
        repaint();
    }

    public void setQRCodeLabel(Icon icon) {
        QRCode.setIcon(icon);
    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public GraphicsEnvironment getGraphicsEnvironment() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment();
    }

    public void setMeetingId(long meetingId) {
        this.moderationCardsController.setMeetingId(meetingId);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QRCode;
    private javax.swing.JLabel QRCodeLabel;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}

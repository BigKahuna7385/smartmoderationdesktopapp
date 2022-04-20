package SmartModerationDesktopApp.MainWindow;

import SmartModerationDesktopApp.ModerationCards.ModerationCardsController;
import SmartModerationDesktopApp.Observer.ClientObserver;
import SmartModerationDesktopApp.Observer.ServerObserver;
import SmartModerationDesktopApp.Server.Client;
import SmartModerationDesktopApp.Utilities.LineDrawer;
import SmartModerationDesktopApp.Server.LoginController;
import SmartModerationDesktopApp.Tutorial.Tutorial;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.json.simple.parser.ParseException;
import javax.swing.JOptionPane;

public class MainWindow extends javax.swing.JFrame implements ServerObserver, ClientObserver {

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
        setIcon();
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
        quickGuide = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Smart Moderation DesktopApp");
        setAutoRequestFocus(false);
        setSize(new java.awt.Dimension(1920, 1080));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        QRCode.setToolTipText("Scan QRCode to start session");
        QRCode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        QRCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QRCodeLabel.setText("Scan QR-code to start session");
        QRCodeLabel.setFocusable(false);
        QRCodeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        quickGuide.setLabel("Login Quick Guide");
        quickGuide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickGuideActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(766, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QRCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quickGuide))
                .addContainerGap(760, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(QRCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(quickGuide, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        quickGuide.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void quickGuideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickGuideActionPerformed
        Tutorial tutorial = Tutorial.getInstance();
        tutorial.setVisible(true);
    }//GEN-LAST:event_quickGuideActionPerformed


    private void initializeModerationCards(String moderationCardsJson) throws IOException {
        moderationCardsController.initializeModerationCards(moderationCardsJson);
        revalidate();
        repaint();
    };

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        moderationCardsController.saveMeetingStatus();
    }//GEN-LAST:event_formWindowClosing

    private void readLoginInformation(String loginInformation) throws ParseException, IOException {
        loginController.readLoginInformation(loginInformation);
        setMeetingId(loginController.getMeetingId());
        client = new Client(loginController.getLoginInformation());
        client.initObserver(this);
        client.getModerationCards();
    }

    @Override
    public void receiveLogin(String message) {
        try {
            System.out.println("ReveiceLogin: " + message);
            readLoginInformation(message);
            QRCode.setVisible(false);
            QRCodeLabel.setVisible(false);
            quickGuide.setVisible(false);            
        } catch (ParseException | IOException ex) {
            JOptionPane.showMessageDialog(this, "No connection to Android host device.");
            QRCode.setVisible(true);
            QRCodeLabel.setVisible(true);
            quickGuide.setVisible(true);
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

    public void sendDeleteModerationCard(long cardId) {
        if (client.deleteModerationCard(cardId)) {
            moderationCardsController.deleteModerationCard(cardId);
        }
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

    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("smartModerationIcon.png"));
        setIconImage(icon.getImage());
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QRCode;
    private javax.swing.JLabel QRCodeLabel;
    private javax.swing.JButton quickGuide;
    // End of variables declaration//GEN-END:variables

    @Override
    public void getModerationCardsJsonString(String moderationCardJson) {
        try {
            initializeModerationCards(moderationCardJson);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

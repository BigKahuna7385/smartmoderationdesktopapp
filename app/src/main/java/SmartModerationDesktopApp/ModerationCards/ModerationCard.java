package SmartModerationDesktopApp.ModerationCards;

import static SmartModerationDesktopApp.ModerationCards.SnapDirection.*;
import SmartModerationDesktopApp.MainWindow;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class ModerationCard extends javax.swing.JPanel {

    private Point location;
    private MouseEvent pressed;
    private int x;
    private int y;
    private final int MAGNETRANGE = 30;
    private ArrayList<ModerationCard> dockedModerationCardList;
    private ArrayList<ModerationCard> moderationCardList;
    private ModerationCard magneticCard;
    private Component currentComponent;
    private boolean distancedMagnet = false;
    private MainWindow mainWindow;
    private SnapDirection snapDirection;
    private long cardId;
    private long meetingId;

    public ModerationCard(long cardId, long meetingId, String content, String backgroundColor) {
        initComponents();
        this.cardId = cardId;
        this.meetingId = meetingId;
        this.moderationCardTextBody.setText(content);
        this.setBackground(Color.decode(backgroundColor));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        moderationCardTextBody = new javax.swing.JTextArea();
        separator = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(153, 153, 255));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setToolTipText("Click to drag card");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(200, 200));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        cardTitle.setText("Titel");

        moderationCardTextBody.setEditable(false);
        moderationCardTextBody.setColumns(20);
        moderationCardTextBody.setRows(5);
        moderationCardTextBody.setText("Body");
        jScrollPane1.setViewportView(moderationCardTextBody);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(cardTitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(separator, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cardTitle)
                .addGap(5, 5, 5)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pressed = evt;
        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        currentComponent = evt.getComponent();
        location = currentComponent.getLocation(location);
        int dX = evt.getX() - pressed.getX();
        int dY = evt.getY() - pressed.getY();
        x = location.x + dX;
        y = location.y + dY;

        if (x < 0) {
            x = 0;
        } else if (x > 1920 - getPreferredSize().width) {
            x = 1920 - getBounds().width;
        }

        if (y < 0) {
            y = 0;
        } else if (y > 1080 - getPreferredSize().height) {
            y = 1080 - getBounds().height;
        }

        if (snapTo(isCardMagnetic())) {
            mainWindow.drawDottedLineBetween(this, magneticCard);
        } else {
            mainWindow.clearBackground();
        }

        currentComponent.setLocation(x, y);
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        if (magneticCard != null) {
            //dockedModerationCardList.add(magneticCard);
            int distance = isDistancedMagnet() ? MAGNETRANGE : 0;
            switch (snapDirection) {
                case WEST:
                    x = magneticCard.x + magneticCard.getBounds().width + distance;
                    break;
                case EAST:
                    x = magneticCard.x - getBounds().width - distance;
                    break;
                case SOUTH:
                    y = magneticCard.y + magneticCard.getBounds().height + distance;
                    break;
                case NORTH:
                    y = magneticCard.y - getBounds().height - distance;
                    break;
                default:
                    throw new AssertionError();
            }
            currentComponent.setLocation(x, y);
        }
        mainWindow.clearBackground();
    }//GEN-LAST:event_formMouseReleased

    public void keyPressed(KeyEvent evt) {

    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea moderationCardTextBody;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables

    private ModerationCard isCardMagnetic() {
        distancedMagnet = false;
        ModerationCard returnModerationCard = null;

        for (ModerationCard moderationCard : moderationCardList) {
            if ((y > moderationCard.getY() && y < moderationCard.getY() + moderationCard.getBounds().height) || (y + getBounds().height > moderationCard.getY() && y + getBounds().height < moderationCard.getY() + moderationCard.getBounds().height)) {
                if (getBounds().x < moderationCard.getBounds().x + moderationCard.getBounds().width + MAGNETRANGE && getBounds().x > moderationCard.getBounds().x + moderationCard.getBounds().width - MAGNETRANGE) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = false;
                    snapDirection = WEST;
                } else if (getBounds().x < moderationCard.getBounds().x + moderationCard.getBounds().width + 2 * MAGNETRANGE && getBounds().x >= moderationCard.getBounds().x + moderationCard.getBounds().width + MAGNETRANGE) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = true;
                    snapDirection = WEST;
                } else if (getBounds().x + getBounds().width - MAGNETRANGE < moderationCard.getBounds().x && getBounds().x + getBounds().width + MAGNETRANGE > moderationCard.getBounds().x) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = false;
                    snapDirection = EAST;
                } else if (getBounds().x + getBounds().width + MAGNETRANGE < moderationCard.getBounds().x && getBounds().x + getBounds().width + 2 * MAGNETRANGE >= moderationCard.getBounds().x) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = true;
                    snapDirection = EAST;
                }
            }
            if ((getBounds().x > moderationCard.getBounds().x && getBounds().x < moderationCard.getBounds().x + moderationCard.getBounds().width) || (getBounds().x + getBounds().width > moderationCard.getBounds().x && getBounds().x + getBounds().width < moderationCard.getBounds().x + moderationCard.getBounds().width)) {
                if (getBounds().y < moderationCard.getY() + moderationCard.getBounds().height + MAGNETRANGE && getBounds().y > moderationCard.getY() + moderationCard.getBounds().height - MAGNETRANGE) {
                    returnModerationCard = moderationCard;
                    snapDirection = SOUTH;
                } else if (getBounds().y < moderationCard.getY() + moderationCard.getBounds().height + 2 * MAGNETRANGE && getBounds().y > moderationCard.getY() + moderationCard.getBounds().height + MAGNETRANGE) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = true;
                    snapDirection = SOUTH;
                } else if (getBounds().y + moderationCard.getBounds().height - MAGNETRANGE < moderationCard.getY() && getBounds().y + moderationCard.getBounds().height + MAGNETRANGE > moderationCard.getY()) {
                    returnModerationCard = moderationCard;
                    snapDirection = NORTH;
                } else if (getBounds().y + moderationCard.getBounds().height + MAGNETRANGE < moderationCard.getY() && getBounds().y + moderationCard.getBounds().height + 2 * MAGNETRANGE > moderationCard.getY()) {
                    returnModerationCard = moderationCard;
                    distancedMagnet = true;
                    snapDirection = NORTH;
                }
            }
        }
        return returnModerationCard;
    }

    public void setModerationCardList(ArrayList<ModerationCard> moderationCardList) {
        this.moderationCardList = moderationCardList;
    }

    private boolean snapTo(ModerationCard cardMagnetic) {
        if (cardMagnetic == null) {
            magneticCard = null;
            return false;
        }
        magneticCard = cardMagnetic;
        return true;
    }

    public SnapDirection getSnapDirection() {
        return snapDirection;
    }

    public boolean isDistancedMagnet() {
        return distancedMagnet;
    }

    public int getMAGNETRANGE() {
        return MAGNETRANGE;
    }
}

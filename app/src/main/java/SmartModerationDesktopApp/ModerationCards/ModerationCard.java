package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.MainWindow;
import static SmartModerationDesktopApp.ModerationCards.SnapDirection.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ModerationCard extends javax.swing.JPanel {

    private final int MAGNETRANGE = 30;
    private int x;
    private int y;
    private long cardId;
    private long meetingId;
    private boolean distancedMagnet = false;
    private String content;
    private String backgroundColor;
    private Point location;
    private MouseEvent pressed;
    private MainWindow mainWindow;
    private ModerationCard magneticCard;
    private SnapDirection snapDirection;
    private ArrayList<ModerationCard> moderationCardList;
    private ArrayList<ModerationCard> dockedModerationCardList;

    //TODO: create Object for input parameters
    public ModerationCard(long cardId, long meetingId, String content, String backgroundColor) {
        initComponents();
        this.cardId = cardId;
        this.meetingId = meetingId;
        this.content = content;
        this.backgroundColor = backgroundColor;
        setBackground(Color.decode(backgroundColor));
        jScrollPane.getViewport().setOpaque(false);
        centerText();
        moderationCardTextBody.setText(content);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        moderationCardTextBody = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(153, 153, 255));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jScrollPane.setBorder(null);
        jScrollPane.setOpaque(false);

        moderationCardTextBody.setEditable(false);
        moderationCardTextBody.setBorder(null);
        moderationCardTextBody.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        moderationCardTextBody.setEnabled(false);
        moderationCardTextBody.setFocusable(false);
        moderationCardTextBody.setOpaque(false);
        moderationCardTextBody.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                moderationCardTextBodyMouseDragged(evt);
            }
        });
        moderationCardTextBody.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moderationCardTextBodyMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                moderationCardTextBodyMouseReleased(evt);
            }
        });
        jScrollPane.setViewportView(moderationCardTextBody);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        cardClicked(evt);
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        cardDragged(evt);
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        cardReleased(evt);
    }//GEN-LAST:event_formMouseReleased

    private void moderationCardTextBodyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moderationCardTextBodyMouseClicked
        cardClicked(evt);
    }//GEN-LAST:event_moderationCardTextBodyMouseClicked

    private void moderationCardTextBodyMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moderationCardTextBodyMouseDragged
        cardDragged(evt);
    }//GEN-LAST:event_moderationCardTextBodyMouseDragged

    private void moderationCardTextBodyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moderationCardTextBodyMouseReleased
        cardReleased(evt);
    }//GEN-LAST:event_moderationCardTextBodyMouseReleased

    private void cardClicked(java.awt.event.MouseEvent evt) {
        pressed = evt;
        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
    }

    private void cardDragged(java.awt.event.MouseEvent evt) {
        location = getLocation(location);
        if (pressed == null) {
            pressed = evt;
        }
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

        setLocation(x, y);
    }

    private void cardReleased(java.awt.event.MouseEvent evt) {
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
            setLocation(x, y);
        }
        mainWindow.clearBackground();
    }
    
    public long getCardId() {
        return cardId;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getColor() {
        return backgroundColor;
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
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextPane moderationCardTextBody;
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

    private void centerText() {
        StyledDocument doc = moderationCardTextBody.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }
}

package SmartModerationDesktopApp.ModerationCards;

import static SmartModerationDesktopApp.ModerationCards.SnapDirections.*;
import SmartModerationDesktopApp.MainWindow;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ModerationCard extends javax.swing.JPanel {

    private final int MAGNETRANGE = 30;
    private final long cardId;
    private final String content;
    private final String backgroundColor;
    private final SnapDirectionChecker snapDirectionChecker;
    private int x;
    private int y;
    private boolean distancedMagnet = false;
    private Point location;
    private MouseEvent pressed;
    private MainWindow mainWindow;
    private ModerationCard magneticCard;
    private SnapDirections snapDirection;
    private ArrayList<ModerationCard> moderationCardList;

    public ModerationCard(long cardId, long meetingId, String content, String backgroundColor) {
        initComponents();
        this.x = 0;
        this.y = 0;
        this.cardId = cardId;
        this.backgroundColor = backgroundColor;
        this.content = content;
        snapDirectionChecker = new SnapDirectionChecker();
        setBackground(Color.decode(backgroundColor));
        createOneColorBackground();
        setCardContent(content);
        centerText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        moderationCardTextBody = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(153, 153, 255));
        setToolTipText("Click to drag card");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(200, 200));
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
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("Moderation Card");
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
        } else if (x > mainWindow.getWidth() - getPreferredSize().width) {
            x = mainWindow.getWidth() - getPreferredSize().width;
        }

        if (y < 0) {
            y = 0;
        } else if (y > mainWindow.getHeight() - getPreferredSize().height) {
            y = mainWindow.getHeight() - getBounds().height;
        }
        isCardMagnetic();
        if (snapTo(magneticCard)) {
            mainWindow.getLineDrawer().drawDottedLineBetween(this, magneticCard);
        } else {
            mainWindow.getLineDrawer().clearLine();
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
                    y = magneticCard.y - getBounds().height - distance;
                    break;
                case NORTH:
                    y = magneticCard.y + magneticCard.getBounds().height + distance;
                    break;
                default:
                    throw new AssertionError();
            }
            setLocation(x, y);
        }
        mainWindow.getLineDrawer().clearLine();
    }

    private void isCardMagnetic() {
        distancedMagnet = false;
        magneticCard = null;
        for (ModerationCard moderationCard : moderationCardList) {
            snapDirectionChecker.setSnapDirectionBetween(this, moderationCard);
        }
    }

    private boolean snapTo(ModerationCard cardMagnetic) {
        if (cardMagnetic == null) {
            magneticCard = null;
            return false;
        }
        magneticCard = cardMagnetic;
        return true;
    }

    private void centerText() {
        StyledDocument doc = moderationCardTextBody.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void createOneColorBackground() {
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setViewportBorder(null);
        moderationCardTextBody.setEditorKit(new MyEditorKit());
        moderationCardTextBody.setBackground(new Color(0, 0, 0, 0));
    }

    public long getCardId() {
        return cardId;
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

    public void setModerationCardList(ArrayList<ModerationCard> moderationCardList) {
        this.moderationCardList = moderationCardList;
    }

    public SnapDirections getSnapDirection() {
        return snapDirection;
    }

    public boolean isDistancedMagnet() {
        return distancedMagnet;
    }

    public int getMAGNETRANGE() {
        return MAGNETRANGE;
    }

    public void setMagneticCard(ModerationCard magneticCard) {
        this.magneticCard = magneticCard;
    }

    public ModerationCard getMagneticCard() {
        return magneticCard;
    }

    public void setDistancedMagnet(boolean distancedMagnet) {
        this.distancedMagnet = distancedMagnet;
    }

    public void setSnapDirection(SnapDirections snapDirection) {
        this.snapDirection = snapDirection;
    }

    public String getContent() {
        return content;
    }

    public void updateProperties(ModerationCard moderationCard) {
        setCardContent(moderationCard.getContent());        
        setBackground(moderationCard.getBackground());
    }

    private void setCardContent(String content) {     
        moderationCardTextBody.setText(content);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextPane moderationCardTextBody;
    // End of variables declaration//GEN-END:variables

    public JTextPane getModerationCardTextBody() {
        return moderationCardTextBody;
    }
}

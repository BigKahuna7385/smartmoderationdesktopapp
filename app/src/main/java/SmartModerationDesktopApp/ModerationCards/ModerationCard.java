package SmartModerationDesktopApp.ModerationCards;

import static SmartModerationDesktopApp.ModerationCards.SnapDirections.*;
import SmartModerationDesktopApp.MainWindow.MainWindow;
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
    private final ModerationCardData moderationCardData;
    private final SnapDirectionChecker snapDirectionChecker;
    private boolean distancedMagnet = false;
    private MouseEvent pressed;
    private ModerationCard magneticCard;
    private SnapDirections snapDirection;
    private ArrayList<ModerationCard> moderationCardList;

    public ModerationCard(ModerationCardData moderationCardData) {
        initComponents();
        setLocation(0, 0);
        snapDirectionChecker = new SnapDirectionChecker();
        this.moderationCardData = moderationCardData;
        drawModerationCardData();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        menuItemDelete = new javax.swing.JMenuItem();
        jScrollPane = new javax.swing.JScrollPane();
        moderationCardTextBody = new javax.swing.JTextPane();
        authorLabel = new javax.swing.JLabel();

        menuItemDelete.setBackground(new java.awt.Color(255, 0, 0));
        menuItemDelete.setForeground(new java.awt.Color(255, 0, 51));
        menuItemDelete.setText("delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenu.add(menuItemDelete);

        setBackground(new java.awt.Color(153, 153, 255));
        setToolTipText("Click to drag card");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setComponentPopupMenu(jPopupMenu);
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
        jScrollPane.setInheritsPopupMenu(true);
        jScrollPane.setOpaque(false);
        jScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPaneMousePressed(evt);
            }
        });

        moderationCardTextBody.setEditable(false);
        moderationCardTextBody.setBorder(null);
        moderationCardTextBody.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        moderationCardTextBody.setEnabled(false);
        moderationCardTextBody.setFocusable(false);
        moderationCardTextBody.setInheritsPopupMenu(true);
        moderationCardTextBody.setMargin(new java.awt.Insets(0, 0, 0, 0));
        moderationCardTextBody.setOpaque(false);
        moderationCardTextBody.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                moderationCardTextBodyMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                moderationCardTextBodyMouseReleased(evt);
            }
        });
        jScrollPane.setViewportView(moderationCardTextBody);

        authorLabel.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(authorLabel))
            .addComponent(jScrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(authorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
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

    private void moderationCardTextBodyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moderationCardTextBodyMouseReleased
        cardReleased(evt);
    }//GEN-LAST:event_moderationCardTextBodyMouseReleased

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        MainWindow.getInstance().sendDeleteModerationCard(moderationCardData.getCardId());
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void jScrollPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPaneMousePressed

    private void moderationCardTextBodyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moderationCardTextBodyMousePressed
        cardClicked(evt);
    }//GEN-LAST:event_moderationCardTextBodyMousePressed

    private void cardClicked(java.awt.event.MouseEvent evt) {
        pressed = evt;
        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        MainWindow.getInstance().getModerationCardsController().sortZOrder(this);
    }

    private void cardDragged(java.awt.event.MouseEvent evt) {
        Point location = getLocation();
        if (pressed == null) {
            pressed = evt;
        }
        int dX = evt.getX() - pressed.getX();
        int dY = evt.getY() - pressed.getY();
        int x = location.x + dX;
        int y = location.y + dY;

        if (x < 0) {
            x = 0;
        } else if (x > MainWindow.getInstance().getWidth() - getPreferredSize().width) {
            x = MainWindow.getInstance().getWidth() - getPreferredSize().width;
        }

        if (y < 0) {
            y = 0;
        } else if (y > MainWindow.getInstance().getHeight() - getPreferredSize().height) {
            y = MainWindow.getInstance().getHeight() - getBounds().height;
        }
        isCardMagnetic();
        if (snapTo(magneticCard)) {
            MainWindow.getInstance().getLineDrawer().drawDottedLineBetween(this, magneticCard);
        } else {
            MainWindow.getInstance().getLineDrawer().clearLine();
        }
        setLocation(x, y);
    }

    private void cardReleased(java.awt.event.MouseEvent evt) {
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        int x = getLocation().x;
        int y = getLocation().y;
        if (magneticCard != null) {
            int distance = isDistancedMagnet() ? MAGNETRANGE : 0;
            switch (snapDirection) {
                case WEST:
                    x = magneticCard.getLocation().x + magneticCard.getBounds().width + distance;
                    break;
                case EAST:
                    x = magneticCard.getLocation().x - getBounds().width - distance;
                    break;
                case SOUTH:
                    y = magneticCard.getLocation().y - getBounds().height - distance;
                    break;
                case NORTH:
                    y = magneticCard.getLocation().y + magneticCard.getBounds().height + distance;
                    break;
                default:
                    throw new AssertionError();
            }
            setLocation(x, y);
        }
        MainWindow.getInstance().getLineDrawer().clearLine();
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
        return moderationCardData.getCardId();
    }

    public String getColor() {
        return moderationCardData.getBackgroundColor();
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
        return moderationCardData.getContent();
    }

    public void updateProperties(ModerationCard moderationCard) {
        setCardContent(moderationCard.getModerationCardData());
        setBackground(moderationCard.getBackground());
    }

    private void setCardContent(ModerationCardData moderationCardData) {
        authorLabel.setText(moderationCardData.getAuthor().substring(0, 2).toUpperCase());
        authorLabel.setToolTipText(moderationCardData.getAuthor());
        moderationCardTextBody.setText(moderationCardData.getContent());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel authorLabel;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JTextPane moderationCardTextBody;
    // End of variables declaration//GEN-END:variables

    public JTextPane getModerationCardTextBody() {
        return moderationCardTextBody;
    }

    public ModerationCardData getModerationCardData() {
        return moderationCardData;
    }

    private void drawModerationCardData() {
        setBackground(Color.decode(moderationCardData.getBackgroundColor()));
        moderationCardTextBody.setDisabledTextColor(Color.decode(moderationCardData.getFontColor()));
        createOneColorBackground();
        setCardContent(moderationCardData);
        centerText();
    }
}

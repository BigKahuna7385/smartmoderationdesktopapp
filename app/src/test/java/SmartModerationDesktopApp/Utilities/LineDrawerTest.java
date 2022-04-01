package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.MainWindow;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.ModerationCards.SnapDirectionChecker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class LineDrawerTest {
/*
    static MainWindow mainWindow;
    static ModerationCard moderationCardMoving;
    static ModerationCard moderationCardMagnetic;
    static LineDrawer lineDrawer;
    static SnapDirectionChecker snapDirectionChecker;
    static int width;
    static int height;
    static int yPosition;
    static int xPosition;
    static int MAGNETICDISTANCE;
    
    @BeforeAll
    public static void setUp() {
        mainWindow = new MainWindow();
        snapDirectionChecker = new SnapDirectionChecker();
        moderationCardMoving = new ModerationCard(1234567890l, 1234567890l, "This card is to the left", "#E91E63");
        moderationCardMagnetic = new ModerationCard(1234567890l, 1234567890l, "This card is in the middle", "#E91E63");
        MAGNETICDISTANCE = moderationCardMoving.getMAGNETRANGE();
        width = moderationCardMoving.getPreferredSize().width;
        height = moderationCardMoving.getPreferredSize().height;
        yPosition = 500;
        xPosition = 500;
    }

    @Test
    public void testDrawVerticalDottedLineBetween() {
        moderationCardMoving.setBounds(xPosition - width + 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        assertNotNull(mainWindow.getLineDrawer());
        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);
        mainWindow.getLineDrawer().drawDottedLineBetween(moderationCardMoving, moderationCardMagnetic);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().x == xPosition);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().y == 0);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().x == xPosition);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().y == mainWindow.getContentPane().getMaximumSize().height);
    }

    @Test
    public void testDrawVerticalDottedLineBetweenWithMagneticDistance() {
        moderationCardMoving.setBounds(xPosition - width - MAGNETICDISTANCE - 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        assertNotNull(mainWindow.getLineDrawer());
        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);
        mainWindow.getLineDrawer().drawDottedLineBetween(moderationCardMoving, moderationCardMagnetic);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().x == xPosition - MAGNETICDISTANCE);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().y == 0);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().x == xPosition - MAGNETICDISTANCE);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().y == mainWindow.getContentPane().getMaximumSize().height);
    }

    @Test
    public void testDrawHorizontalDottedLineBetween() {
        moderationCardMoving.setBounds(xPosition, yPosition - height - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        assertNotNull(mainWindow.getLineDrawer());
        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);
        mainWindow.getLineDrawer().drawDottedLineBetween(moderationCardMoving, moderationCardMagnetic);

        assertTrue(mainWindow.getLineDrawer().getStartPoint().x == 0);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().x == mainWindow.getContentPane().getMaximumSize().width);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().y == yPosition);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().y == yPosition);
    }

    @Test
    public void testDrawHorizontalDottedLineBetweenWithMagneticDistance() {
        moderationCardMoving.setBounds(xPosition, yPosition - height - MAGNETICDISTANCE - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        assertNotNull(mainWindow.getLineDrawer());
        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);
        mainWindow.getLineDrawer().drawDottedLineBetween(moderationCardMoving, moderationCardMagnetic);

        assertTrue(mainWindow.getLineDrawer().getStartPoint().x == 0);
        assertTrue(mainWindow.getLineDrawer().getStartPoint().y == yPosition - MAGNETICDISTANCE);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().x == mainWindow.getContentPane().getMaximumSize().width);
        assertTrue(mainWindow.getLineDrawer().getEndPoint().y == yPosition - MAGNETICDISTANCE);
    }
     */
}

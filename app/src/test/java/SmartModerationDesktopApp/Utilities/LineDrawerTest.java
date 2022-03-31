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

    static MainWindow mainWindow;
    static ModerationCard moderationCardMoving;
    static ModerationCard moderationCardMagnetic;
    static LineDrawer lineDrawer;
    static int width;
    static int height;
    static int yPosition;
    static int xPosition;
    static int MAGNETICDISTANCE;

    @BeforeAll
    public static void setUp() {
        mainWindow = new MainWindow();
        moderationCardMoving = new ModerationCard(1234567890l, 1234567890l, "This card is to the left", "#E91E63");
        moderationCardMagnetic = new ModerationCard(1234567890l, 1234567890l, "This card is in the middle", "#E91E63");
        MAGNETICDISTANCE = moderationCardMoving.getMAGNETRANGE();
        width = moderationCardMoving.getPreferredSize().width;
        height = moderationCardMoving.getPreferredSize().height;
        yPosition = 100;
        xPosition = 100;
    }

    @Test
    public void testDrawVerticalDottedLineBetween() {
        moderationCardMoving.setBounds(xPosition - width + 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        assertNotNull(mainWindow.getLineDrawer());
        //mainWindow.getLineDrawer().drawDottedLineBetween(moderationCardMoving, moderationCardMagnetic);
        //assertTrue(lineDrawer.getStartPoint().x == 0);

    }

}

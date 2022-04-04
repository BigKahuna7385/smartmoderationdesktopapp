package SmartModerationDesktopApp.ModerationCards;

import static SmartModerationDesktopApp.ModerationCards.SnapDirections.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class SnapDirectionCheckerTest {

    static SnapDirectionChecker snapDirectionChecker;
    static ModerationCard moderationCardMoving;
    static ModerationCard moderationCardMagnetic;
    static int width;
    static int height;
    static int yPosition;
    static int xPosition;
    static int MAGNETICDISTANCE;

    @BeforeAll
    public static void setUp() {
        snapDirectionChecker = new SnapDirectionChecker();
        moderationCardMoving = new ModerationCard(1234567890l, 1234567890l, "This card is to the left", "#E91E63", "#FFFFFF");
        moderationCardMagnetic = new ModerationCard(1234567890l, 1234567890l, "This card is in the middle", "#E91E63", "#FFFFFF");
        MAGNETICDISTANCE = moderationCardMoving.getMAGNETRANGE();
        width = moderationCardMoving.getPreferredSize().width;
        height = moderationCardMoving.getPreferredSize().height;
        yPosition = 100;
        xPosition = 100;
    }

    @Test
    public void testMovingCardLeftOfMagneticCardWhenInsideCard() {
        moderationCardMoving.setBounds(xPosition - width + 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        //assertTrue(isInsideHorizontally(moderationCardMoving, moderationCardMagnetic));
        assertNotNull(moderationCardMoving.getMagneticCard());
        assertFalse(moderationCardMoving.isDistancedMagnet());
        assertEquals(EAST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardLeftOfMagneticCard() {
        moderationCardMoving.setBounds(xPosition - width - 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertFalse(moderationCardMoving.isDistancedMagnet());
        assertEquals(EAST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardLeftOfMagneticCardAndIsDistanced() {
        moderationCardMoving.setBounds(xPosition - width - MAGNETICDISTANCE - 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertTrue(moderationCardMoving.isDistancedMagnet());
        assertEquals(EAST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardRightOfMagneticCardWhenInsideCard() {
        moderationCardMoving.setBounds(xPosition + width - 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertFalse(moderationCardMoving.isDistancedMagnet());
        assertEquals(WEST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardRightOfMagneticCard() {
        moderationCardMoving.setBounds(xPosition + width + 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertFalse(moderationCardMoving.isDistancedMagnet());
        assertEquals(WEST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardRightOfMagneticCardAndIsDistanced() {
        moderationCardMoving.setBounds(xPosition + width + MAGNETICDISTANCE + 5, yPosition, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        //assertTrue(isInsideHorizontally(moderationCardMoving, moderationCardMagnetic));
        assertNotNull(moderationCardMoving.getMagneticCard());
        assertTrue(moderationCardMoving.isDistancedMagnet());
        assertEquals(WEST, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardOnTopOfMagneticCardWhenInsideCard() {
        moderationCardMoving.setBounds(xPosition, yPosition - height - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertFalse(moderationCardMoving.isDistancedMagnet());
        assertEquals(SOUTH, moderationCardMoving.getSnapDirection());
    }

    @Test
    public void testMovingCardOnTopOfMagneticCard() {
        moderationCardMoving.setBounds(xPosition, yPosition - height - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertEquals(SOUTH, moderationCardMoving.getSnapDirection());
        assertFalse(moderationCardMoving.isDistancedMagnet());
    }

    @Test
    public void testMovingCardOnTopOfMagneticCardAndIsDistanced() {
        moderationCardMoving.setBounds(xPosition, yPosition - height - MAGNETICDISTANCE - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        //assertTrue(isInsideVertically(moderationCardMoving, moderationCardMagnetic));
        assertNotNull(moderationCardMoving.getMagneticCard());
        assertEquals(SOUTH, moderationCardMoving.getSnapDirection());
        assertTrue(moderationCardMoving.isDistancedMagnet());
    }

    @Test
    public void testMovingCardOnBottomOfMagneticCardWhenInsideCard() {
        moderationCardMoving.setBounds(xPosition, yPosition + height - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertEquals(NORTH, moderationCardMoving.getSnapDirection());
        assertFalse(moderationCardMoving.isDistancedMagnet());
    }

    @Test
    public void testMovingCardOnBottomOfMagneticCard() {
        moderationCardMoving.setBounds(xPosition, yPosition + height + MAGNETICDISTANCE - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        assertNotNull(moderationCardMoving.getMagneticCard());
        assertEquals(NORTH, moderationCardMoving.getSnapDirection());
        assertFalse(moderationCardMoving.isDistancedMagnet());
    }

    @Test
    public void testMovingCardOnBottomOfMagneticCardAndIsDistanced() {
        moderationCardMoving.setBounds(xPosition, yPosition + height + 2 * MAGNETICDISTANCE - 5, height, width);
        moderationCardMagnetic.setBounds(xPosition, yPosition, height, width);

        snapDirectionChecker.setSnapDirectionBetween(moderationCardMoving, moderationCardMagnetic);

        //assertTrue(isInsideVertically(moderationCardMoving, moderationCardMagnetic));
        assertNotNull(moderationCardMoving.getMagneticCard());
        assertEquals(NORTH, moderationCardMoving.getSnapDirection());
        assertTrue(moderationCardMoving.isDistancedMagnet());
    }

}

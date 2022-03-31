package SmartModerationDesktopApp.ModerationCards;

import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ModerationCardTest {

    @Test
    public void testUpdateProperties() {
        ModerationCard beforeModerationCard = new ModerationCard(1234567890l, 1234567890l, "Test content", "#E91E63");
        String contentAfter = "Test content after";
        String colorCodeAfter = "#8BC34A";
        ModerationCard afterModerationCard = new ModerationCard(1234567890l, 1234567890l, contentAfter, colorCodeAfter);
        beforeModerationCard.updateProperties(afterModerationCard);
        assertTrue(beforeModerationCard.getModerationCardTextBody().getText().equals(contentAfter));
        assertTrue(beforeModerationCard.getBackground().equals(Color.decode(colorCodeAfter)));
    }

}

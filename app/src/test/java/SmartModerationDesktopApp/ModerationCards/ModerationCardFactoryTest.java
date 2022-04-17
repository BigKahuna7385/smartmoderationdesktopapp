package SmartModerationDesktopApp.ModerationCards;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ModerationCardFactoryTest {

    private ModerationCardFactory moderationCardFactory;
    private ArrayList<ModerationCard> moderationCards;
    private File file;

    @BeforeEach
    public void setUp() throws IOException {
        moderationCards = new ArrayList<>();
        ModerationCard moderationCard1 = new ModerationCard(new ModerationCardData(2624659524793748349L, 1234567890l, "content", "#E91E63", "#FFFFFF", "Test_Author"));
        ModerationCard moderationCard2 = new ModerationCard(new ModerationCardData(2760337309490495595L, 1234567890l, "content", "#E91E64", "#FFFFFF", "Test_Author"));
        ModerationCard moderationCard3 = new ModerationCard(new ModerationCardData(3239753487134706863L, 1234567890l, "content", "#E91E65", "#FFFFFF", "Test_Author"));
        moderationCards.add(moderationCard1);
        moderationCards.add(moderationCard2);
        moderationCards.add(moderationCard3);
        moderationCardFactory = new ModerationCardFactory(moderationCards, 1234567890l);
        file = new File("./cache/1234567890.json");
        FileUtils.copyFile(new File("./src/test/resources/cardCacheTestSet.json"), file);
    }

    @Test
    public void testLoadModerationCardPositionsFromCache() throws IOException {

        moderationCardFactory.loadModerationCardPositionsFromCache();
        assertNotNull(moderationCardFactory.getModerationCards());
        assertEquals(moderationCards.size(), 3);
        assertEquals(moderationCards.get(0).getX(), 1238);
        assertEquals(moderationCards.get(1).getX(), 402);
        assertEquals(moderationCards.get(2).getX(), 135);
        assertEquals(moderationCards.get(0).getY(), 295);
        assertEquals(moderationCards.get(1).getY(), 561);
        assertEquals(moderationCards.get(2).getY(), 57);
    }

    @Test
    public void testSetFanout() {
        for (ModerationCard moderationCard : moderationCards) {
            moderationCardFactory.setFanout(moderationCard);
        }
        assertEquals(moderationCards.get(0).getX(), 0);
        assertEquals(moderationCards.get(0).getY(), 0);
        assertEquals(moderationCards.get(1).getX(), 15);
        assertEquals(moderationCards.get(1).getY(), 15);
        assertEquals(moderationCards.get(2).getX(), 30);
        assertEquals(moderationCards.get(2).getY(), 30);
    }

    @Test
    public void testSetFanoutIfCardPositionIsSet() {
        for (ModerationCard moderationCard : moderationCards) {
            moderationCard.setLocation(50, 0);
            moderationCardFactory.setFanout(moderationCard);
        }
        assertEquals(moderationCards.get(0).getX(), 50);
        assertEquals(moderationCards.get(0).getY(), 0);
        assertEquals(moderationCards.get(1).getX(), 50);
        assertEquals(moderationCards.get(1).getY(), 0);
        assertEquals(moderationCards.get(2).getX(), 50);
        assertEquals(moderationCards.get(2).getY(), 0);
    }
}

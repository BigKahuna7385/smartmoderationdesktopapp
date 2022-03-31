
package SmartModerationDesktopApp.ModerationCards;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class ModerationCardFactoryTest {
    
    private ModerationCardFactory moderationCardFactory;
    private ArrayList<ModerationCard> moderationCards;
    private File file;
    
    
    @BeforeEach
    public void setUp() throws IOException{
        moderationCards = new ArrayList<>();
        ModerationCard moderationCard1 = new ModerationCard(2624659524793748349L, 1234567890l, "content", "#E91E63");
        ModerationCard moderationCard2 = new ModerationCard(2760337309490495595L, 1234567890l, "content", "#E91E64");
        ModerationCard moderationCard3 = new ModerationCard(3239753487134706863L, 1234567890l, "content", "#E91E65");
        moderationCards.add(moderationCard1);
        moderationCards.add(moderationCard2);
        moderationCards.add(moderationCard3);
        moderationCardFactory = new ModerationCardFactory(moderationCards, 1234567890l);
        file = new File("./cache/1234567890.json");
        Files.copy(Paths.get("./src/test/resources/cardCacheTestSet.json"), Paths.get("./cache/1234567890.json"), REPLACE_EXISTING );
    }
    
    @AfterEach
    public void cleanUp(){
        
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
        
    }
    
}

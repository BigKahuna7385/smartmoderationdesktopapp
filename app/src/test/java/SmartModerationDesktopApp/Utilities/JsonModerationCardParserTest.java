
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Utilities.JsonModerationCardParser;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.json.simple.parser.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class JsonModerationCardParserTest {

    static JsonModerationCardParser jsonModerationCardParser;

    @BeforeAll
    public static void setUp() {
        jsonModerationCardParser = new JsonModerationCardParser();
    }

    @Test
    public void parseSingleModerationCardJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/SingleModerationCardTestSet.json")));
        String json = br.lines().collect(Collectors.joining());
        System.out.println("Test Json: " + json);
        assertNotNull(json);
        ModerationCard moderationCard = jsonModerationCardParser.parseSingleModerationCardJson(json);
        assertNotNull(moderationCard);
        assertEquals(moderationCard.getContent(), "logintest");
        assertEquals(moderationCard.getColor(), "#FFC107");
        assertEquals(moderationCard.getCardId(), 8716934582346075052L);
    }

    @Test
    public void parseModerationCardsJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/ModerationCardsTestSet.json")));
        ArrayList<ModerationCard> moderationCards = jsonModerationCardParser.parseModerationCardJson(br.lines().collect(Collectors.joining()));
        assertNotNull(moderationCards);
        assertTrue(moderationCards.size() == 3);
        assertEquals(moderationCards.get(0).getContent(), "testetst");
        assertEquals(moderationCards.get(0).getColor(), "#673AB7");
        assertEquals(moderationCards.get(0).getCardId(), 1164845262799520113L);

        assertEquals(moderationCards.get(1).getContent(), "test");
        assertEquals(moderationCards.get(1).getColor(), "#673AB7");
        assertEquals(moderationCards.get(1).getCardId(), 6599522553032296570L);

        assertEquals(moderationCards.get(2).getContent(), "logintest");
        assertEquals(moderationCards.get(2).getColor(), "#FFC107");
        assertEquals(moderationCards.get(2).getCardId(), 8716934582346075052L);

    }

    @Test
    public void parseCacheJsonTest() throws FileNotFoundException {
        HashMap<Long, Point> cardCache = jsonModerationCardParser.parseCacheJson(new File("./src/test/resources/cardCacheTestSet.json"));
        assertNotNull(cardCache);
        assertEquals(cardCache.get(2624659524793748349l), new Point(1238, 295));
        assertEquals(cardCache.get(2760337309490495595l), new Point(402, 561));
        assertEquals(cardCache.get(3239753487134706863l), new Point(135, 57));
    }
}

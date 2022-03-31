
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Server.LoginInformation;
import SmartModerationDesktopApp.Utilities.JsonReader;
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

public class JsonReaderTest {

    static JsonReader jsonReader;

    @BeforeAll
    public static void setUp() {
        jsonReader = new JsonReader();
    }

    @Test
    public void parseSingleModerationCardJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/SingleModerationCardTestSet.json")));
        ModerationCard moderationCard = jsonReader.parseSingleModerationCardJson(br.lines().collect(Collectors.joining()));
        assertNotNull(moderationCard);
        assertEquals(moderationCard.getContent(), "Lawand ist doof");
        assertEquals(moderationCard.getColor(), "#E91E63");
        assertEquals(moderationCard.getCardId(), 2624659524793748349l);
    }

    @Test
    public void parseModerationCardsJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/ModerationCardsTestSet.json")));
        ArrayList<ModerationCard> moderationCards = jsonReader.parseModerationCardJson(br.lines().collect(Collectors.joining()));
        assertNotNull(moderationCards);
        assertTrue(moderationCards.size() == 3);
        assertEquals(moderationCards.get(0).getContent(), "Lawand ist doof");
        assertEquals(moderationCards.get(0).getColor(), "#E91E63");
        assertEquals(moderationCards.get(0).getCardId(), 2624659524793748349l);

        assertEquals(moderationCards.get(1).getContent(), "Daniel ist doof");
        assertEquals(moderationCards.get(1).getColor(), "#673AB7");
        assertEquals(moderationCards.get(1).getCardId(), 2760337309490495595l);

        assertEquals(moderationCards.get(2).getContent(), "Daniel ist doch nicht doof");
        assertEquals(moderationCards.get(2).getColor(), "#8BC34A");
        assertEquals(moderationCards.get(2).getCardId(), 3239753487134706863l);
    }

    @Test
    public void parseLoginJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/LoginTest.json")));
        LoginInformation loginInformation = jsonReader.readLoginInformationJson(br.lines().collect(Collectors.joining()));
        assertNotNull(loginInformation);
        assertEquals(loginInformation.getAndroidIpAddress(), "127.0.0.1");
        assertEquals(loginInformation.getMeetingId(), 3570151905752727837l);
        assertEquals(loginInformation.getAndroidPort(), "8000");
    }

    @Test
    public void parseCacheJsonTest() throws FileNotFoundException {
        HashMap<Long, Point> cardCache = jsonReader.parseCacheJson(new File("./src/test/resources/cardCacheTestSet.json"));
        assertNotNull(cardCache);
        assertEquals(cardCache.get(2624659524793748349l), new Point(1238, 295));
        assertEquals(cardCache.get(2760337309490495595l), new Point(402, 561));
        assertEquals(cardCache.get(3239753487134706863l), new Point(135, 57));
    }
}
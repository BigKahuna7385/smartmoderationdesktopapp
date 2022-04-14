
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.ModerationCards.ModerationCardData;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class JsonWriterTest {

    static JsonWriter jsonWriter;

    public JsonWriterTest() {
    }

    @BeforeAll
    public static void setUp() {
        jsonWriter = new JsonWriter();
    }

    @Test
    public void testSaveMeetingStatus() throws FileNotFoundException {
        long meetingId = 1234567890l;        
        long cardId = 9191919191919191l;        
        ArrayList<ModerationCard> moderationCards = new ArrayList<>();
        moderationCards.add(new ModerationCard(new ModerationCardData(cardId, meetingId, "Test Content", "#673AB7", "#FFFFFF")));
        jsonWriter.saveMeetingStatus(meetingId, moderationCards);
        BufferedReader br = new BufferedReader(new FileReader(new File("./cache/" + meetingId + ".json")));
        assertNotNull(br);
        String fileCache = br.lines().collect(Collectors.joining());
        assertTrue(fileCache.contains(String.valueOf(cardId)));
        assertTrue(fileCache.contains( "\"positionY\":0,\"positionX\":0"));       
    }
}


import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Utilities.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;
import org.json.simple.parser.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class JsonReaderTest {

    static JsonReader jsonReader;

    @BeforeAll
    public static void setUp() {
        jsonReader = new JsonReader();
    }
    
    @Test
    public void test() throws FileNotFoundException, ParseException {
        //jsonReader = new JsonReader();
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/SingleModerationCardTestSet.json")));
        ModerationCard moderationCard = jsonReader.parseSingleModerationCardJson(br.lines().collect(Collectors.joining()));
        assertNotNull(moderationCard);
        assertEquals(moderationCard.getContent(), "Lawand ist doof");
    }
}

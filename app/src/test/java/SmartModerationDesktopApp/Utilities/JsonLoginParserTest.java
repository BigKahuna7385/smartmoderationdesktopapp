
import SmartModerationDesktopApp.Server.LoginInformation;
import SmartModerationDesktopApp.Utilities.JsonLoginParser;
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

public class JsonLoginParserTest {

    static JsonLoginParser jsonLoginParser;

    @BeforeAll
    public static void setUp() {
        jsonLoginParser = new JsonLoginParser();       
    }

    @Test
    public void parseLoginJsonTest() throws FileNotFoundException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(new File("./src/test/resources/LoginTest.json")));
        LoginInformation loginInformation = jsonLoginParser.readLoginInformationJson(br.lines().collect(Collectors.joining()));
        assertNotNull(loginInformation);
        assertEquals(loginInformation.getAndroidIpAddress(), "127.0.0.1");
        assertEquals(loginInformation.getMeetingId(), 3570151905752727837l);
        assertEquals(loginInformation.getAndroidPort(), 8000);
    }
}

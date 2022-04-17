package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.Server.LoginInformation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonLoginParser {    
    public LoginInformation readLoginInformationJson(String loginInformationJson) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(loginInformationJson);
        long port = (long) jsonObject.get("port");
        return new LoginInformation((long) jsonObject.get("meetingId"), (String) jsonObject.get("ipAddress"), (int) port);
    }
}
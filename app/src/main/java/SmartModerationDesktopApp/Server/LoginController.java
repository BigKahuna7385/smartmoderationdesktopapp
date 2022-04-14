package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.Utilities.JsonReader;
import org.json.simple.parser.ParseException;

public class LoginController {

    private LoginInformation loginInformation;
    private final JsonReader jsonReader;

    public LoginController() {
        jsonReader = new JsonReader();
    }

    public void readLoginInformation(String loginString) throws ParseException {
        loginInformation = jsonReader.readLoginInformationJson(loginString);
    }

    public LoginInformation getLoginInformation() {
        return loginInformation;
    }

    public long getMeetingId() {
        return loginInformation.getMeetingId();
    }

}

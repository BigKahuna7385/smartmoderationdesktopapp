package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.Utilities.JsonLoginParser;

import org.json.simple.parser.ParseException;

public class LoginController {

    private LoginInformation loginInformation;
    private final JsonLoginParser jsonLoginParser;

    public LoginController() {
        jsonLoginParser = new JsonLoginParser();
    }

    public void readLoginInformation(String loginString) throws ParseException {
        loginInformation = jsonLoginParser.readLoginInformationJson(loginString);
    }

    public LoginInformation getLoginInformation() {
        return loginInformation;
    }

    public long getMeetingId() {
        return loginInformation.getMeetingId();
    }

}

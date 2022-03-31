package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.Server.Server;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriter {

    public void saveMeetingStatus(long meetingID, ArrayList<ModerationCard> moderationCards) {

        JSONArray cardsArray = new JSONArray();
        for (ModerationCard card : moderationCards) {
            JSONObject cardJSON = new JSONObject();
            cardJSON.put("cardId", card.getCardId());
            JSONArray cardPositions = new JSONArray();
            JSONObject position = new JSONObject();
            position.put("positionX", card.getX());
            position.put("positionY", card.getY());
            cardPositions.add(position);
            cardJSON.put("positions", cardPositions);
            cardsArray.add(cardJSON);
        }

        File directory = new File("./cache");
        if (!directory.exists()) {
            directory.mkdir();
        }
        try ( FileWriter fileWriter = new FileWriter("./cache/" + meetingID + ".json")) {
            fileWriter.write(cardsArray.toJSONString());
            fileWriter.close();
        } catch (IOException ex) {
        }
    }

    public String getLoginInformationJson(Server server) {
        JSONObject loginInformation = new JSONObject();
        loginInformation.put("ipAddess", server.getIpAddressString());
        loginInformation.put("port", Server.getPORT());
        loginInformation.put("apiKey", server.getApiKey());
        return loginInformation.toJSONString();
    }
}
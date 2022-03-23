package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {

    public ArrayList<ModerationCard> parseModerationCardJson(String inputJson) {
        ArrayList<ModerationCard> moderationCardList = new ArrayList();
        
        if (inputJson == null) {
            return moderationCardList;
        }
        
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(inputJson);
            JSONArray meetingCards = (JSONArray) object;
            for (Object meetingCard : meetingCards) {
                JSONObject jsonObject = (JSONObject) meetingCard;
                long cardId = (long) jsonObject.get("cardId");
                String content = (String) jsonObject.get("content");
                String color = (String) jsonObject.get("color");
                long meetingId = (long) jsonObject.get("meetingId");
                moderationCardList.add(new ModerationCard(cardId, meetingId, content, color));
            }
        } catch (ParseException e) {
        }
        return moderationCardList;
    }
}

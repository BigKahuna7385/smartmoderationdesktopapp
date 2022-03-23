package SmartModerationDesktopApp.Utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {

    public void parseJson(String inputString) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(inputString);
            JSONArray meetingCards = (JSONArray) object;          
            for (Object meetingCard : meetingCards) {
                JSONObject jsonObject = (JSONObject) meetingCard;
                long cardId = (long) jsonObject.get("cardId");
                String content = (String) jsonObject.get("content");
                String color = (String) jsonObject.get("color");
                long meetingId = (long) jsonObject.get("meetingId");
                System.out.println("cardId: " + cardId);
                System.out.println("Content: " + content);
                System.out.println("Color: " + color);
                System.out.println("meetingId: " + meetingId);
            }
        } catch (ParseException e) {
        }
    }
}

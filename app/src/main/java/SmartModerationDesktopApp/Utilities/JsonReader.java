package SmartModerationDesktopApp.Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {

    public void parseJson() {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser
                    .parse(new FileReader("/home/daniel/Downloads/Telegram Desktop/ModerationCardsTestSet.json"));
            System.out.println("File read!");
            //convert Object to JSONObject

            JSONArray meetingCards = (JSONArray) object;
            //Reading the String
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
        } catch (FileNotFoundException fe) {
        } catch (Exception e) {
        }
    }

}

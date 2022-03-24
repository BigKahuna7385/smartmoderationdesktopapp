package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    
    //TODO: Functionality to load existing moderation cards based on meeting id

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
    
    public ArrayList<ModerationCard> parseCacheJson(File inputJson) throws FileNotFoundException {
        ArrayList<ModerationCard> moderationCardList = new ArrayList();
        
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(inputJson);
        try {
            Object object = jsonParser.parse(fileReader);
            JSONArray cachesModerationCards = (JSONArray) object;
            for (Object meetingCard : cachesModerationCards) {
                JSONObject jsonObject = (JSONObject) meetingCard;
                long cardId = (long) jsonObject.get("cardId");
                String content = (String) jsonObject.get("content");
                String color = (String) jsonObject.get("color");
                long meetingId = (long) jsonObject.get("meetingId");
                int positionX = (int) jsonObject.get("positionX");
                int positionY = (int) jsonObject.get("positionY");
                ModerationCard moderationCard = new ModerationCard(cardId, meetingId, content, color);
                moderationCard.setX(positionX);
                moderationCard.setX(positionY);
                moderationCardList.add(moderationCard);
            }
        } catch (ParseException | IOException e) {
        }
        return moderationCardList;
    }    
    
}

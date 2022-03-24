package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public HashMap<Long, Point> parseCacheJson(File inputJson) {
        HashMap<Long, Point> cachedModerationCardPositions = new HashMap<>();
        
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(inputJson);
            Object object = jsonParser.parse(fileReader);
            JSONArray cachedModerationCards = (JSONArray) object;
            for (Object moderationCard : cachedModerationCards) {
                JSONObject jsonMeetingCard = (JSONObject) moderationCard;
                long cardId = (long) jsonMeetingCard.get("cardId");
                JSONArray positions = (JSONArray) jsonMeetingCard.get("positions");
                for (Object point : positions) {
                    JSONObject jsonPosition = (JSONObject) point;
                    long positionX = (long) jsonPosition.get("positionX");
                    long positionY = (long) jsonPosition.get("positionY");
                    cachedModerationCardPositions.put(cardId, new Point((int)positionX, (int)positionY));
                }
            }
        } catch (ParseException | IOException ex) {
        }        return cachedModerationCardPositions;

        }    
    
}


package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriter {
    
    public void saveMeetingStatus(long meetingID, ArrayList<ModerationCard> moderationCards){
        
        JSONArray cardsArray = new JSONArray();
        for (ModerationCard card: moderationCards) {
            JSONObject cardJSON = new JSONObject();
            cardJSON.put("cardId", card.getCardId());
            cardJSON.put("content", card.getContent());
            cardJSON.put("color", card.getColor());
            cardJSON.put("meetingId", meetingID);
            cardJSON.put("positionX", card.getX());
            cardJSON.put("positionY", card.getY());
            cardsArray.add(cardJSON);
        }
        
        File directory = new File("./cache");
        if (!directory.exists()){
            directory.mkdir();
        }
        try (FileWriter fileWriter = new FileWriter("./cache/" + meetingID + ".json")) {
            fileWriter.write(cardsArray.toJSONString()); 
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

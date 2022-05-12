package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import SmartModerationDesktopApp.ModerationCards.ModerationCardData;
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

public class JsonModerationCardParser {

    public ArrayList<ModerationCard> parseModerationCardJson(String inputJson) {
        ArrayList<ModerationCard> moderationCardList = new ArrayList();
        if (inputJson == null) {
            return moderationCardList;
        }

        try {
            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(inputJson);
            JSONObject meetingCardJson = (JSONObject) object;
            long meetingId = (long) meetingCardJson.get("meetingId");
            JSONArray meetingCards = (JSONArray) meetingCardJson.get("moderationCards");
            for (Object meetingCard : meetingCards) {
                JSONObject jsonObject = (JSONObject) meetingCard;
                long cardId = (long) jsonObject.get("cardId");
                String content = (String) jsonObject.get("content");
                String backgroundColor = (String) jsonObject.get("backgroundColor");
                String fontColor = (String) jsonObject.get("fontColor");
                String author = (String) jsonObject.get("author");
                moderationCardList.add(new ModerationCard(new ModerationCardData(cardId, meetingId, content, backgroundColor, fontColor, author)));
            }
        } catch (ParseException e) {
        }
        return moderationCardList;
    }

    public ModerationCard parseSingleModerationCardJson(String inputJson) throws ParseException {
        System.out.println(inputJson);
        JSONParser jsonParser = new JSONParser();

        Object object = jsonParser.parse(inputJson);
        JSONObject meetingCard = (JSONObject) object;
        JSONObject jsonObject = (JSONObject) meetingCard;
        long cardId = (long) jsonObject.get("cardId");
        String content = (String) jsonObject.get("content");
        String backgroundColor = (String) jsonObject.get("backgroundColor");
        String fontColor = (String) jsonObject.get("fontColor");
        long meetingId = (long) jsonObject.get("meetingId");
        String author = (String) jsonObject.get("author");
        System.out.println("ModerationCardParsed: " + content + " | " + backgroundColor + " | " + fontColor + " | " + meetingId + " | " + author);
        return new ModerationCard(new ModerationCardData(cardId, meetingId, content, backgroundColor, fontColor, author));
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
                    cachedModerationCardPositions.put(cardId, new Point((int) positionX, (int) positionY));
                }
            }
        } catch (ParseException | IOException ex) {
        }
        return cachedModerationCardPositions;
    }
}

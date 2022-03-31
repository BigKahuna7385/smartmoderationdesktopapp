package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.Utilities.JsonReader;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ModerationCardFactory {

    private final int FANOUTDISTANCE = 15;
    private final JsonReader jsonReader;
    private final ArrayList<ModerationCard> moderationCards;
    private final long meetingId;
    private int moderationCardOffset;

    public ModerationCardFactory(ArrayList<ModerationCard> moderationCards, long meetingId) {
        this.jsonReader = new JsonReader();
        this.moderationCards = moderationCards;
        this.meetingId = meetingId;
        moderationCardOffset = 0;
    }

    public void loadModerationCardPositionsFromCache() {
        File moderationCardCacheFile = new File("./cache/" + meetingId + ".json");
        final boolean cacheExists = moderationCardCacheFile.exists();

        if (cacheExists) {
            HashMap<Long, Point> cachedModerationCardPositions = jsonReader.parseCacheJson(moderationCardCacheFile);
            moderationCards.forEach((moderationCard) -> {
                if (cachedModerationCardPositions.containsKey(moderationCard.getCardId())) {
                    Point point = cachedModerationCardPositions.get(moderationCard.getCardId());
                    moderationCard.setX(point.x);
                    moderationCard.setY(point.y);
                }
            });
        }
    }
    
    public void setFanout(ModerationCard moderationCard){
        if (moderationCard.getX() == 0 && moderationCard.getY() == 0) {
                moderationCard.setX(moderationCardOffset);
                moderationCard.setY(moderationCardOffset);
                moderationCardOffset += FANOUTDISTANCE;
            }
            moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
            moderationCard.setModerationCardList(moderationCards);
    }
    
    public ArrayList<ModerationCard> getModerationCards(){
        return moderationCards;
    }
}
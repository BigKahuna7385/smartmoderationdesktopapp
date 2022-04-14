package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.Utilities.JsonModerationCardParser;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ModerationCardFactory {

    private final int FANOUTDISTANCE = 15;
    private final JsonModerationCardParser jsonModerationCardParser;
    private final ArrayList<ModerationCard> moderationCards;
    private final long meetingId;
    private int moderationCardOffset;

    public ModerationCardFactory(ArrayList<ModerationCard> moderationCards, long meetingId) {
        this.moderationCards = moderationCards;
        this.meetingId = meetingId;
        jsonModerationCardParser = new JsonModerationCardParser();
        moderationCardOffset = 0;
    }

    public void loadModerationCardPositionsFromCache() {
        File moderationCardCacheFile = new File("./cache/" + meetingId + ".json");
        final boolean cacheExists = moderationCardCacheFile.exists();

        if (cacheExists) {
            HashMap<Long, Point> cachedModerationCardPositions = jsonModerationCardParser.parseCacheJson(moderationCardCacheFile);
            moderationCards.forEach((moderationCard) -> {
                if (cachedModerationCardPositions.containsKey(moderationCard.getCardId())) {
                    Point point = cachedModerationCardPositions.get(moderationCard.getCardId());
                    moderationCard.setLocation(point);
                }
            });
        }
    }

    public void setFanout(ModerationCard moderationCard) {
        if (moderationCard.getX() == 0 && moderationCard.getY() == 0) {
            moderationCard.setLocation(moderationCardOffset, moderationCardOffset);
            moderationCardOffset += FANOUTDISTANCE;
        }
        moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
        moderationCard.setModerationCardList(moderationCards);
    }

    public ArrayList<ModerationCard> getModerationCards() {
        return moderationCards;
    }
}

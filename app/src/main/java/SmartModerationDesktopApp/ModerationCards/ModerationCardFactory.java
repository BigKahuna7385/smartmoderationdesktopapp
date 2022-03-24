package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.MainWindow;
import SmartModerationDesktopApp.Utilities.JsonReader;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ModerationCardFactory {

    private final long meetingId;
    private final JsonReader jsonReader;
    private final ArrayList<ModerationCard> moderationCards;
    private final MainWindow mainWindowFrame;
    
    
    public ModerationCardFactory(MainWindow mainWindow){
        this.meetingId = mainWindow.getMeetingId();
        this.jsonReader = new JsonReader();
        this.moderationCards = mainWindow.getModerationCards();
        this.mainWindowFrame = mainWindow;
    }
    
    
    public void placeModerationCards(){
        File moderationCardCacheFile = new File("./cache/" + meetingId + ".json");
        final boolean cacheExists = moderationCardCacheFile.exists();
        
        if(cacheExists){
            HashMap<Long, Point> cachedModerationCardPositions = jsonReader.parseCacheJson(moderationCardCacheFile);
            moderationCards.forEach((moderationCard) ->{
                if(cachedModerationCardPositions.containsKey(moderationCard.getCardId())){
                    Point point = cachedModerationCardPositions.get(moderationCard.getCardId());
                    moderationCard.setX(point.x);
                    moderationCard.setY(point.y);
                }
            });
        }
        
        int moderationCardOffset = 0;
        
        for(ModerationCard moderationCard : moderationCards) {
            moderationCard.setMainWindow(mainWindowFrame);
            mainWindowFrame.getContentPane().add(moderationCard);
            if(moderationCard.getX() == 0 && moderationCard.getY() == 0){
                moderationCard.setX(moderationCardOffset);
                moderationCard.setY(moderationCardOffset);
                moderationCardOffset += 10;
            }
            moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
            moderationCard.setModerationCardList(moderationCards);
        };
    }
    
    
}

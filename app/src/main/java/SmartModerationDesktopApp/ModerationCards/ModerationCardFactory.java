package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.MainWindow;
import SmartModerationDesktopApp.Utilities.JsonReader;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ModerationCardFactory {

    private final int FANOUTDISTANCE = 15;
    private final JsonReader jsonReader;
    private final ArrayList<ModerationCard> moderationCards;
    private final MainWindow mainWindow;

    public ModerationCardFactory(MainWindow mainWindow) {
        this.jsonReader = new JsonReader();
        this.moderationCards = mainWindow.getModerationCards();
        this.mainWindow = mainWindow;
    }

    public void placeModerationCards() {
        File moderationCardCacheFile = new File("./cache/" + mainWindow.getMeetingId() + ".json");
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

        int moderationCardOffset = 0;

        for (ModerationCard moderationCard : moderationCards) {
            moderationCard.setMainWindow(mainWindow);
            mainWindow.getContentPane().add(moderationCard);
            if (moderationCard.getX() == 0 && moderationCard.getY() == 0) {
                moderationCard.setX(moderationCardOffset);
                moderationCard.setY(moderationCardOffset);
                moderationCardOffset += FANOUTDISTANCE;
            }
            moderationCard.setBounds(moderationCard.getX(), moderationCard.getY(), moderationCard.getPreferredSize().width, moderationCard.getPreferredSize().height);
            moderationCard.setModerationCardList(moderationCards);
        }
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}

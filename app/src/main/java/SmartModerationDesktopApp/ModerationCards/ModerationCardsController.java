package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.MainWindow.MainWindow;
import SmartModerationDesktopApp.Utilities.JsonModerationCardParser;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class ModerationCardsController {

    private final ArrayList<ModerationCard> moderationCards;

    private final JsonModerationCardParser jsonModerationCardParser;
    private final JsonWriter jsonWriter;
    private ModerationCardFactory moderationCardFactory;
    private boolean cardsInitialized;

    private long meetingId;

    public ModerationCardsController() {
        moderationCards = new ArrayList<>();
        jsonModerationCardParser = new JsonModerationCardParser();
        jsonWriter = new JsonWriter();
        cardsInitialized = false;
        startCachingService();
    }

    public ArrayList<ModerationCard> getModerationCards() {
        return moderationCards;
    }

    public void initializeModerationCards(String moderationCardsString) {
        ArrayList<ModerationCard> inputCards = jsonModerationCardParser.parseModerationCardJson(moderationCardsString);
        moderationCards.addAll(inputCards);
        moderationCardFactory = new ModerationCardFactory(moderationCards, meetingId);
        moderationCardFactory.loadModerationCardPositionsFromCache();
        for (ModerationCard moderationCard : moderationCards) {
            moderationCardFactory.setFanout(moderationCard);
            MainWindow.getInstance().getContentPane().add(moderationCard);
        }
        cardsInitialized = true;
    }

    public void setMeetingId(long meetingId) {
        this.meetingId = meetingId;
    }

    public void putModerationCard(String message) {
        try {
            System.out.println("Try to put new moderation card.");
            System.out.println("Message: " + message);
            ModerationCard moderationCard = jsonModerationCardParser.parseSingleModerationCardJson(message);
            moderationCards.add(moderationCard);
            MainWindow.getInstance().getContentPane().add(moderationCard);
            moderationCardFactory.setFanout(moderationCard);
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteModerationCard(long cardId) {
        System.out.println("Try to delete moderation card.");
        for (ModerationCard moderationCard : moderationCards) {
            if (moderationCard.getCardId() == cardId) {
                System.out.println("Found card to delete.");
                moderationCards.remove(moderationCard);
                MainWindow.getInstance().getContentPane().remove(moderationCard);
                break;
            }
        }
    }

    public void updateModerationCard(String message) {
        try {
            ModerationCard tempModerationCard = jsonModerationCardParser.parseSingleModerationCardJson(message);
            for (ModerationCard moderationCard : moderationCards) {
                if (moderationCard.getCardId() == tempModerationCard.getCardId()) {
                    moderationCard.updateProperties(tempModerationCard);
                    break;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveMeetingStatus() {
        jsonWriter.saveMeetingStatus(meetingId, moderationCards);
    }

    private void startCachingService() {
        Runnable chachingRunnable = () -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (cardsInitialized) {
                        saveMeetingStatus();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ModerationCardsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread chachingService = new Thread(chachingRunnable);
        chachingService.start();
    }
}

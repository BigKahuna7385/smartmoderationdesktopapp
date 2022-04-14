package SmartModerationDesktopApp.ModerationCards;

import SmartModerationDesktopApp.MainWindow.MainWindow;
import SmartModerationDesktopApp.Utilities.JsonReader;
import SmartModerationDesktopApp.Utilities.JsonWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class ModerationCardsController {

    private final ArrayList<ModerationCard> moderationCards;


    private ModerationCardFactory moderationCardFactory;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;

    private long meetingId;

    public ModerationCardsController() {
        moderationCards = new ArrayList<>();
        jsonReader = new JsonReader();
        jsonWriter = new JsonWriter();       
    }

    public ArrayList<ModerationCard> getModerationCards() {
        return moderationCards;
    }

    public void initializeModerationCards(String moderationCardsString) {
        ArrayList<ModerationCard> inputCards = jsonReader.parseModerationCardJson(moderationCardsString);
        moderationCards.addAll(inputCards);
        moderationCardFactory = new ModerationCardFactory(moderationCards, meetingId);
        moderationCardFactory.loadModerationCardPositionsFromCache();
        for (ModerationCard moderationCard : moderationCards) {
            moderationCardFactory.setFanout(moderationCard);
            MainWindow.getInstance().getContentPane().add(moderationCard);
        }
    }

    public void setMeetingId(long meetingId) {
        this.meetingId = meetingId;
    }

    public void receivePutModerationCard(String message) {
        try {
            System.out.println("Try to put new moderation card.");
            System.out.println("Message: " + message);
            ModerationCard moderationCard = jsonReader.parseSingleModerationCardJson(message);     
            moderationCards.add(moderationCard);
            MainWindow.getInstance().getContentPane().add(moderationCard);
            moderationCardFactory.setFanout(moderationCard);
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveDeleteModerationCard(long cardId) {
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

    public void receiveUpdateModerationCard(String message) {
        try {
            ModerationCard tempModerationCard = jsonReader.parseSingleModerationCardJson(message);
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

}

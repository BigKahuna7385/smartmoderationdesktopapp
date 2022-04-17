package SmartModerationDesktopApp.Observer;

public interface ClientObservable {

    void initObserver(ClientObserver observer);

    void getModerationCardsJsonString(String moderationCardJson);
    
}

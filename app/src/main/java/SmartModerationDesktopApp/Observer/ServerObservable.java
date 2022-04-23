package SmartModerationDesktopApp.Observer;

public interface ServerObservable {

    void initObserver(ServerObserver observer);

    void login(String message);

    void postModerationCard(String message);

    void deleteModerationCard(long cardId);

    void updateModerationCard(String message);
}
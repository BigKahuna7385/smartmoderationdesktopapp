package SmartModerationDesktopApp.Observer;

public interface ServerObservable {

    void initObserver(ServerObserver observer);

    void login(String message);

    void putModerationCard(String message);

    void deleteModerationCard(String message);

    void updateModerationCard(String message);
}

package SmartModerationDesktopApp.Observer;

public interface ServerObserver<T extends ServerObservable> {

    public void receiveLogin(String message);

    public void receivePutModerationCard(String message);

    public void receiveDeleteModerationCard(long cardId);

    public void receiveUpdateModerationCard(String message);

}
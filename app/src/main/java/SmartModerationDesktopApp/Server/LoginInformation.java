package SmartModerationDesktopApp.Server;

public class LoginInformation {

    private final long meetingId;
    private final String androidPort;
    private final String androidIpAddress;

    public LoginInformation(long meetingId, String androidIpAddress, String androidPort) {
        this.meetingId = meetingId;
        this.androidPort = androidPort;
        this.androidIpAddress = androidIpAddress;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public String getAndroidPort() {
        return androidPort;
    }

    public String getAndroidIpAddress() {
        return androidIpAddress;
    }
}

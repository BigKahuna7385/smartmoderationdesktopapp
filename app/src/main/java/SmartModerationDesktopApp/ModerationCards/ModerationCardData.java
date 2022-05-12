package SmartModerationDesktopApp.ModerationCards;

public class ModerationCardData {

    private final long cardId;
    private final long meetingId;
    private final String content;
    private final String backgroundColor;
    private final String fontColor;
    private final String author;

    public ModerationCardData(long cardId, long meetingId, String content, String backgroundColor, String fontColor, String author) {
        this.cardId = cardId;
        this.meetingId = meetingId;
        this.content = content;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.author = author;
    }

    public long getCardId() {
        return cardId;
    }

    public long getMeetingId() {
        return meetingId;
    }

    public String getContent() {
        return content;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getAuthor() {
        return author;
    }
}

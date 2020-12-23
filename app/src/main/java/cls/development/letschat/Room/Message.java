package cls.development.letschat.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Message")
public class Message {
    @PrimaryKey(autoGenerate = true)
    public long messageId;

    public long chatId;
    public String contentText;
    public Media mediaContent;
    public long sendDate;
    public long sender;
    public String senderAsString;
    public boolean canBeDownloaded;
    public boolean isDownloaded;


    public Message(long messageId, long chatId, String contentText, Media mediaContent, long sendDate, long sender, String senderAsString, boolean canBeDownloaded, boolean isDownloaded) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.contentText = contentText;
        this.mediaContent = mediaContent;
        this.sendDate = sendDate;
        this.sender = sender;
        this.senderAsString = senderAsString;
        this.canBeDownloaded = canBeDownloaded;
        this.isDownloaded = isDownloaded;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Media getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(Media mediaContent) {
        this.mediaContent = mediaContent;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public String getSenderAsString() {
        return senderAsString;
    }

    public void setSenderAsString(String senderAsString) {
        this.senderAsString = senderAsString;
    }

    public boolean isCanBeDownloaded() {
        return canBeDownloaded;
    }

    public void setCanBeDownloaded(boolean canBeDownloaded) {
        this.canBeDownloaded = canBeDownloaded;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}

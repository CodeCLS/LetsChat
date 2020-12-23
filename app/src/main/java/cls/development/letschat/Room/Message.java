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
}

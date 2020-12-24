package cls.development.letschat.Room;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.room.Entity;

import java.util.ArrayList;

public class Chat {
    private static final String TAG = "ChatModel";
    private boolean pinned;
    private boolean favorite;
    private int color;
    private long createdDate;
    private long instanceDate;
    private String lastMessageContent;
    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private String unreadMessagesCount;
    private Drawable extraImage;

    public Chat(boolean pinned, boolean favorite, int color, long createdDate, long instanceDate, ArrayList<Message> messageArrayList,String lastMessageContent,String unreadMessagesCount, Drawable extraImage) {
        this.pinned = pinned;
        this.favorite = favorite;
        this.color = color;
        this.createdDate = createdDate;
        this.instanceDate = instanceDate;
        this.messageArrayList = messageArrayList;
        this.lastMessageContent = lastMessageContent;
        this.unreadMessagesCount = unreadMessagesCount;
        this.extraImage = extraImage;
    }

    public Drawable getExtraImage() {
        return extraImage;
    }

    public void setExtraImage(Drawable extraImage) {
        this.extraImage = extraImage;
    }

    public String getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(String unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getInstanceDate() {
        return instanceDate;
    }

    public void setInstanceDate(long instanceDate) {
        this.instanceDate = instanceDate;
    }

    public ArrayList<Message> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }
}

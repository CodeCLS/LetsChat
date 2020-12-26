package cls.development.letschat.Interfaces;

import android.net.Uri;

import java.util.ArrayList;

import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.Message;

public interface FirebaseClientCallback {
    public void chatChanged(Chat chat, ArrayList<Message> messages);
    public void allChats(ArrayList<Chat> chats);
    public void setDynamicLink(Uri uri);


}

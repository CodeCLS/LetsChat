package cls.development.letschat.Interfaces;

import java.util.ArrayList;

import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.Message;

public interface FirebaseClientCallback {
    public void chatChanged(Chat chat, ArrayList<Message> messages);
    public void allChats(ArrayList<Chat> chats);

}

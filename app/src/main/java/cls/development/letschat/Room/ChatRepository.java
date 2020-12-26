package cls.development.letschat.Room;

import cls.development.letschat.OnlineData.DataRepository;


public class ChatRepository {
    private static ChatRepository chatRepository;
    public synchronized static ChatRepository getInstance(){
        if(chatRepository == null)
            chatRepository= new ChatRepository();
        return chatRepository;
    }

    public void getRoomUID() {

    }
}

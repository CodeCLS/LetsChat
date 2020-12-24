package cls.development.letschat;

import androidx.lifecycle.MutableLiveData;

import cls.development.letschat.Room.Chat;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private MutableLiveData<Chat> selectedChat = new MutableLiveData<>();

    public Chat getSelectedChat() {
        return selectedChat.getValue();
    }

    public void setSelectedChat(Chat selectedChat) {
        this.selectedChat.setValue(selectedChat);
    }
}

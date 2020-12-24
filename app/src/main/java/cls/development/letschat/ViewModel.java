package cls.development.letschat;

import androidx.lifecycle.MutableLiveData;

import cls.development.letschat.Room.Chat;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private MutableLiveData<Chat> selectedChat = new MutableLiveData<>();
    private MutableLiveData<String> uId = new MutableLiveData<>();

    public ViewModel() {
        DataRepository dataRepository = DataRepository.getInstance();
    }

    public Chat getSelectedChat() {
        return selectedChat.getValue();
    }

    public void setSelectedChat(Chat selectedChat) {
        this.selectedChat.setValue(selectedChat);
    }


    public MutableLiveData<String> getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId.setValue(uId);
    }
}

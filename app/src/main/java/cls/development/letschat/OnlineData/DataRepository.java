package cls.development.letschat.OnlineData;

import android.app.Activity;
import android.net.Uri;

import com.google.firebase.auth.PhoneAuthProvider;

import cls.development.letschat.Room.Chat;

public class DataRepository{
    public FirebaseClient firebaseClient;
    public static String ID;
    public static DataRepository dataRepository;
    public synchronized static DataRepository getInstance(){
        if(dataRepository == null)
            dataRepository= new DataRepository();
        return dataRepository;
    }

    public DataRepository() {
        firebaseClient = FirebaseClient.getInstance();
    }

    public String getFirebaseUid(){
        return FirebaseClient.getInstance().getUid();
    }
    public void sendVerificationPhone(String number, Activity activity, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks){
        firebaseClient.sendVerificationCode(number, activity,callbacks);
    }


    public Chat createChatFromDeepLink(Uri uri) {
        Chat chat = new Chat();
        chat.setCreatedDate(System.currentTimeMillis());
        firebaseClient.startNewChat(chat,);

        return chat;
    }
}

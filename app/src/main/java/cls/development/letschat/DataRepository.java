package cls.development.letschat;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthProvider;

public class DataRepository {
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

}

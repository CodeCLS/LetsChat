package cls.development.letschat.OnlineData;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebaseClient {
    private static final String TAG = "FirebaseClient";
    public static FirebaseClient dataRepository;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private PhoneAuthOptions options;
    public synchronized static FirebaseClient getInstance(){
        if(dataRepository == null)
            dataRepository= new FirebaseClient();
        return dataRepository;
    }

    public FirebaseClient() {
        init();

    }

    private void init() {
        try {
            this.mAuth = FirebaseAuth.getInstance();
            this.firebaseUser = mAuth.getCurrentUser();
        }
        catch (Exception e){
            Log.e(TAG, "initError: "  + e);

        }
    }

    public String getUid() {
        return mAuth.getUid();

    }
    public boolean currentlyLoggedIn(){
        return firebaseUser != null;
    }
    public void sendVerificationCode(String number, Activity activity, PhoneAuthProvider.OnVerificationStateChangedCallbacks callback){
        options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(callback)  // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}

package cls.development.letschat;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebaseClient {
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
        this.firebaseUser = mAuth.getCurrentUser();
    }

    public void getUid() {

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

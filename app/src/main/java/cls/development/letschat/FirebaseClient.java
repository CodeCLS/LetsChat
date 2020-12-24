package cls.development.letschat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseClient {
    public static FirebaseClient dataRepository;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
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
    public boolean signUpUser(){
        work
    }

}

package cls.development.letschat.OnlineData;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.Interfaces.FirebaseClientCallback;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.Message;

public class FirebaseClient {
    private static final String TAG = "FirebaseClient";
    private static final String CONSTANT_STRING_FIREBASE_REALTIME_MESSAGES = "Messages";
    private static final int MAX_LENGTH_HASH = 10;
    public static FirebaseClient firebaseClient;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private PhoneAuthOptions options;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public synchronized static FirebaseClient getInstance(){
        if(firebaseClient == null)
            firebaseClient = new FirebaseClient();
        return firebaseClient;
    }

    public FirebaseClient() {
        init();

    }

    private void init() {
        try {
            this.mAuth = FirebaseAuth.getInstance();
            this.firebaseUser = mAuth.getCurrentUser();
            this.firebaseDatabase =  FirebaseDatabase.getInstance();
            this.databaseReference = firebaseDatabase.getReference();
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
        Log.d(TAG, "sendVerificationCode123: " + number);
        options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(callback)  // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    public boolean sendMessage(Message message, Chat chat, Context context){
        DatabaseReference chatReference = databaseReference.child(CONSTANT_STRING_FIREBASE_REALTIME_MESSAGES);
        Task<Void> databaseReference = chatReference.child(String.valueOf(chat.getId())).setValue(chat.getId());
        databaseReference.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addChatData(message,chat, chatReference);
            }
        });

    return false;



    }

    private void addChatData(Message message, Chat chat, DatabaseReference chatReference) {
        chatReference.child(String.valueOf(chat.getId())).child(String.valueOf(message.getMessageId())).setValue(chat.getId());
    }
    private void initGetAllMessagesFromFirebase(Chat chat,ViewModel viewModel){
        DatabaseReference chatReference = databaseReference.child(CONSTANT_STRING_FIREBASE_REALTIME_MESSAGES);
        chatReference.child(String.valueOf(chat.getId())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Message> array = new ArrayList<>();
                FirebaseClientCallback firebaseClientCallback = (FirebaseClientCallback) viewModel;
                firebaseClientCallback.chatChanged(chat,array);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void getAllChatsFromFirebase(){

    }


    public void startNewChat(Chat chat, String uid,String ownId, OnFailureListener onFailureListener,  OnSuccessListener onSuccessListener) {
        DatabaseReference chatReference = databaseReference.child(CONSTANT_STRING_FIREBASE_REALTIME_MESSAGES);
        chatReference.child(String.valueOf(chat.getId())).setValue(chat.getId());
        Task<Void> taskCreatingChat = chatReference.child(uid).child("Chats").child(chat.getId()).setValue(chat.getId());
        taskCreatingChat.addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener);
        chatReference.child(ownId).child("Chats").child(chat.getId()).setValue(chat.getId());






    }

}

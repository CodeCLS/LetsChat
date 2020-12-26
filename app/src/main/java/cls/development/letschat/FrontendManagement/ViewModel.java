package cls.development.letschat.FrontendManagement;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.Interfaces.FirebaseClientCallback;
import cls.development.letschat.Interfaces.LoginNumberCallback;
import cls.development.letschat.OnlineData.DataRepository;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.ChatRepository;
import cls.development.letschat.Room.Message;

public class ViewModel extends androidx.lifecycle.ViewModel implements FirebaseClientCallback {
    private static final String TAG = "ViewModel";
    private MutableLiveData<Chat> selectedChat = new MutableLiveData<>();
    private MutableLiveData<String> uId = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Message>> arrayMessagesChat = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Chat>> arrayAllChats = new MutableLiveData<>();
    private MutableLiveData<Uri> deepLink = new MutableLiveData<>();
    private MutableLiveData<Context> context = new MutableLiveData<>();
    private MutableLiveData<View> view = new MutableLiveData<>();
    private MutableLiveData<Boolean> isConnected = new MutableLiveData<>();




    private DataRepository dataRepository;
    private ChatRepository chatRepository;

    public ViewModel() {

    }
    public void initViewModelInActivity(Context context,AppCompatActivity appCompatActivity) throws Exception {
        this.context.setValue(context);
        startCheckUp(appCompatActivity);
        dataRepository = DataRepository.getInstance(context);
        chatRepository = ChatRepository.getInstance();

    }

    private void startCheckUp(AppCompatActivity owner) throws Exception {
        if (isConnected() && dataRepository.getFirebaseUid() == null){
            throw new Exception(getContext().getString(R.string.connected_but_no_id));

        }
        else if(isConnected()&&dataRepository.getFirebaseUid() != null){
            uId.setValue(dataRepository.getFirebaseUid());
            setIsConnected(true);
            doOnlineWork();

            return;
        }
        if (!isConnected() && dataRepository.getUIDShared() == null)
            throw new Exception(getContext().getString(R.string.not_connected_and_no_room_uid));
        else if(!isConnected() && dataRepository.getUIDShared() != null){
            uId.setValue(dataRepository.getUIDShared());
            setIsConnected(false);

            doOfflineWork(owner);
        }

    }

    private void doOfflineWork(AppCompatActivity owner) {
        isConnected.observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setIsConnected(aBoolean);
                Snackbar.make(owner.getCurrentFocus(),"",Snackbar.LENGTH_SHORT).show();

            }
        });

    }

    private void doOnlineWork() {

    }

    public Boolean getIsConnected() {
        return isConnected.getValue();
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected.setValue(isConnected);
    }

    public Context getContext() {
        return context.getValue();
    }

    public void setContext(Context context) {
        this.context.getValue();
    }

    public Uri getDeepLink() {
        return deepLink.getValue();
    }

    public void setDeepLink(Uri deepLink) {
        this.deepLink.setValue(deepLink);
    }

    public View getView() {
        return view.getValue();
    }

    public void setView(View view) {
        this.view.setValue(view);
    }

    public Chat getSelectedChat() {
        return selectedChat.getValue();
    }

    public void setSelectedChat(Chat selectedChat) {
        this.selectedChat.setValue(selectedChat);
    }


    public String getuId() {
        return uId.getValue();
    }

    public void setuId(String uId) {
        this.uId.setValue(uId);
    }
    public void signUpWithInstaAndNumber(String number, String insta, Activity activity, Context context, View view, LoginFragment loginFragment){
        httpCallInsta("https://instagram.com/" + insta + "/",context,view,activity,number,loginFragment);


    }
    public void httpCallInsta(String url, Context context, View view, Activity activity, String number, LoginFragment loginFragment) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        callBackVerification(number, activity,loginFragment,view);

                        // enjoy your response
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, R.string.wrong_insta , Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse2: " + error);

                // enjoy your error status
            }
        });

        queue.add(stringRequest);
    }

    private void callBackVerification(String number, Activity activity, LoginFragment loginFragment,View view) {
        dataRepository.sendVerificationPhone(number, activity, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                LoginNumberCallback loginNumberCallback = (LoginNumberCallback) loginFragment;
                Snackbar.make(Objects.requireNonNull(view),R.string.succesfully_verified,Snackbar.LENGTH_SHORT).show();
                loginNumberCallback.successfullyVerified(phoneAuthCredential);


                Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getProvider());

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: " + e);
                Snackbar.make(Objects.requireNonNull(view),(R.string.verification_error) + e.getLocalizedMessage(),Snackbar.LENGTH_SHORT).show();



            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent: " + token + " " + verificationId);
                LoginNumberCallback loginNumberCallback =(LoginNumberCallback) loginFragment;
                loginNumberCallback.openDialogPhoneVerification(token,verificationId);
                Snackbar.make(view,R.string.code_sent, Snackbar.LENGTH_SHORT).show();

            }

        });
    }
    public void enterCode(String code,String verificationId){
        Log.d(TAG, "enterCode:123123 ");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Log.d(TAG, "enterCode:123123 "+credential);
        Log.d(TAG, "enterCode:1323123 "+credential.getProvider());



    }


    @Override
    public void chatChanged(Chat chat, ArrayList<Message> messages) {
        arrayMessagesChat.setValue(messages);

    }

    @Override
    public void allChats(ArrayList<Chat> chats) {
        arrayAllChats.setValue(chats);

    }

    public void createChatFromDeepLink() {
        Uri uri = deepLink.getValue();
        dataRepository.createChatFromDeepLink(uri, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Snackbar.make(getView(),R.string.created_chat_from_deeplink,Snackbar.LENGTH_SHORT);

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        },getuId());
    }
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

}

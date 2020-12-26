package cls.development.letschat.FrontendManagement;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

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
    private static final String CONSTANT_INSTAGRAM_LINK = "https://instagram.com/";
    private final MutableLiveData<Chat> selectedChat = new MutableLiveData<>();
    private final MutableLiveData<String> uId = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Message>> arrayMessagesChat = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Chat>> arrayAllChats = new MutableLiveData<>();
    private final MutableLiveData<Uri> deepLink = new MutableLiveData<>();
    private final MutableLiveData<Uri> userDynamicLink = new MutableLiveData<>();

    private final MutableLiveData<Context> context = new MutableLiveData<>();
    private final MutableLiveData<View> view = new MutableLiveData<>();
    private final MutableLiveData<String> insta = new MutableLiveData<>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();




    private DataRepository dataRepository;
    private ChatRepository chatRepository;

    public ViewModel() {

    }

    public ArrayList<Chat> getArrayAllChats() {
        return arrayAllChats.getValue();
    }



    public void initViewModelInActivity(Context context) throws Exception {
        this.context.setValue(context);
        dataRepository = DataRepository.getInstance(context);
        chatRepository = ChatRepository.getInstance();
        setup();
        startCheckUp();

    }

    private void setup() {
        dataRepository.getAllChats(this);
        dataRepository.getUserLink(this);
        dataRepository.getUserInformation(this);
    }

    private void startCheckUp() throws Exception {

        if (isConnected()){
            if (dataRepository.getFirebaseUid() == null){
                Log.d(TAG, "startChec12312kUp213123: ");

                throw new Exception(getContext().getString(R.string.connected_but_no_id));


            }
            else{
                dataRepository.setIdShared(dataRepository.getFirebaseUid());

                Log.d(TAG, "12312startChec12312kUp213123: ");

                uId.setValue(dataRepository.getFirebaseUid());
                setIsConnected(true);
                return;

            }

        }
        else{
            Log.d(TAG, "1231123412startChec12312kUp213123: ");

            if (dataRepository.getUIDShared() == null){
                Log.d(TAG, "12312startCh4124ec12312kUp213123: ");

                throw new Exception(getContext().getString(R.string.not_connected_and_no_room_uid));


            }
            else{
                Log.d(TAG, "12312startChec12312kUp213151523: ");

                uId.setValue(dataRepository.getUIDShared());
                setIsConnected(false);

            }
        }


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
    public Uri getUserDynamicLink() {
        return userDynamicLink.getValue();
    }

    public void setUserDynamicLink(Uri deepLink) {
        this.userDynamicLink.setValue(deepLink);
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
        httpCallInsta(insta,CONSTANT_INSTAGRAM_LINK + insta + "/",context,view,activity,number,loginFragment);


    }
    public void httpCallInsta(String insta,String url, Context context, View view, Activity activity, String number, LoginFragment loginFragment) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        callBackVerification(insta,number, activity,loginFragment,view);

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

    private void callBackVerification(String insta,String number, Activity activity, LoginFragment loginFragment,View view) {
        dataRepository.sendVerificationPhone(number, activity, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                backendWorkNewUser(loginFragment,phoneAuthCredential,insta,number,view);



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

    private void backendWorkNewUser(LoginFragment loginFragment,PhoneAuthCredential phoneAuthCredential,String insta, String number,View view) {
        Log.d(TAG, "enter123Code:12321 ");


        dataRepository.createNewUser(phoneAuthCredential, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setuId(dataRepository.getFirebaseUid());
                dataRepository.setIdShared(dataRepository.getFirebaseUid());
                Log.d(TAG, "onComplete:123123123 ");
                dataRepository.createNewUserInRealtimeDB(insta, number, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete:12213123 ");
                        task.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "ent123erCode:12321 ");


                                ((LoginNumberCallback) loginFragment).successfullyVerified(phoneAuthCredential);
                                Snackbar.make(Objects.requireNonNull(view),R.string.succesfully_verified,Snackbar.LENGTH_SHORT).show();

                            }
                        });
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "ent12123erCode:12321 ");

                            }
                        });

                    }
                },context.getValue());
            }
        });



    }

    public void enterCode(LoginFragment loginFragment,String insta,String number,View view,String code,String verificationId){
        Log.d(TAG, "enterCode:12321 ");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        backendWorkNewUser(loginFragment,credential,insta,number,view);



    }


    @Override
    public void chatChanged(Chat chat, ArrayList<Message> messages) {
        arrayMessagesChat.setValue(messages);

    }

    @Override
    public void allChats(ArrayList<Chat> chats) {
        arrayAllChats.setValue(chats);
        Log.d(TAG, "allChats: "+arrayAllChats.getValue());

    }

    @Override
    public void setDynamicLink(Uri uri) {
        setUserDynamicLink(uri);
    }

    @Override
    public void setUserInformation(String s, String s1) {
        this.insta.setValue(s);
        this.phone.setValue(s1);
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

    public String getInsta() {
        return insta.getValue();
    }
    public String getPhone() {
        return phone.getValue();
    }
    public void setInsta(String insta) {
        this.insta.setValue(insta);
    }
    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }


    public MutableLiveData<String> getInstaObserver() {
        return insta;
    }
}

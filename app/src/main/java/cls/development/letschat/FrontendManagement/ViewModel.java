package cls.development.letschat.FrontendManagement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;

import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.LoginNumberCallback;
import cls.development.letschat.OnlineData.DataRepository;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.ChatRepository;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private static final String TAG = "ViewModel";
    private MutableLiveData<Chat> selectedChat = new MutableLiveData<>();
    private MutableLiveData<String> uId = new MutableLiveData<>();
    private DataRepository dataRepository;
    private ChatRepository chatRepository;

    public ViewModel() {
        dataRepository = DataRepository.getInstance();
        chatRepository = ChatRepository.getInstance();
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
}

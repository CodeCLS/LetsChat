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
    public void signUpWithInstaAndNumber(String number, String insta, Activity activity, Context context, View view){
        callBackVerification(number, activity);
        httpCallInsta("https://instagram.com/" + insta + "/",context,view);


    }
    public void httpCallInsta(String url, Context context, View view) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
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

    private void callBackVerification(String number, Activity activity) {
        dataRepository.sendVerificationPhone(number, activity, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getProvider());

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: " + e);


            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent: " + token + " " + verificationId);

            }

        });
    }
}

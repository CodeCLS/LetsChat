package cls.development.letschat.OnlineData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.Interfaces.LoginNumberCallback;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.ChatDao;
import cls.development.letschat.Room.ChatRepository;

public class DataRepository{
    private static final String CONSTANT_DEEPLINK_AUTHORITY = "letschat.cls-development.com";
    private static final String CONSTANT_PATH_NAME_DEEPLINK = "chat_invite";
    private static final String CONSTANT_ID_STRING_DEEPLINK = "uid";
    private static final String CONSTANT_SOCIAL_TITLE = "Join anonymous chats with your friends";
    private static final String TAG = "DataRepository";
    private static final String CONSTANT_DOMAIN_URI_PREFIX = "letschat.cls-development.com";
    private static final int MAX_LENGTH_HASH = 10;
    private static final String CONSTANT_SHARED_NAME = "LetsChatApp";
    private final Context context;
    public FirebaseClient firebaseClient;
    private SharedPreferences sharedPreferences;
    public static String ID;
    public static DataRepository dataRepository;
    public synchronized static DataRepository getInstance(Context context){
        if(dataRepository == null)
            dataRepository= new DataRepository(context);
        return dataRepository;
    }

    public DataRepository(Context context) {
        this.context=context;
        firebaseClient = FirebaseClient.getInstance();
        sharedPreferences = context.getSharedPreferences(CONSTANT_SHARED_NAME,Context.MODE_PRIVATE);

    }

    public String getFirebaseUid(){
        return FirebaseClient.getInstance().getUid();
    }
    public void sendVerificationPhone(String number, Activity activity, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks){
        firebaseClient.sendVerificationCode(number, activity,callbacks);
    }


    public void createChatFromDeepLink(Uri uri, OnSuccessListener onsuccess , OnFailureListener onfailure,String ownId) {
        Chat chat = new Chat();
        chat.setCreatedDate(System.currentTimeMillis());
        chat.setId(random());
        String uid = uri.getQueryParameter(CONSTANT_ID_STRING_DEEPLINK);
        firebaseClient.startNewChat(chat,uid,ownId,onfailure, onsuccess);
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH_HASH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void createDeepLink(String id, Context context){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(CONSTANT_DEEPLINK_AUTHORITY)
                .appendPath(CONSTANT_PATH_NAME_DEEPLINK)
                .appendQueryParameter(CONSTANT_ID_STRING_DEEPLINK, id);
        String myUrl = builder.build().toString();
        String query = "";
        try {
            query = URLEncoder.encode(String.format("&%1s=%2s", "uid", id), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "letschat.cls-development.com/" + query;
        Task<ShortDynamicLink> uri  = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(myUrl))
                .setDomainUriPrefix(CONSTANT_DOMAIN_URI_PREFIX)
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder()
                                .setMinimumVersion(11)
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(CONSTANT_SOCIAL_TITLE)
                                .setDescription(context.getString(R.string.welcome_text))
                                //.setImageUrl(Uri.parse("https://onestickers.com/img/main-logo.png"))
                                .build())
                .buildShortDynamicLink()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {



                    }
                })
                .addOnCompleteListener(task -> Log.d(TAG,"loading")).addOnFailureListener(e -> Log.d(TAG," cause you are a cool person: " + e)).addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        Log.d(TAG,"successs");
                        if (shortDynamicLink != null){
                            //Log.d(TAG,"linkk" + shortDynamicLink.getShortLink());
                            //Intent sendIntent = new Intent();
                            //sendIntent.setAction(Intent.ACTION_SEND);
                            //sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Jemand will dir einen coolen Kletter Post zeigen");
                            //sendIntent.setType("text/plain");
                            //sendIntent.putExtra("Post_ID",id);
                            //sendIntent.putExtra(Intent.EXTRA_TEXT,"Tritt der Kletter Community bei und zeige uns deine coolen Momente beim Klettern. Downloade jetzt." + shortDynamicLink.getShortLink());
                            //mContext.startActivity(sendIntent);


                        }

                    }
                });






    }

    public String getUIDShared() {
        return sharedPreferences.getString("uid" , null);


    }

    public void createNewUser(LoginFragment loginFragment, PhoneAuthCredential phoneAuthCredential) {
        firebaseClient.createNewUser(loginFragment,phoneAuthCredential);


    }
}

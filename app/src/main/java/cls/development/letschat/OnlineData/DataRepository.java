package cls.development.letschat.OnlineData;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;

public class DataRepository{
    private static final String CONSTANT_DEEPLINK_AUTHORITY = "https://letschat.cls-development.com/";
    private static final String CONSTANT_PATH_NAME_DEEPLINK = "chat_invite";
    private static final String CONSTANT_ID_STRING_DEEPLINK = "uid";
    private static final String CONSTANT_SOCIAL_TITLE = "Join anonymous chats with your friends";
    private static final String TAG = "DataRepository";
    private static final String CONSTANT_DOMAIN_URI_PREFIX = "letschat.cls-development.com";
    private static final int MAX_LENGTH_HASH = 10;
    private static final String CONSTANT_SHARED_NAME = "LetsChatApp";
    private static final String CONSTANT_SHARED_ID_NAME = "uid";
    private static final String CONSTANT_SAME_ID = "You can't chat with yourself";
    private static final String CONSTANT_SHARED_LINK_USER = "UserDynamicLink";
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
        if (uid.equals(getUid())){
            onfailure.onFailure(new Exception(CONSTANT_SAME_ID));
        }
        firebaseClient.startNewChat(chat,uid,ownId,onfailure, onsuccess);
    }

    public String getUid() {
        if (getFirebaseUid() != null){
            return getFirebaseUid();
        }
        else{
            return getUIDShared();
        }
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

    public void createDeepLinkToFirebaseClient(String insta, String number, OnCompleteListener<Void> onCompleteListener, String id, Context context){
        String s ="https://letschat.cls-development.com/linktoanonymouschatting";
        // Build the link with all required parameters
        String u ="letschat.cls-development.com/linktoanonymouschatting";
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(u)
                .path("ID")
                .appendQueryParameter("id", id);
        String myUrl = builder.build().toString();
        Task<ShortDynamicLink> uri  = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(myUrl))
                .setDomainUriPrefix(s)
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
                .addOnCompleteListener(task -> Log.d(TAG,"loading")).addOnFailureListener(e -> Log.d(TAG," error" + e)).addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        Log.d(TAG,"successs");
                        if (shortDynamicLink != null){
                            firebaseClient.addUserToRealTime(insta,number,onCompleteListener,shortDynamicLink.getShortLink());
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
        return sharedPreferences.getString(CONSTANT_SHARED_ID_NAME , null);


    }

    public void createNewUser(PhoneAuthCredential phoneAuthCredential, OnCompleteListener<AuthResult> onSuccessListener) {
        firebaseClient.createNewUser(phoneAuthCredential,onSuccessListener);


    }
    public void createNewUserInRealtimeDB(String insta, String number,OnCompleteListener<Void> onCompleteListener,Context context) {
        createDeepLinkToFirebaseClient(insta,number,onCompleteListener,getUid(),context);


    }

    public void setIdShared(String firebaseUid) {
        SharedPreferences.Editor sharedEdit = sharedPreferences.edit();
        sharedEdit.putString(CONSTANT_SHARED_ID_NAME, firebaseUid);
        sharedEdit.apply();

    }
    public void getUserLink(ViewModel model){
        firebaseClient.getUserLink(model);

    }

    public void getAllChats(ViewModel viewModel) {
        firebaseClient.getAllChats(viewModel);
    }
}

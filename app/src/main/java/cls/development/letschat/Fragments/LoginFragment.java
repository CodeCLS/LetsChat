package cls.development.letschat.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Fade;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import cls.development.letschat.CustomViews.NumberVerificationView;
import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.FrontendManagement.ViewModelFactory;
import cls.development.letschat.Interfaces.LoginNumberCallback;
import cls.development.letschat.R;

public class LoginFragment extends androidx.fragment.app.Fragment implements LoginNumberCallback {
    private static final String TAG = "LoginFragment" ;
    private static final long CONSTANT_ANIMATION_DIALOG_DURATION = 100;
    private LinearLayout editTextInstagramLinear;
    private EditText editTextInstagram;


    private EditText editTelephoneNumber;
    private LinearLayout telephoneNumberLinear;



    private LinearLayout continueButton;
    private TextView continueTextView;
    private long millis_start;
    private NumberVerificationView verificationContainer;
    private LinearLayout backgroundContainer;
    private View darkBackground;
    private ViewModel viewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated:12321 ");
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        viewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(ViewModel.class);


        init();
    }

    private void init() {
        editTextInstagram = getView().findViewById(R.id.edit_instagram_login);
        editTelephoneNumber = getView().findViewById(R.id.edit_phone_login);

        darkBackground = getView().findViewById(R.id.darkened_background_login);
        verificationContainer = getView().findViewById(R.id.verification_Container_Login);
        continueButton = getView().findViewById(R.id.login_linearlayout_btn_container);
        backgroundContainer = getView().findViewById(R.id.background_container_login);
        LoginFragment loginFragment = this;

        //onPress();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextInstagram.getText().toString().isEmpty() ||
                        editTextInstagram.getText().toString().equals("") ||
                        editTelephoneNumber.getText().toString().isEmpty() ||
                        editTelephoneNumber.getText().toString().length() < 6){
                    Snackbar.make(getView(),R.string.wrong_credentials,Snackbar.LENGTH_SHORT).show();

                }
                else{
                    viewModel.signUpWithInstaAndNumber("+4917641025403" , "calebseeling" ,getActivity(),getContext(),getView(),loginFragment );

                }



                //transitionToMain();

            }
        });


    }

    private void createDialogCode(PhoneAuthProvider.ForceResendingToken token, String verification) {
        verificationContainer.initFromFragment(this,token,verification);
        verificationContainer.setVisibility(View.VISIBLE);
        darkBackground.setVisibility(View.VISIBLE);

        verificationContainer.animate().alpha(1f).setDuration(CONSTANT_ANIMATION_DIALOG_DURATION);
        darkBackground.animate().alpha(0.9f).setDuration(CONSTANT_ANIMATION_DIALOG_DURATION);
        darkBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnimationToEnd();

            }
        });




    }
    private void dialogAnimationToEnd( ) {
        ViewPropertyAnimator viewPropertyAnimator = verificationContainer.animate().alpha(0f).setDuration(CONSTANT_ANIMATION_DIALOG_DURATION);
        darkBackground.animate().alpha(0f).setDuration(CONSTANT_ANIMATION_DIALOG_DURATION);
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                verificationContainer.setVisibility(View.GONE);
                darkBackground.setVisibility(View.GONE);
                viewPropertyAnimator.setListener(null);
                darkBackground.setOnClickListener(null);

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void onPress() {
        continueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        millis_start = System.currentTimeMillis();
                        Log.d(TAG, "onTouchstart: " + millis_start);
                        continueButton.setTranslationZ(0f);

                    case MotionEvent.ACTION_CANCEL:
                        continueButton.setTranslationZ(20f);
                        long millis_end = System.currentTimeMillis();
                        Log.d(TAG, "onTouchend: " + millis_end);

                        if(millis_end-millis_start < 200){
                            transitionToRespectiveFragment();
                        }
                    case MotionEvent.ACTION_UP:
                        long millis_end_2 = System.currentTimeMillis();

                        Log.d(TAG, "onTouchend:1 " + millis_end_2);





                }

                return true;
            }
        });
    }

    private void transitionToRespectiveFragment() {

        Log.d(TAG, "transitionToAllChats:2 ");
        AllChatsFragment allChatsFragment = new AllChatsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        allChatsFragment.setEnterTransition(new Fade());
        allChatsFragment.setExitTransition(new Fade());
        fragmentTransaction.replace(R.id.mainFrame,allChatsFragment);
        fragmentTransaction.commit();



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.login_fragment_view,container,false);
    }

    public void numberVerificationSubmit() {
        transitionToRespectiveFragment();

    }

    @Override
    public void openDialogPhoneVerification(PhoneAuthProvider.ForceResendingToken token, String verificationId) {
        createDialogCode(token,verificationId);

    }

    @Override
    public void successfullyVerified(PhoneAuthCredential credential) {
        numberVerificationSubmit();

    }

    public void checkCode(String code, String verification) {
        viewModel.enterCode(code,verification);
    }


}

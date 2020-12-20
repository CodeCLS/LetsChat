package cls.development.letschat.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cls.development.letschat.CustomViews.NumberVerificationView;
import cls.development.letschat.FragmentSwitcher;
import cls.development.letschat.R;

public class LoginFragment extends androidx.fragment.app.Fragment {
    private static final String TAG = "LoginFragment" ;
    private static final long CONSTANT_ANIMATION_DIALOG_DURATION = 100;
    private LinearLayout editTextInstagramLinear;
    private EditText editTextInstagram;


    private EditText telephoneNumber;
    private LinearLayout telephoneNumberLinear;



    private LinearLayout continueButton;
    private TextView continueTextView;
    private long millis_start;
    private NumberVerificationView verificationContainer;
    private LinearLayout backgroundContainer;
    private View darkBackground;
    private static FragmentSwitcher fragmentSwitcher;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();
    }

    private void init() {
        fragmentSwitcher = (FragmentSwitcher) getActivity();

        darkBackground = getView().findViewById(R.id.darkened_background_login);
        verificationContainer = getView().findViewById(R.id.verification_Container_Login);
        continueButton = getView().findViewById(R.id.login_linearlayout_btn_container);
        backgroundContainer = getView().findViewById(R.id.background_container_login);
        verificationContainer.initFromFragment(this);

        //onPress();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                createDialogCode();
                //transitionToMain();

            }
        });


    }

    private void createDialogCode() {
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
                            transitionToMain();
                        }
                    case MotionEvent.ACTION_UP:
                        long millis_end_2 = System.currentTimeMillis();

                        Log.d(TAG, "onTouchend:1 " + millis_end_2);





                }

                return true;
            }
        });
    }

    private void transitionToMain() {
        fragmentSwitcher.changeToFragment(new AllChatsFragment());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.login_fragment_view,container,false);
    }

    public void numberVerificationSubmit() {
        transitionToMain();

    }
}

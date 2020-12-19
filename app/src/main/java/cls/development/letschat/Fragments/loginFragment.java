package cls.development.letschat.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cls.development.letschat.R;

public class loginFragment extends Fragment {
    private static final String TAG = "LoginFragment" ;
    private LinearLayout editTextInstagramLinear;
    private EditText editTextInstagram;


    private EditText telephoneNumber;
    private LinearLayout telephoneNumberLinear;



    private LinearLayout continueButton;
    private TextView continueTextView;
    private long millis_start;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();
    }

    private void init() {
        continueButton = getView().findViewById(R.id.login_linearlayout_btn_container);
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

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_code_number_verification);
        dialog.setCancelable(true);
        dialog.show();



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
        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        Fragment fragment2 = new mainFragment();
        fragmentTransaction2.addToBackStack("xyz");
        fragmentTransaction2.replace(R.id.mainFrame, fragment2);
        fragmentTransaction2.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.login_fragment_view,container,false);
    }
}

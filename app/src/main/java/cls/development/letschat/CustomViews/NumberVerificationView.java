package cls.development.letschat.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.PhoneAuthProvider;

import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.FrontendManagement.ViewModelFactory;
import cls.development.letschat.R;

public class NumberVerificationView extends FrameLayout {
    private static final String TAG = "NumberVerification";
    private LinearLayout linearLayoutContainer;
    private EditText editCodeVerification;
    private PhoneAuthProvider.ForceResendingToken token;
    private String verification;
    private ViewModel viewModel;
    private FragmentActivity activity;

    public NumberVerificationView(@NonNull Context context) {
        super(context);
    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init() {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        Log.d(TAG, "init: " + activity + " " + viewModelFactory);
        viewModel = new ViewModelProvider(activity,viewModelFactory).get(ViewModel.class);
        inflate(getContext(),R.layout.dialog_code_number_verification,this);
        linearLayoutContainer = findViewById(R.id.login_verification_linearlayout_btn_container);
        editCodeVerification = findViewById(R.id.login_verification_edit_btn_edit);

    }
    public void initFromFragment(LoginFragment loginFragment, PhoneAuthProvider.ForceResendingToken token, String verification){
        this.token = token;
        this.verification = verification;
        linearLayoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.checkCode(loginFragment,viewModel.getInsta(),viewModel.getPhone(),(View) linearLayoutContainer,editCodeVerification.getText().toString(),verification);
            }
        });

    }

    public void addFragmentActivity(FragmentActivity requireActivity) {
        this.activity = requireActivity;
    }
}

package cls.development.letschat.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.R;

public class NumberVerificationView extends FrameLayout {
    private LinearLayout linearLayoutContainer;
    public NumberVerificationView(@NonNull Context context) {
        super(context);
        init();
    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public NumberVerificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        inflate(getContext(),R.layout.dialog_code_number_verification,this);
        linearLayoutContainer = findViewById(R.id.login_verification_linearlayout_btn_container);

    }
    public void initFromFragment(LoginFragment loginFragment){
        linearLayoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.numberVerificationSubmit();
            }
        });

    }
}

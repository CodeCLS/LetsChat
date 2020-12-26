package cls.development.letschat.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cls.development.letschat.R;


public class HeaderView extends FrameLayout {
    private TextView textView;
    public HeaderView(@NonNull Context context) {
        super(context);
        init();
    }


    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        inflate(getContext(), R.layout.header_view,this);
        textView= findViewById(R.id.text_insta_header);
    }

    @SuppressLint("SetTextI18n")
    public void setTextInsta(String s) {
        textView.setText("@"+s);
    }
}

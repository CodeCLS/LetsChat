package cls.development.letschat.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import cls.development.letschat.R;

public class MessageView extends LinearLayout {
    private static final String CONSTANT_SENDER_SELF = "self";
    private String sender;
    public MessageView(Context context) {
        super(context);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void init(){
        setMessageBackground();



    }

    private void setMessageBackground() {
        if(sender.equals(CONSTANT_SENDER_SELF))
            LayoutInflater.from(getContext()).inflate(R.layout.message_view_self, this);
        else
            LayoutInflater.from(getContext()).inflate(R.layout.message_view_other, this);
    }
}

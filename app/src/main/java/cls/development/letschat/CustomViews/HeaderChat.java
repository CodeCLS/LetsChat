package cls.development.letschat.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cls.development.letschat.R;

public class HeaderChat extends FrameLayout {
    public HeaderChat(@NonNull Context context) {
        super(context);
    }

    public HeaderChat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public HeaderChat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public HeaderChat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        inflate(getContext(), R.layout.header_chat_layout,this);

    }
}

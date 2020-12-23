package cls.development.letschat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;

import java.util.ArrayList;

import cls.development.letschat.Adapter.MainAdapterChats;
import cls.development.letschat.AdapterCallbackFragment;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;

public class AllChatsFragment extends Fragment implements AdapterCallbackFragment {
    private LinearLayout btnNewChatLinear;
    private TextView btnNewChat;
    private RecyclerView recyclerView;
    private ImageView shareBtn;
    private ImageView copyBtn;
    private ImageView settingsBtn;
    private ImageView feedbackBtn;

    private MainAdapterChats adapterChats;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.main_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        btnNewChat = getView().findViewById(R.id.btn_new_chat_txt);
        btnNewChatLinear = getView().findViewById(R.id.linear_main_btn_chat_new);
        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.chat_recyclerview_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Chat> array = new ArrayList<Chat>();
        array.add(new Chat(false,false, ContextCompat.getColor(getContext(),R.color.secondary),1230812,379187239,null,"Hello","2",null));
        adapterChats = new MainAdapterChats(array,this);
        recyclerView.setAdapter(adapterChats);

    }

    @Override
    public void changeFragmentFromItemClick() {
        transitionToChat();
    }

    private void transitionToChat() {
        ChatFragment chatFragment = new ChatFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        chatFragment.setEnterTransition(new Fade());
        chatFragment.setExitTransition(new Fade());
        fragmentTransaction.replace(R.id.mainFrame,chatFragment);
        fragmentTransaction.commit();
    }
}

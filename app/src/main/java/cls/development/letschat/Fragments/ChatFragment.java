package cls.development.letschat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cls.development.letschat.Adapter.ChatAdapter;
import cls.development.letschat.R;
import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.FrontendManagement.ViewModelFactory;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.Message;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ViewModel viewModel;

    public static void chatChanged(ArrayList<Message> messages, Chat chat) {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.chat_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        viewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(ViewModel.class);
        init();
    }

    private void init() {
        recyclerView = getView().findViewById(R.id.chat_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ChatAdapter(viewModel.getSelectedChat()));

    }
}

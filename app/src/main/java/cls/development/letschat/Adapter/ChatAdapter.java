package cls.development.letschat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cls.development.letschat.MessageManager;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;
import cls.development.letschat.Room.Message;

import static cls.development.letschat.MessageManager.errorHandlingSender;
import static cls.development.letschat.MessageManager.otherInt;
import static cls.development.letschat.MessageManager.otherString;
import static cls.development.letschat.MessageManager.selfInt;
import static cls.development.letschat.MessageManager.selfString;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Chat chat;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == selfInt)
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.messa);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (chat.getMessageArrayList().get(position).senderAsString.equals(selfString))
            return selfInt;
        else if(chat.getMessageArrayList().get(position).senderAsString.equals(otherString))
            return otherInt;
        return errorHandlingSender;
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

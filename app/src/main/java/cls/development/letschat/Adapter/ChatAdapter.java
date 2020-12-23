package cls.development.letschat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view_self,parent,false));
        else if (viewType == otherInt)
            return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view_other,parent,false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        chatViewHolder.textViewContent.setText("asdasd");

    }

    @Override
    public int getItemCount() {
        return 6;
        //return chat.getMessageArrayList().size();
    }

    @Override
    public int getItemViewType(int position) {
        //if (chat.getMessageArrayList().get(position).senderAsString.equals(selfString))
        //    return selfInt;
        //else if(chat.getMessageArrayList().get(position).senderAsString.equals(otherString))
        //    return otherInt;
        return 0;
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView textViewContent;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.text_chat_message_content);
        }
    }
}

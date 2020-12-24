package cls.development.letschat.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cls.development.letschat.AdapterCallbackFragment;
import cls.development.letschat.R;
import cls.development.letschat.Room.Chat;

public class MainAdapterChats extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static Fragment fragmentInstance;
    private static final String TAG = "Adapter";
    private ArrayList<Chat> chatObjects = new ArrayList<>();
    private Context context;

    public MainAdapterChats(ArrayList<Chat> chatObjects,Fragment instance) {
        this.chatObjects = chatObjects;
        MainAdapterChats.fragmentInstance = instance;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false));





    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainViewHolder mainViewHolder = (MainViewHolder) holder;
        Toast.makeText(context, ""+chatObjects.get(position).getColor(), Toast.LENGTH_SHORT).show();
        mainViewHolder.txtUnreadMessages.setText(chatObjects.get(position).getUnreadMessagesCount());
        Glide.with(context).load(new ColorDrawable(chatObjects.get(position).getColor())).into(mainViewHolder.roundImageViewColor);
        mainViewHolder.txtContentLastMessage.setText(chatObjects.get(position).getLastMessageContent());
        if(chatObjects.get(position).isPinned() || chatObjects.get(position).isFavorite() && chatObjects.get(position).getExtraImage() != null)
            Glide.with(context).load(chatObjects.get(position).getExtraImage()).into(mainViewHolder.extraImageView);
        else
            mainViewHolder.extraImageView.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
        return chatObjects.size();
    }


    public class MainViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout container;
        private ImageView extraImageView;
        private de.hdodenhof.circleimageview.CircleImageView roundImageViewColor;
        private TextView txtContentLastMessage;
        private TextView txtUnreadMessages;



        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_chat_item);
            extraImageView = itemView.findViewById(R.id.additional_imageView_chat_item);
            roundImageViewColor = itemView.findViewById(R.id.color_for_chat_conversation_item);
            txtContentLastMessage = itemView.findViewById(R.id.last_message_txt_item_chat);
            txtUnreadMessages = itemView.findViewById(R.id.unread_notifications);

            initActions();

        }

        private void initActions() {
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AdapterCallbackFragment adapterCallbackFragment = (AdapterCallbackFragment)MainAdapterChats.fragmentInstance;
                    adapterCallbackFragment.changeFragmentFromItemClick(chatObjects.get(getAdapterPosition()));


                }
            });


        }
    }

}

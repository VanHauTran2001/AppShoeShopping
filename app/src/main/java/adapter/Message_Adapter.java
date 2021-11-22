package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import model.Chat;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.MessageViewHolder>{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<Chat> chatArrayList;
    private String imageURL;
    FirebaseUser firebaseUser;

    public Message_Adapter(Context context, ArrayList<Chat> chatArrayList, String imageURL) {
        this.context = context;
        this.chatArrayList = chatArrayList;
        this.imageURL = imageURL;
    }

    @Override
    public MessageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new MessageViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Chat chat = chatArrayList.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageURL.equals("default")){
            holder.profile_Image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(imageURL).into(holder.profile_Image);
        }
        if (position == chatArrayList.size()-1){
            if (chat.isSeen()){
                holder.txtSeen.setText("Seen");
            }else {
                holder.txtSeen.setText("Delivered");
            }
        }else {
            holder.txtSeen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile_Image;
        public TextView show_message;
        public TextView txtSeen;

        public MessageViewHolder(View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_Image = itemView.findViewById(R.id.profile_Image);
            txtSeen = itemView.findViewById(R.id.txtSeen);
        }
    }
    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatArrayList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}

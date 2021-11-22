package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.MessageActivity;
import com.example.appprojectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import model.Chat;
import model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private ArrayList<User> userArrayList;
    private boolean isChat;
    String theLastMessage;

    public UserAdapter(Context context, ArrayList<User> userArrayList, boolean isChat) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.isChat = isChat;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        User user = userArrayList.get(position);
//        Log.d("test", user.getUserName()+ "111");
        holder.txtUserName.setText(user.getUserName());

        if (user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(user.getImageURL()).into(holder.profile_image);
        }
        if (isChat){
            lastMessage(user.getId(),holder.lastMessage);
        }else {
            holder.lastMessage.setVisibility(View.GONE);
        }
        if (isChat){
            if (user.getStatus().equals("online")){
                holder.imgOnline.setVisibility(View.VISIBLE);
                holder.imgOffline.setVisibility(View.GONE);
            }else {
                holder.imgOnline.setVisibility(View.GONE);
                holder.imgOffline.setVisibility(View.VISIBLE);
            }
        }else {
            holder.imgOnline.setVisibility(View.GONE);
            holder.imgOffline.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MessageActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUserName;
        private ImageView profile_image;
        private ImageView imgOnline;
        private ImageView imgOffline;
        private TextView lastMessage;
        public UserViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            profile_image = itemView.findViewById(R.id.profile_image);
            imgOnline = itemView.findViewById(R.id.imgOnline);
            imgOffline = itemView.findViewById(R.id.imgOffline);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }

    //check fpr last message
    private void lastMessage(String userid , TextView lastMessage){
        theLastMessage = "default";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (firebaseUser==null){
                        return;
                    }
                    if (chat.getReceiver().equals(firebaseUser.getUid()) &&chat.getSender().equals(userid)
                    ||chat.getReceiver().equals(userid) &&chat.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chat.getMessage();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        lastMessage.setText("No message");
                        break;
                    default:
                        lastMessage.setText(theLastMessage);
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}

package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojectcuoikhoa.R;

import java.util.List;

import model.Message;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.MessageHolder>{
    List<Message> messageList;

    public Message_Adapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public MessageHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder( Message_Adapter.MessageHolder holder, int position) {
        holder.txtMessage.setText(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{
        private TextView txtMessage;

        public MessageHolder( View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
    }
}

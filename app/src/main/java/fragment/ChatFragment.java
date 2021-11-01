package fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentChatBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Message_Adapter;
import model.Message;

public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    ArrayList<Message> messageArrayList;
    Message_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false);
        messageArrayList = new ArrayList<>();
        adapter = new Message_Adapter(messageArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recylerMessage.setLayoutManager(linearLayoutManager);
        binding.recylerMessage.setAdapter(adapter);
        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
        binding.edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checlKeyBoard();
            }
        });
        return binding.getRoot();
    }
    private void SendMessage(){
        String message = binding.edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)){
            Toast.makeText(getActivity(), "Bạn chưa nhập dữ liệu !", Toast.LENGTH_SHORT).show();
            return;
        }else {
            messageArrayList.add(new Message(message));
            adapter.notifyDataSetChanged();
            binding.recylerMessage.scrollToPosition(messageArrayList.size()-1);
            binding.edtMessage.setText("");
        }
    }
    public void checlKeyBoard(){
        binding.activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                binding.activityRoot.getWindowVisibleDisplayFrame(rect);
                int heightDiff = binding.activityRoot.getRootView().getHeight() - rect.height();
                if (heightDiff > 0.25*binding.activityRoot.getRootView().getHeight()){
                    if (messageArrayList.size()>0){
                        binding.recylerMessage.scrollToPosition(messageArrayList.size()-1);
                        binding.activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}

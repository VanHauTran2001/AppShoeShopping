package fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import adapter.Message_Adapter;
import model.Chat;
import model.User;

import static android.app.Activity.RESULT_OK;
import static com.example.appprojectcuoikhoa.HomeActivity.MY_REQUEST_CODE;

public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String name = firebaseUser.getDisplayName();
                if (name == null){
                    binding.nameUser.setText(user.getUserName() + "");
                }else {
                    binding.nameUser.setText(name + "");
                }
                if (user.getImageURL().equals("default")){
                        binding.imageProfile.setImageResource(R.mipmap.ic_launcher);
                }else {
                       Glide.with(getActivity()).load(user.getImageURL()).into(binding.imageProfile);
                    }

            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        //add number chat
        //lay du lieu tu Realtiem db
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        binding.tabLayout.setupWithViewPager(binding.viewPager);
//        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                int unread = 0;
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Chat chat = dataSnapshot.getValue(Chat.class);
//                    if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isSeen()){
//                        unread++;
//                    }
//                }
//                if (unread==0){
//                    viewPagerAdapter.addFragment(new TinNhanFragment(),"Chats");
//                }else {
//                    viewPagerAdapter.addFragment(new TinNhanFragment(),"("+unread+") Chats");
//                }
//                viewPagerAdapter.addFragment(new UserFragment(),"User");
//                binding.viewPager.setAdapter(viewPagerAdapter);
//            }
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
        //update name and avata
        ShowInformation();
        return binding.getRoot();
    }

    private void ShowInformation() {
        if (firebaseUser==null){
            return;
        }
        Uri avata = firebaseUser.getPhotoUrl();
        Glide.with(this).load(avata).error(R.drawable.ic_baseline_account_circle_24).into(binding.imageProfile);
    }
    //Tab Viewpager
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new TinNhanFragment(),"Chats");
        viewPagerAdapter.addFragment(new UserFragment(),"User");
        binding.viewPager.setAdapter(viewPagerAdapter);
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }
//    private void SendMessage(){
//        String message = binding.edtMessage.getText().toString().trim();
//        if (TextUtils.isEmpty(message)){
//            Toast.makeText(getActivity(), "Bạn chưa nhập dữ liệu !", Toast.LENGTH_SHORT).show();
//            return;
//        }else {
//            messageArrayList.add(new Message(message));
//            adapter.notifyDataSetChanged();
//            binding.recylerMessage.scrollToPosition(messageArrayList.size()-1);
//            binding.edtMessage.setText("");
//        }
//    }
//    public void checlKeyBoard(){
//        binding.activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                binding.activityRoot.getWindowVisibleDisplayFrame(rect);
//                int heightDiff = binding.activityRoot.getRootView().getHeight() - rect.height();
//                if (heightDiff > 0.25*binding.activityRoot.getRootView().getHeight()){
//                    if (messageArrayList.size()>0){
//                        binding.recylerMessage.scrollToPosition(messageArrayList.size()-1);
//                        binding.activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                }
//            }
//        });
//    }
}

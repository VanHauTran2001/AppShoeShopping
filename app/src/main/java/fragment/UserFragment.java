package fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.UserAdapter;
import model.User;


public class UserFragment extends Fragment {

    FragmentUserBinding binding;
    private UserAdapter userAdapter;
    private ArrayList<User> userArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);
        binding.recylerUser.setHasFixedSize(true);
        binding.recylerUser.setLayoutManager(new LinearLayoutManager(getContext()));
        userArrayList = new ArrayList<>();
       userAdapter = new UserAdapter(getActivity(),userArrayList,false);
        binding.recylerUser.setAdapter(userAdapter);
        readUser();
        binding.searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchUserText(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return binding.getRoot();
    }
    private void searchUserText(String s) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("search")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    assert  firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid())){
                        userArrayList.add(user);

                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void readUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userArrayList.clear();

                if (binding.searchUser.getText().toString().equals("")) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        assert user !=null;
                        assert firebaseUser != null;
//                        if (user.getId() != null){
//                            userArrayList.add(user);
//                        }
                        if (user.getId() != null &&!user.getId().equals(firebaseUser.getUid())) {
                            userArrayList.add(user);
                        }
                    }

                    userAdapter = new UserAdapter(getActivity(),userArrayList,false);
                    binding.recylerUser.setAdapter(userAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
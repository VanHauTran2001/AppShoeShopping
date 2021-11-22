package com.example.appprojectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.appprojectcuoikhoa.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUserName = binding.edtUserSignup.getText().toString().trim();
                String ndEmail = binding.edtUserEmailSignup.getText().toString().trim();
                String ndPass = binding.edtPasswordSignup.getText().toString().trim();
                if (TextUtils.isEmpty(ndEmail)){
                    binding.edtUserEmailSignup.setError("Email cannot be empty");
                    binding.edtUserEmailSignup.requestFocus();
                }else if (TextUtils.isEmpty(ndPass)){
                    binding.edtPasswordSignup.setError("Password cannot be empty");
                    binding.edtPasswordSignup.requestFocus();
                }else if (TextUtils.isEmpty(txtUserName)){
                    binding.edtUserSignup.setError("User can not be empty !");
                    binding.edtUserSignup.requestFocus();
                } else {
                    register(txtUserName,ndEmail,ndPass);

                }
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });
    }
    private void register(final String userName, String ndEmail, String ndPass) {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(ndEmail,ndPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("User").child(userID);
                            HashMap<String ,String> hashMap = new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("userName",userName);
                            hashMap.put("imageURL","default");
                            hashMap.put("status","offline");
                            hashMap.put("search",userName.toLowerCase());
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(SignupActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
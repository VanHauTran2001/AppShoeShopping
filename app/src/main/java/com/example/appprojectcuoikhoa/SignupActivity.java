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

public class    SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        progressDialog = new ProgressDialog(this);
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignup();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });
    }

    private void onClickSignup() {

                String ndEmail = binding.edtUserEmailSignup.getText().toString().trim();
                String ndPass = binding.edtPasswordSignup.getText().toString().trim();
                    if (TextUtils.isEmpty(ndEmail)){
                        binding.edtUserEmailSignup.setError("Email cannot be empty");
                        binding.edtUserEmailSignup.requestFocus();
                    }else if (TextUtils.isEmpty(ndPass)){
                        binding.edtPasswordSignup.setError("Password cannot be empty");
                        binding.edtPasswordSignup.requestFocus();
                    }else {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(ndEmail,ndPass)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                            finishAffinity();
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }

    }
}
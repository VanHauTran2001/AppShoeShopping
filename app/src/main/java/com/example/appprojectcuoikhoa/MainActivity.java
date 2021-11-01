package com.example.appprojectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appprojectcuoikhoa.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;
    int passwordNotVisible = 1;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        sharedPreferences= getSharedPreferences("dataLogin",MODE_PRIVATE);
        //lấy giá trị
        binding.edtEmail.setText(sharedPreferences.getString("user",""));
        binding.edtPassword.setText(sharedPreferences.getString("pass",""));
        binding.checkPass.setChecked(sharedPreferences.getBoolean("checked",false));

        progressDialog = new ProgressDialog(this);
        binding.imgCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChecked();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_alpha);
        binding.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
            }
        });
        binding.txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });
        binding.txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
            }
        });
    }

    public void onChecked(){
        if (passwordNotVisible == 1) {
            binding.imgCheckPass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            binding.edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordNotVisible = 0;
        } else {
            binding.imgCheckPass.setImageResource(R.drawable.ic_check_on);
            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordNotVisible = 1;
        }
        binding.edtPassword.setSelection(binding.edtPassword.length());
    }
    private void onClickLogin(){
        String edtEmail = binding.edtEmail.getText().toString().trim();
        String edtPassword = binding.edtPassword.getText().toString().trim();
        if (binding.checkPass.isChecked()){
            Toast.makeText(MainActivity.this, "Đã lưu tài khoản", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user",edtEmail);
            editor.putString("pass",edtPassword);
            editor.putBoolean("checked",true);
            editor.commit();
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user");
            editor.remove("pass");
            editor.remove("checked");
            editor.commit();
        }
        if (TextUtils.isEmpty(edtEmail)){
            binding.edtEmail.setError("Email cannot be empty");
            binding.edtPassword.requestFocus();
        }else if (TextUtils.isEmpty(edtPassword)){
            binding.edtPassword.setError("Password cannot be empty");
            binding.edtPassword.requestFocus();
        }else {
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            progressDialog.show();
            Auth.signInWithEmailAndPassword(edtEmail,edtPassword)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            } else {
                                dialogLogin(Gravity.CENTER);
                            }
                        }
                    });
        }

    }
    private void dialogLogin(int gravity){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_sigin);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = gravity;
        window.setAttributes(windowAtributes);
        if (Gravity.CENTER ==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        Button btnDialogOk = dialog.findViewById(R.id.btnDialogSignin);

        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
package com.example.appprojectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.appprojectcuoikhoa.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_reset_password);
       progressDialog = new ProgressDialog(this);
       binding.btnOKReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClickResetPassword();
           }
       });
       binding.btnCancelReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(ResetPasswordActivity.this,MainActivity.class));
           }
       });
    }
    private void onClickResetPassword(){
        String strEmail = binding.edtEmailReset.getText().toString().trim();
        if (TextUtils.isEmpty(strEmail)){
            binding.edtEmailReset.setError("Email can not be empty");
            binding.edtEmailReset.requestFocus();
        }else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();
            auth.sendPasswordResetEmail(strEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                OpenDialog(Gravity.CENTER);
                            }
                        }
                    });
        }
    }
    private void OpenDialog(int gravity){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_reset);
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
        Button DialogOK = dialog.findViewById(R.id.btnDialogOK);

        DialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this,MainActivity.class));
            }
        });
        dialog.show();
    }
}
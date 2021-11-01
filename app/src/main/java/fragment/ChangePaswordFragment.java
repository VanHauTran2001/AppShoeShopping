package fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appprojectcuoikhoa.HomeActivity;
import com.example.appprojectcuoikhoa.MainActivity;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.ResetPasswordActivity;
import com.example.appprojectcuoikhoa.databinding.FragmentChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePaswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;
    ProgressDialog progressDialog;
    MainActivity m_activity;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password,container,false);
       progressDialog = new ProgressDialog(getActivity());
        binding.btnOKChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangePassword();
            }
        });
        binding.btnCancelChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
       return binding.getRoot();
    }

    private void onClickChangePassword(){
        String curentPass = binding.edtCurrentPass.getText().toString().trim();
        String confirmPass = binding.edtConfirmPass.getText().toString().trim();
        String newPass = binding.edtNewPass.getText().toString().trim();
        progressDialog.show();
         if (TextUtils.isEmpty(curentPass)){
            binding.edtCurrentPass.setError("Current Password cannot be empty");
            binding.edtCurrentPass.requestFocus();
            progressDialog.dismiss();
        }else if (TextUtils.isEmpty(confirmPass)){
            binding.edtConfirmPass.setError("Confirm Password cannot be empty");
            binding.edtConfirmPass.requestFocus();
            progressDialog.dismiss();
        }else if (TextUtils.isEmpty(newPass)){
             binding.edtNewPass.setError("New Password cannot be empty");
             binding.edtNewPass.requestFocus();
             progressDialog.dismiss();
         }else if (!curentPass.equals(confirmPass)){
            Toast.makeText(getActivity(), "Xác nhận mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(newPass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                OpenDialog(Gravity.CENTER);
                            }else {
                                Toast.makeText(getActivity(), "Mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void OpenDialog(int gravity){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_change_password);
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
       Button btnDialogChangeOK = dialog.findViewById(R.id.btnDialogChangeOK);

        btnDialogChangeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
               dialog.dismiss();
            }
        });
        dialog.show();
    }
}

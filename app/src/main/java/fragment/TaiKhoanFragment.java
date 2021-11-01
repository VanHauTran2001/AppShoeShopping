package fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.HomeActivity;
import com.example.appprojectcuoikhoa.MainActivity;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentTaiKhoanBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;

import Utils.Utils;

import static com.example.appprojectcuoikhoa.HomeActivity.MY_REQUEST_CODE;

public class TaiKhoanFragment extends Fragment {
    FragmentTaiKhoanBinding binding;
    ProgressDialog progressDialog;
    private Uri mUri;
    private HomeActivity homeActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tai_khoan,container,false);
        homeActivity = (HomeActivity) getActivity();
        progressDialog = new ProgressDialog(getActivity());
        setUserInformation();
        initListener();
        return binding.getRoot();
    }

    private void setUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            return;
        }
        else {

            binding.edtFullName.setText(user.getDisplayName());
            binding.edtMail.setText(user.getEmail());
            Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.avatar).into(binding.imageAvatar);
        }
    }
    private void initListener(){
        binding.imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickRequestPermission();
            }
        });
        binding.btnupdateTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateTaiKhoan();
                String phone = binding.edtPhoneNumber.getText().toString().trim();
                String diachi = binding.edtDiaChi.getText().toString().trim();
                Utils.PHONE = phone;
                Utils.DIACHI = diachi;
            }
        });
    }
    private void onClickRequestPermission(){
        if (homeActivity==null){
            return;
        }
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            homeActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            homeActivity.openGallery();
        }else {
            String [] permison = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permison,MY_REQUEST_CODE);
        }
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        binding.imageAvatar.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void onClickUpdateTaiKhoan(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            return;
        }
        progressDialog.show();
        String strFullName = binding.edtFullName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Update Tài Khoản Thành Công", Toast.LENGTH_SHORT).show();
                            homeActivity.showUserInformation();
                        }
                    }
                });
        String strEmail = binding.edtMail.getText().toString().trim();
        user.updateEmail(strEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           homeActivity.showUserInformation();
                        }
                    }
                });
    }
}

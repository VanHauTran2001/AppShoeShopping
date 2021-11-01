package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentDiaChiBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;


public class DiaChiCuaHangFragment extends Fragment implements OnMapReadyCallback {
    FragmentDiaChiBinding binding;
    GoogleMap map;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dia_chi,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.ggMapFragment);
        mapFragment.getMapAsync(this);
        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(21.054160837963042, 105.73496948202447);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        map.addMarker(new MarkerOptions()
                .title("Cửa hàng giày Hậu Sneaker")
                .snippet("298 Đ.Cầu Diễn,Cầu Diễn,Từ Liêm,Hà Nội")
                .position(latLng));
    }
}

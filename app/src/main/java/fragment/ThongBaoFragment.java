package fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojectcuoikhoa.HomeActivity;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentThongBaoBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Utils.Utils;
import adapter.ThongBao_Adapter;
import data.FavoriteDB;
import model.ThongBao;

public class ThongBaoFragment extends Fragment {
    FragmentThongBaoBinding binding;
    ThongBao_Adapter thongBaoAdapter;
    List<ThongBao> thongBaoList;
    FavoriteDB favoriteDB;
    ThongBao mThongBao;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteDB = new FavoriteDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_thong_bao,container,false);
        thongBaoList = new ArrayList<>();
        addDuLieu();
        thongBaoAdapter = new ThongBao_Adapter(getActivity(),thongBaoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recylerThongBao.setLayoutManager(layoutManager);
        binding.recylerThongBao.setAdapter(thongBaoAdapter);

        return binding.getRoot();
    }

    public void addDuLieu() {
        Cursor dataThongbqao = favoriteDB.ShowThongBao();
        if (dataThongbqao != null){
            while (dataThongbqao.moveToNext()){
                int id = dataThongbqao.getInt(0);
                String txtthongbao = dataThongbqao.getString(1);
                String hour = dataThongbqao.getString(2);
                String day = dataThongbqao.getString(3);
                thongBaoList.add(new ThongBao(id,txtthongbao,hour,day));
            }
        }


//        double money = Utils.MONNEY;
//        String s = String.valueOf(new DecimalFormat("###,###,###.000").format(money) + "đ");
//        if (money!=0.0) {
//            String thongBao = "Bạn vừa đặt thành công đơn hàng có giá trị là " + s
//                    + ". Xin cảm ơn quý khách đã tin tưởng .";
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            String date = currentDate.format(calendar.getTime());
//            SimpleDateFormat currentHour = new SimpleDateFormat("HH:mm:ss");
//            String hour = currentHour.format(calendar.getTime());
//            thongBaoList.add(new ThongBao(thongBao, hour, date));
//        }else {
//            Toast.makeText(getActivity(), "Không có đơn hàng nào !", Toast.LENGTH_SHORT).show();
//        }
    }
}

package data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.GioHang;
//
public class DataLocalManager {
//    private static final String PREF_OBJECT_GIOHANG = "PREF_OBJECT_GIOHANG";
//    private static final String PREF_LIST_GIOHANG = "PREF_LIST_GIOHANG";
//    private static DataLocalManager instance;
//    private MyShareFerences myShareFerences;
//
//    public static void init(Context context){
//        instance = new DataLocalManager();
//        instance.myShareFerences = new MyShareFerences(context);
//    }
//    public static DataLocalManager getInstance(){
//        if (instance == null){
//            instance = new DataLocalManager();
//        }
//        return instance;
//    }
//    public static void setDuLieuGioHang(GioHang gioHang){
//        Gson gson = new Gson();
//        String strJson = gson.toJson(gioHang);
//        DataLocalManager.getInstance().myShareFerences.putStringValue(PREF_OBJECT_GIOHANG,strJson);
//    }
//    public static GioHang getGioHang(){
//        String strJson = DataLocalManager.getInstance().myShareFerences.getStringValue(PREF_OBJECT_GIOHANG);
//        Gson gson = new Gson();
//        GioHang gioHang = gson.fromJson(strJson,GioHang.class);
//        return gioHang;
//    }
//    public static void setListGioHang(List<GioHang> listGioHang){
//        Gson gson = new Gson();
//        JsonArray jsonArray = gson.toJsonTree(listGioHang).getAsJsonArray();
//        String strJsonArray = jsonArray.toString();
//        DataLocalManager.getInstance().myShareFerences.putStringValue(PREF_LIST_GIOHANG,strJsonArray);
//    }
//    public static List<GioHang> getListGioHang(){
//        String strJsonArray = DataLocalManager.getInstance().myShareFerences.getStringValue(PREF_LIST_GIOHANG);
//        List<GioHang> gioHangList = new ArrayList<>();
//        try {
//            JSONArray jsonArray = new JSONArray(strJsonArray);
//            JSONObject jsonObject;
//            GioHang gioHang;
//            Gson gson = new Gson();
//            for (int i=0;i<jsonArray.length();i++){
//                jsonObject = jsonArray.getJSONObject(i);
//                gioHang = gson.fromJson(jsonObject.toString(),GioHang.class);
//                gioHangList.add(gioHang);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return gioHangList;
//    }
//    private void checkForNullKey(String key){
//        if (key == null){
//            throw new NullPointerException();
//        }
//    }
}

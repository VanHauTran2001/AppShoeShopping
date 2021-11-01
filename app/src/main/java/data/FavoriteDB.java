package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavoriteDB extends SQLiteOpenHelper {
    public static int DB_VERSION = 3;
    public static String DATABASE_NAME = "ShoeDB";
    public static String TABLE_NAME = "favoriteTable";
    public static String TABLE_THONGBAO = "thongbaoTable";

    public static String KEY_ID = "id";
    public static String IMG_SHOE = "imgShoe";
    public static String IMG_FAVORITE = "imgFavorite";
    public static String NAME_SHOE = "nameShoe";
    public static String MONEY_SHOE = "moneyShoe";
    public static String DONG = "dongShoe";
    public static String FAVORITE_STATUS = "fStatus";
    public static String LOGO_SALE = "logoSale";
    public static String DONG_SALE_BD = "dongSaleBD";
    public static String MONEY_SALE_BD = "moneySaleBD";

    public static String ID_THONGBAO = "idThongBao";
//    public static String IMG_LOGO_TB = "imgLogoThongBao";
    public static String TXT_TB = "TXT_TB";
    public static String HOUR = "hour";
    public static String DAY = "day";


    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT,"
            + IMG_SHOE+ " TEXT,"
            + LOGO_SALE + " TEXT,"
            + IMG_FAVORITE + " TEXT,"
            + NAME_SHOE + " TEXT,"
            + MONEY_SHOE + " TEXT,"
            + DONG + " TEXT,"
            + MONEY_SALE_BD + " TEXT,"
            + DONG_SALE_BD + " TEXT,"
            + FAVORITE_STATUS+" TEXT)";

    private static String CREATE_TABLE_THONGBAO = "CREATE TABLE thongbaoTable (ID_THONGBAO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TXT_TB TEXT, HOUR TEXT, DAY TEXT )";

    public FavoriteDB(Context context) {
        super(context,DATABASE_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_THONGBAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!= newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THONGBAO);
            onCreate(db);
        }
    }

    //create emty table
    public void insertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        for (int i=1;i<11;i++){
            cv.put(KEY_ID,i);
            cv.put(FAVORITE_STATUS,"0");
            db.insert(TABLE_NAME,null,cv);
        }
    }

    //insert data into database
    public void insertIntoTheDatabase(String id,String favStatus,String logoSale,String imgShoe,String imgFavoriteShoe,String nameShoe,String dong,Double moneyShoe,String dongSaleBD,Double moneySaleBD){
        SQLiteDatabase database;
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,id);
        cv.put(FAVORITE_STATUS,favStatus);
        cv.put(LOGO_SALE,logoSale);
        cv.put(IMG_SHOE,imgShoe);
        cv.put(IMG_FAVORITE,imgFavoriteShoe);
        cv.put(NAME_SHOE,nameShoe);
        cv.put(DONG,dong);
        cv.put(MONEY_SHOE,moneyShoe);
        cv.put(DONG_SALE_BD,dongSaleBD);
        cv.put(MONEY_SALE_BD,moneySaleBD);
        database.insert(TABLE_NAME,null,cv);
    }

    public void insertThongBaoDatabase(String txtThongBao , String hour , String day){
        SQLiteDatabase database;
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TXT_TB,txtThongBao);
        contentValues.put(HOUR,hour);
        contentValues.put(DAY,day);
        database.insert(TABLE_THONGBAO,null,contentValues);
    }
    public Cursor ShowThongBao(){
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_THONGBAO;
        return database.rawQuery(sql,null,null);
    }
    public void removeThongBao(int idThongBao){
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_THONGBAO + " WHERE ID_THONGBAO=" + idThongBao + "";
        database.execSQL(sql);
    }
    //read all data
    public Cursor readAllData(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " +KEY_ID+"="+id+"";
        return database.rawQuery(sql,null,null);
    }

    //remove line from database
    public void removeFavorite(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = " UPDATE "+TABLE_NAME+" SET "+FAVORITE_STATUS+"='0' WHERE "+KEY_ID+"="+id+"";
        database.execSQL(sql);
    }

    //select all favorite list
    public Cursor selectAllFavoriteList(){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " +FAVORITE_STATUS+" ='1'";
        return database.rawQuery(sql,null,null);
    }

}

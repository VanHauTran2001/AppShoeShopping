package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import model.GioHang;
import model.Shoe;
//
//@androidx.room.Database(entities = {Shoe.class},version = 1)
//public abstract class Database extends RoomDatabase {
//    private static final String DATABASE_NAME = "user.db";
//    private static Database instance;
//
//    public static synchronized Database getInstance(Context context){
//        if (instance==null){
//            instance = Room.databaseBuilder(context.getApplicationContext(),Database.class,DATABASE_NAME)
//                        .allowMainThreadQueries()
//                        .build();
//        }
//        return instance;
//    }
//    public abstract UserDAO userDAO();
//}

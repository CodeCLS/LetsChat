package cls.development.letschat.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cls.development.letschat.Converter;

@Database(entities = Message.class , exportSchema = false,version=1)
@TypeConverters({Converter.class})
public abstract class RoomDB extends RoomDatabase {
    public final static String room_name = "ChatDB";
    private static RoomDB instance;
    public static synchronized RoomDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,room_name)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }
    public abstract ChatDao chatDao();
}

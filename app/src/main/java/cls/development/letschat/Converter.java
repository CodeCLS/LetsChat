package cls.development.letschat;

import androidx.room.TypeConverter;

import cls.development.letschat.Room.Media;

public class Converter {
    @TypeConverter
    public static String fromMessageMedia(Media media) {
        return media == null? null : media.getUrl() + "-letschat-" + media.getType();
    }

    @TypeConverter
    public static Media stringToMessageMedia(String mediaString) {
        if (mediaString == null)
            return null;
        String[] array = mediaString.split("-letschat-");
        return new Media(array[0], array.length > 1? array[1] :null);

    }
}

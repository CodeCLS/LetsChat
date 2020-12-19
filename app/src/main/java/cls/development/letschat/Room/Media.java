package cls.development.letschat.Room;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {
    private String url;
    private String type;


    public Media(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(type);

    }
}

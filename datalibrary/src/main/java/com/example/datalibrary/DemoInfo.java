package com.example.datalibrary;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;


public class DemoInfo implements Parcelable {
    public String status;
    public String msg;
    public int code;
    public List<DemoInnerDataInfo> datas;

    public DemoInfo(){
    }
    protected DemoInfo(Parcel in) {
        status = in.readString();
        msg = in.readString();
        code = in.readInt();
        datas = in.createTypedArrayList(DemoInnerDataInfo.CREATOR);
    }

    public static final Creator<DemoInfo> CREATOR = new Creator<DemoInfo>() {
        @Override
        public DemoInfo createFromParcel(Parcel in) {
            return new DemoInfo(in);
        }

        @Override
        public DemoInfo[] newArray(int size) {
            return new DemoInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pParcel, int pI) {
        pParcel.writeString(status);
        pParcel.writeString(msg);
        pParcel.writeInt(code);
        pParcel.writeTypedList(datas);
    }

    public static class DemoInnerDataInfo implements Parcelable{
        public String thumbnail;
        public String title;
        public String author;
        public String id;

        public DemoInnerDataInfo(){}

        public DemoInnerDataInfo(Parcel in) {
            thumbnail = in.readString();
            title = in.readString();
            author = in.readString();
            id = in.readString();
        }

        public static final Creator<DemoInnerDataInfo> CREATOR = new Creator<DemoInnerDataInfo>() {
            @Override
            public DemoInnerDataInfo createFromParcel(Parcel in) {
                return new DemoInnerDataInfo(in);
            }

            @Override
            public DemoInnerDataInfo[] newArray(int size) {
                return new DemoInnerDataInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel pParcel, int pI) {
            pParcel.writeString(thumbnail);
            pParcel.writeString(title);
            pParcel.writeString(author);
            pParcel.writeString(id);
        }
    }
}

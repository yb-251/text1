package com.example.datalibrary;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 任小龙 on 2020/10/16.
 */
public class VipBannerAndLiveInfo {
    public List<BannerVip> lunbotu;
    public LiveVip live;

    public static class BannerVip{
        public String img;
        public String url;
    }
    public static class LiveVip{
        public int count;
        public List<LiveVipListInfo> live;
    }

    public static class LiveVipListInfo implements Serializable {
        private static final long serialVersionUID = -7638701530950638821L;
        public  String amiba_id;
        public  String m_specialty_id;
        public  String id;
        public  String live_id;
        public  String teacher_name;
        public  String live_name;
        public  String correlative_lessons;
        public  String end_time;
        public  String teacher_uid;
        public  String specialty_id;
        public  String activityId;
        public  String start_time;
        public  String room_id;
        public  String is_liveing;
        public  String startTime;
        public  String endTime;
        public  String teacher_photo;
        public  String activityName;
        public  String lesson_id;
        public  String type;
        public  String from_type;
        public  String startDateTime;
        public  String startDate;
        public  String lessonPic;
        public  String url;
    }
}

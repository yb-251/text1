package com.example.datalibrary;

import java.util.List;

/**
 * Created by 任小龙 on 2020/10/16.
 */
public class VipListInfo {
    public List<VipInnerList> list;
    public String count;
    public int is_vip;
    public int is_check;
    public static class VipInnerList{

        public String lesson_id;
        public String lesson_name;
        public String thumb;
        public String studentnum;
        public String teacher_id;
        public String amiba_id;
        public String price;
        public String vip_price;
        public String show_vip_tag;
        public String vip_tag_status;
        public String url;
        public String length;
        public String teacher_name;
        public String comment_rate;
        public String comment_level;
        public String vip_thumb;

    }
}

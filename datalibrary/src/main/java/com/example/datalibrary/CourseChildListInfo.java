package com.example.datalibrary;

import java.io.Serializable;
import java.util.List;

public class CourseChildListInfo implements Serializable {
    private static final long serialVersionUID = -3229263492466521536L;
    public List<ListContent> lists;

    public static class ListContent implements Serializable {
        private static final long serialVersionUID = 5990712151767801325L;
        public String id;
        public String lesson_id;
        public String lesson_name;
        public String type;
        public String price;
        public String vip_price;
        public int show_vip_tag;
        public String thumb;
        public String specialty_id;
        public String studentnum;
        public String m_specialty_id;
        public String rank;
        public List<String> comment_html;
        public String comment_htmls;
        public String rate;
        public int vip_tag_status;
    }
}

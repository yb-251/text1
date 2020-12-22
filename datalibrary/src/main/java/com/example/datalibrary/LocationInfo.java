package com.example.datalibrary;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 任小龙 on 2020/10/15.
 */
public class LocationInfo implements Serializable {
    private static final long serialVersionUID = -4230738693549321006L;
    public int code;
    public String msg;
    public List<AreaInfo> data;
}

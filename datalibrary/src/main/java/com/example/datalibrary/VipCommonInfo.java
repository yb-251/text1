package com.example.datalibrary;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 任小龙 on 2020/10/16.
 */
public class VipCommonInfo implements Serializable {

    private static final long serialVersionUID = 6696296715802347577L;

    public List<VipBannerAndLiveInfo.BannerVip> lunbotu ;
    public List<VipBannerAndLiveInfo.LiveVipListInfo> live ;
    public List<VipListInfo.VipInnerList> list;

    public VipCommonInfo(List<VipBannerAndLiveInfo.BannerVip> pLunbotu, List<VipBannerAndLiveInfo.LiveVipListInfo> pLive, List<VipListInfo.VipInnerList> pList) {
        lunbotu = pLunbotu;
        live = pLive;
        list = pList;
    }
}

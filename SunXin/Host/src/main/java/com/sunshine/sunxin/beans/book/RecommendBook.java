package com.sunshine.sunxin.beans.book;

// Copyright (c) 2016 ${ORGANIZATION_NAME}. All rights reserved.

/**
 * Created by 钟光燕 on 2016/10/24.
 * e-mail guangyanzhong@163.com
 */

public class RecommendBook {
    /**
     * _id : 526e8e3e7cfc087140004df7
     * author : 太一生水
     * cover : /agent/http://image.cmfu.com/books/3347382/3347382.jpg
     * shortIntro : 十大封号武帝之一，绝世古飞扬在天荡山脉陨落，于十五年后转世重生，化为天水国公子李云霄，开启了一场与当世无数天才相争锋的逆天之旅。武道九重，十方神境，从此整个世界...
     * title : 万古至尊
     * hasCp : true
     * latelyFollower : 3053
     * retentionRatio : 42.59
     * updated : 2016-07-25T15:29:51.703Z
     * chaptersCount : 2406
     * lastChapter : 第2406章 千载风云尽付一笑（大结局）
     */

    public String _id;
    public String author;
    public String cover;
    public String shortIntro;
    public String title;
    public boolean hasCp;
    public boolean isTop = false;
    public boolean isSeleted = false;
    public boolean showCheckBox = false;
    public boolean isFromSD = false;
    public String path = "";
    public int latelyFollower;
    public double retentionRatio;
    public String updated;
    public int chaptersCount;
    public String lastChapter;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecommendBook) {
            RecommendBook bean = (RecommendBook) obj;
            return this._id.equals(bean._id);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this._id.hashCode();
    }
}

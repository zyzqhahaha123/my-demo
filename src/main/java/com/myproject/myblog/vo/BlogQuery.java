package com.myproject.myblog.vo;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-29 14:41
 */
public class BlogQuery {

    private String title;

    private Long typeId;

    private boolean recommend;

    public BlogQuery() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }
}

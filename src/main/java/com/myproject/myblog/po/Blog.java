package com.myproject.myblog.po;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-25 15:37
 */

// Hibernate4.3 以后 @Entity已经可以代替 @Table
@Entity //表明该类 (UserEntity) 为一个实体类，它默认对应数据库中的表名是user_entity
@Table(name="t_blog")//指定与数据库表对应的名字
public class Blog {

    @Id
    @GeneratedValue //生成
    private Long id;

    private String title; //标题

    @Basic(fetch = FetchType.LAZY) //懒加载，只有在使用到这个字段的时候才回去加载
    @Lob //让Sting 默认的255长度变大
    private String content; //内容

    private String firstPicture; //首图

    private String flag; //标记

    private Integer views; //浏览次数

    private boolean appreciation; //赞赏是否开启

    private boolean shareStatement; //版权是否开启 转载说明

    private boolean comment; //评论是否开启 评论区

    private boolean published; //是否发布

    private boolean recommend; //是否推荐

    private String description;//描述

    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;//创建时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间

    //建立实体类关系
    @ManyToOne
    private Type type;

    //多对多
    @ManyToMany(cascade = {CascadeType.PERSIST})  //定义级联关系 给当前设置的实体操作另一个实体的权限
    private List<Tag> tags= new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient //该注解 可以让这个字段不会入库，表示只是一个单纯的属性，不是表字段
    private String tagIds;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }


    //初始化一下tagIds
    public void init(){
        this.tagIds = tr(this.getTags());
    }
    //把传过来的集合转换成 1,2,3 的这种数组
    public String tr(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            boolean f = false;
            for (Tag tag : tags){
                if(f){
                    stringBuffer.append(",");
                }else {
                    f = true;
                }
                stringBuffer.append(tag.getId());
            }
            return stringBuffer.toString();
        }
        return tagIds;
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", comment=" + comment +
                ", published=" + published +
                ", recommend=" + recommend +
                ", description='" + description + '\'' +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
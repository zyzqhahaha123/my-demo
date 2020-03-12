package com.myproject.myblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-25 16:22
 */
@Entity
@Table(name = "t_comment")
public class Comment extends  ParentEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;//昵称

    private String email;

    private String content;

    private String avatar;

    @Temporal(TemporalType.TIMESTAMP) //这个是日期加时间
    private Date creatTime;

    @ManyToOne
    private Blog blog;


    @OneToMany(mappedBy = "parentComment") //父对应多个子
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment; //只有一个父


    private boolean adminComment;//是否是管理员


    public Comment() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public Date getCreatTime() {
        return creatTime;
    }

    @Override
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", creatTime=" + creatTime +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}

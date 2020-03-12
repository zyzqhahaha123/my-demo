package com.myproject.myblog.po;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-25 15:49
 */
public class ParentEntity {

    @Temporal(TemporalType.TIMESTAMP) //这个是日期加时间
    private Date creatTime;

    @Temporal(TemporalType.TIMESTAMP) //这个是日期加时间
    private Date updateTime;

    public ParentEntity() {
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


    @Override
    public String toString() {
        return "ParentEntity{" +
                "creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

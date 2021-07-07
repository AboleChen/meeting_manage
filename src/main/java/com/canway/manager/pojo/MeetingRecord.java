package com.canway.manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("meeting_record")
public class MeetingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("User")
    private String User;

    @TableField("begin")
    private Date begin;

    @TableField("end")
    private Date end;

    @TableField("room")
    private Integer room;

    @TableField("equipment")
    private Integer equipment;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public void setEquipment(Integer equipment) {
        this.equipment = equipment;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return User;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public Integer getRoom() {
        return room;
    }

    public Integer getEquipment() {
        return equipment;
    }

}

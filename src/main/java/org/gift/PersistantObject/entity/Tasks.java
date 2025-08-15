package org.gift.PersistantObject.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Tasks {
    private int id;
    private int user_id;
    private String title;
    private Date deadline;
    private String status;
    private int progress;
    private Date created_at;
    private Date updated_at;
}

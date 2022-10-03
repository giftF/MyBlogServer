package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ActionTrajectory {
    private int id;
    private String ip;
    private String ask;
    private Date createdate;
}

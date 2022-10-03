package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Leave {
    private Integer id;
    private String msg;
    private Integer status;
    private String ip;
    private Date updatedate;
    private Date createdate;
}

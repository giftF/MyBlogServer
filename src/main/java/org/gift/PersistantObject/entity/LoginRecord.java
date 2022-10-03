package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;
@Data
public class LoginRecord {
    private int id;
    private String ip;
    private String value;
    private int status;
    private Date createdate;
}

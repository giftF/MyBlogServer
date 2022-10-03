package org.gift.PersistantObject.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Login implements Serializable {
    private int id;
    private String users_id;
    private String ip;
    private String equipment;
    private Date createdate;
}

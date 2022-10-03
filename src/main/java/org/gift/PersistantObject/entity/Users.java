package org.gift.PersistantObject.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Users {
    private int id;
    private String username;
    private String password;
    private Date createdate;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String p5;
    private String commit;
}

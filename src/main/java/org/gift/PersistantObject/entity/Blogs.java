package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Blogs {
    private Integer id;
    private String title;
    private Integer top;
    private Integer status;
    private Integer classify;
    private String keyword;
    private String value;
    private Integer views;
    private Date updatedate;
    private Date createdate;
}

package org.gift.ValueObject.Blog;

import lombok.Data;

import java.util.Date;

@Data
public class ResContent {
    private String msg;
    private int status;
    private int id;
    private String title;
    private int top;
    private int classify;
    private String keyword;
    private String value;
    private int views;
    private Date updatedate;
    private Date createdate;
}

package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PhotoAlbum {
    private Integer id;
    private String title;
    private String front_cover;
    private Integer status;
    private Integer views;
    private String commit;
    private Integer top;
    private Date createdate;
}

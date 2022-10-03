package org.gift.PersistantObject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Photo {
    private Integer id;
    private Integer PhotoAlbum_id;
    private String title;
    private String url;
    private Integer status;
    private String commit;
    private Date createdate;
    private Integer index;
}

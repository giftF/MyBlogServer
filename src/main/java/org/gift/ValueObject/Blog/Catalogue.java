package org.gift.ValueObject.Blog;

import lombok.Data;
import org.gift.ValueObject.Response.CatalogueInterface;

import java.util.Date;

@Data
public class Catalogue implements CatalogueInterface {
    private Integer id;
    private String title;
    private Date createdate;
    private Integer views;
}

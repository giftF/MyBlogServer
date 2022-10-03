package org.gift.ValueObject.PhotoAlbum;

import lombok.Data;
import org.gift.ValueObject.Response.CatalogueInterface;

@Data
public class Catalogue implements CatalogueInterface {
    private Integer id;
    private String title;
    private Integer views;
    private String front_cover;
    private String commit;
}

package org.gift.ValueObject.Photo;

import lombok.Data;
import org.gift.ValueObject.Response.CatalogueInterface;

@Data
public class Catalogue implements CatalogueInterface {
    private Integer id;
    private String title;
    private String url;
    private String commit;
}

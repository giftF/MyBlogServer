package org.gift.PersistantObject.entity;

import lombok.Data;
import org.gift.ValueObject.Response.CatalogueInterface;

import java.util.Date;

@Data
public class Plans implements CatalogueInterface {
    private int id;
    private String message;
    private int tp;
    private Date createdate;
    private Date updatedate;
}

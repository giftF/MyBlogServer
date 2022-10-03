package org.gift.ValueObject.Response;

import lombok.Data;

import java.util.List;

@Data
public class ResList {
    private String msg;
    private Integer status;
    private List<? extends CatalogueInterface> list;
}

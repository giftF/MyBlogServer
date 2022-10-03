package org.gift.PersistantObject.entity;

import lombok.Data;

@Data
public class Deploy {
    private Integer id;
    private String classify;
    private String key;
    private String value;
    private String commit;
}

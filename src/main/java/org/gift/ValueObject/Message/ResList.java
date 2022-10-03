package org.gift.ValueObject.Message;

import lombok.Data;
import org.gift.PersistantObject.entity.Leave;

import java.util.List;

@Data
public class ResList {
    private String msg;
    private Integer status;
    private List<Leave> messagelist;
}

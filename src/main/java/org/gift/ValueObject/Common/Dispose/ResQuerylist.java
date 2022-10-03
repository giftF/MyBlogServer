package org.gift.ValueObject.Common.Dispose;

import lombok.Data;
import org.gift.PersistantObject.entity.Deploy;

import java.util.List;

@Data
public class ResQuerylist {
    private String msg;
    private int status;
    private List<Deploy> classifylist;
}

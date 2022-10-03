package org.gift.ValueObject.Common.Dispose;

import lombok.Data;
import org.gift.PersistantObject.entity.Deploy;

@Data
public class ResQuery {
    private int status;
    private String msg;
    private Deploy classify;
}

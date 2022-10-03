package org.gift.ValueObject.Message;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class ResStatistics {
    private String msg;
    private Integer status;
    private Integer count;
    private Integer unread;
}

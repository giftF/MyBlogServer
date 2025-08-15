package org.gift.ValueObject.Customer;

import lombok.Data;

@Data
public class ResLogin {
    private String msg;
    private int status;
    private String token;
    private String username;
    private String photo;

}

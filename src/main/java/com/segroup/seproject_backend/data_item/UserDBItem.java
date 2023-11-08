package com.segroup.seproject_backend.data_item;

import lombok.Data;

@Data
public class UserDBItem {
    private long user_id;
    private long verify_key;
    private String user_name;
    private String user_password;
}

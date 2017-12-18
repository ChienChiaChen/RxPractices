package com.chiachen.rxjavapractices.network.base_info;

/**
 * Created by jianjiacheng on 13/12/2017.
 */

public class UserBaseInfoResponse {
    private String name, id;

    public String getId() {
        return id;
    }

    public UserBaseInfoResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserBaseInfoResponse setName(String name) {
        this.name = name;
        return this;
    }
}

package com.chiachen.rxjavapractices;

/**
 * Created by jianjiacheng on 13/12/2017.
 */

class UserBaseInfoResponse {
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

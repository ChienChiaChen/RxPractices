package com.chiachen.rxjavapractices;

/**
 * Created by jianjiacheng on 12/12/2017.
 */

class LoginResponse {
    public String name;
    private String id;

    public String getName() {
        return name;
    }

    public LoginResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public LoginResponse setId(String id) {
        this.id = id;
        return this;
    }
}

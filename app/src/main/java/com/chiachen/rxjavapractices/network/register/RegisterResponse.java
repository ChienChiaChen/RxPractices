package com.chiachen.rxjavapractices.network.register;

/**
 * Created by jianjiacheng on 12/12/2017.
 */

public class RegisterResponse {
    private String name, id;

    public String getName() {
        return name;
    }

    public RegisterResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public RegisterResponse setId(String id) {
        this.id = id;
        return this;
    }
}

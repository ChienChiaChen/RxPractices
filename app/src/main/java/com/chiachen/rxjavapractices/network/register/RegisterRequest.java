package com.chiachen.rxjavapractices.network.register;

/**
 * Created by jianjiacheng on 12/12/2017.
 */

public class RegisterRequest {
    public String name;
    private int id;

    public String getName() {
        return name;
    }

    public RegisterRequest setName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public RegisterRequest setId(int id) {
        this.id = id;
        return this;
    }
}
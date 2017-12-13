package com.chiachen.rxjavapractices;

/**
 * Created by jianjiacheng on 13/12/2017.
 */

class UserExtraInfoResponse {
    private String age, id;

    public String getId() {
        return id;
    }

    public UserExtraInfoResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getAge() {
        return age;
    }

    public UserExtraInfoResponse setAge(String age) {
        this.age = age;
        return this;
    }
}

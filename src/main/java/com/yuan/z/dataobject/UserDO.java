package com.yuan.z.dataobject;

public class UserDO {
    private Integer id;

    private String name;

    private Integer age;

    private Integer gender;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;

    public UserDO(Integer id, String name, Integer age, Integer gender, String telphone, String registerMode, String thirdPartyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.telphone = telphone;
        this.registerMode = registerMode;
        this.thirdPartyId = thirdPartyId;
    }

    public UserDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode == null ? null : registerMode.trim();
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId == null ? null : thirdPartyId.trim();
    }
}
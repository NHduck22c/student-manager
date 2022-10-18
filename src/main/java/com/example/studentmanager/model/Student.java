package com.example.studentmanager.model;

public class Student {

    private String id;
    private String password;
    private String name;
    private String sex;
    private String school_date;
    private String major;
    private String email;
    private ClassInfo classInfo;
    private String avatar_url;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getSchool_date() {
        return school_date;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSchool_date(String school_date) {
        this.school_date = school_date;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String toString() {
        return
                this.id + ", " +
                        this.name + ", " +
                        this.email + ", " +
                        this.sex + ", " +
                        this.school_date + ", " +
                        this.major + ", " +
                        this.classInfo.getClassName() + "\n";
    }
}

package com.mxdl.customview.entity;

/**
 * Description: <Student><br>
 * Author:      mxdl<br>
 * Date:        2019/10/7<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Student {
    private String name;
    private int age;
    private String address;

    public Student(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

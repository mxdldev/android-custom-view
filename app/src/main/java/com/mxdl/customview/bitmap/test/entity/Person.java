package com.mxdl.customview.bitmap.test.entity;

import androidx.annotation.NonNull;

/**
 * Description: <Person><br>
 * Author:      mxdl<br>
 * Date:        2019/11/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Person {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}

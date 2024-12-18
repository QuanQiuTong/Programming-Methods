package com.project1;

public class Main {
    public static void main(String[] args) {
        System.out.flush();

        MyString myString = new MyString("Hello, World!".toCharArray());

        System.out.println(myString.length());

        System.out.println(myString.getValue());

        MyString ab = myString.concat("World".toCharArray());
        System.out.println(ab.getValue());

        System.out.println(myString.indexOf("World".toCharArray())); // 7
        System.out.println(myString.indexOf("Java".toCharArray())); // -1

        MyString r = ab.replace("World".toCharArray(), "Java".toCharArray());
        System.out.println(r.getValue());

        MyString q = myString.replace("ll".toCharArray(), "LL".toCharArray());
        System.out.println(q.getValue());
    }
}
package ru.coutvv.vkliker.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author coutvv
 */
public class Trash {

    public static void main(String[] args) {
        List<MyType> list = new ArrayList<>();
        list.addAll(Arrays.asList(new MyType(2,3),new MyType(1,2), new MyType(12,42)));
        list.sort((o1, o2) -> {

            return -o1.a + o2.a;
        });
        System.out.println(list);

    }
}

class MyType {
    int a;
    int b;

    public MyType(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public String toString() {
        return "[" + a + "," + b + "]";
    }
}
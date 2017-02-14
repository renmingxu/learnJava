package com.renmingxu.test.main;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renmingxu on 17-2-9.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        class TestObject extends Object {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }
        TestObject t = new TestObject();
        System.out.println(t.hashCode());
        System.out.println("asdfasdf".hashCode());
    }


}

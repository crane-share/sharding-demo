package com.crane.sharding.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author syhe
 * @Title: NormalTest
 * @ProjectName sharding-demo
 * @Description: TODO 普通验证
 * @date 2019-11-2209:39
 */
public class NormalTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("crane0");
        list.add("crane1");
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
    }
}

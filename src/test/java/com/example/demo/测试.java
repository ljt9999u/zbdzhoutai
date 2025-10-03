package com.example.demo;

import com.example.demo.utils.Md5Util;

public class 测试 {
    public static void main(String[] args) { // 需要 main 方法作为入口
        String password = "123456789";
        String md5 = Md5Util.getMD5String(password);
        System.out.println("加密结果长度：" + md5.length()); // 应为32
        System.out.println("加密结果：" + md5);
    }
}

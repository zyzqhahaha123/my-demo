package com.myproject.myblog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: my-blog
 * @description: MD5加密
 * @author: zhan
 * @create: 2020-02-26 15:46
 */
public class MD5Uitls {

    /**
     *
     * @param str 需要被加密的数字
     * @return 加密后的数字
     */
    public static String code(String str){

        try {
            //1.获取MessageDigest对象
            MessageDigest digest = MessageDigest.getInstance("md5");

            //2.执行加密操作
            byte[] bytes = str.getBytes();

            //在MD5算法这，得到的目标字节数组的特点：长度固定为16
            byte[] targetBytes = digest.digest(bytes);

            //3.声明字符数组
            char[] characters = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

            //4.遍历targetBytes
            StringBuilder builder = new StringBuilder();
            for (byte b : targetBytes) {
                //5.取出b的高四位的值
                //先把高四位通过右移操作拽到低四位
                int high = (b >> 4) & 15;

                //6.取出b的低四位的值
                int low = b & 15;

                //7.以high为下标从characters中取出对应的十六进制字符
                char highChar = characters[high];

                //8.以low为下标从characters中取出对应的十六进制字符
                char lowChar = characters[low];

                builder.append(highChar).append(lowChar);
            }
            System.out.println("加密后的密码:" + builder.toString());
            return builder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}

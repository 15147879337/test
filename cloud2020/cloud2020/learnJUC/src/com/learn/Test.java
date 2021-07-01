package com.learn;

public class Test {
    public static void main(String[] args) {
        String str = "AMT_YM= '202106' and SEND_STATUS != '1'";
        System.out.println(str.indexOf("'"));
        System.out.println(str.substring(str.indexOf("'")+1,15));
    }
}

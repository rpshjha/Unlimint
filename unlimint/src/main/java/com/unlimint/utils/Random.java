package com.unlimint.utils;


public class Random {

    private Random() {
    }

    public static String getAlphaNumericString(int n) {

        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static int getRandomInt(int length) {
        int bound = 9;
        int multiplyBy = 1;
        for (int i = 1; i < length; i++) {
            bound = bound * 10;
            multiplyBy = multiplyBy * 10;
        }
        return multiplyBy + new java.util.Random().nextInt(bound);
    }
}

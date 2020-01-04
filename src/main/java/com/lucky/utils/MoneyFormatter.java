package com.lucky.utils;

public class MoneyFormatter {

    public static String format(Double value) {
        String[] suffix = new String[]{"k", "m", "b", "t", "q", "qq", "s", "ss", "o", "n", "d"};
        int size = (value != 0) ? (int) Math.log10(value) : 0;
        if (size >= 3) {
            while ((size%3) > 0) {
                size -= 1;
            }
        }

        double notation = Math.pow(10, size);
        return (size >= 3) ? + (Math.round((value / notation) * 100) / 100.0d)+suffix[(size/3) - 1] : + value + "";
    }
}

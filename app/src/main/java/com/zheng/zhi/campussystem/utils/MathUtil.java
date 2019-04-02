package com.zheng.zhi.campussystem.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class MathUtil {

    private static int decimalsAccuracy = 15;

    private static double add(String a1, String a2) {
        return getDoubleValue(a1) + getDoubleValue(a2);
    }

    private static double sub(String a1, String a2) {
        return getDoubleValue(a1) - getDoubleValue(a2);
    }

    private static double pun(String a1, String a2) {
        return getDoubleValue(a1) * getDoubleValue(a2);
    }

    private static double div(String a1, String a2) {
        return getDoubleValue(a1) / getDoubleValue(a2);
    }

    private static String addBig(String a1, String a2) {
        return (new BigDecimal(a1).add(new BigDecimal(a2))).stripTrailingZeros().toPlainString();
    }

    private static String subBig(String a1, String a2) {
        return (new BigDecimal(a1).subtract(new BigDecimal(a2))).stripTrailingZeros().toPlainString();
    }

    private static String punBig(String a1, String a2) {
        return (new BigDecimal(a1).multiply(new BigDecimal(a2))).stripTrailingZeros().toPlainString();
    }

    public static String divBig(String a1, String a2) {
        return (new BigDecimal(a1).divide(new BigDecimal(a2), decimalsAccuracy, ROUND_HALF_DOWN)).stripTrailingZeros().toPlainString();
    }

    private static double getDoubleValue(String str) {
        double result = 0;
        if (str.contains(".")) {
            String before = str.substring(0, str.indexOf("."));
            double beforeDouble = getDoubleValue(before);
            String after = str.substring(str.indexOf(".") + 1);
            double afterDouble = getDoubleValue(after) / (Math.pow(10, after.length()));
            result = beforeDouble + afterDouble;
        } else {
            int times = 1;
            for (int i = str.length() - 1; i >= 0; i--) {
                char number = str.charAt(i);
                result = result + (number - 48) * times;
                times = times * 10;
            }
        }
        return result;
    }

    public static String evalBigNumber(String str) {
        boolean isFirstsub = false;
        if (!str.contains("+") && !str.contains("-") && !str.contains("*") && !str.contains("/")) {
            return str;
        }
        if (str.charAt(0) == '-') {
            isFirstsub = true;
            str = str.substring(1);
            if (!str.contains("+") && !str.contains("-") && !str.contains("*") && !str.contains("/")) {
                return "-" + str;
            }
        }

        List<String> numbers = new ArrayList<>();
        List<String> operationals = new ArrayList<>();
        int sum = getOperationals(str);
        for (int i = 0; i < sum; i++) {
            int position = getNumberStr(str);
            numbers.add(str.substring(0, position));
            if (i == 0 && isFirstsub) {
                numbers.set(0, "-" + numbers.get(i));
            }
            operationals.add(str.substring(position, position + 1));
            str = str.substring(position + 1);
        }
        numbers.add(str);

        for (int i = 0; i < operationals.size(); i++) {
            if (operationals.get(i).equals("*")) {
                numbers.set(i, punBig(numbers.get(i), numbers.get(i + 1)));
                numbers.remove(i + 1);
                operationals.remove(i);
                i--;
            } else if (operationals.get(i).equals("/")) {
                numbers.set(i, divBig(numbers.get(i), numbers.get(i + 1)));
                numbers.remove(i + 1);
                operationals.remove(i);
                i--;
            }
        }

        for (int i = 0; i < operationals.size(); i++) {
            if (operationals.get(i).equals("+")) {
                numbers.set(i, addBig(numbers.get(i), numbers.get(i + 1)));
                numbers.remove(i + 1);
                operationals.remove(i);
                i--;
            } else if (operationals.get(i).equals("-")) {
                numbers.set(i, subBig(numbers.get(i), numbers.get(i + 1)));
                numbers.remove(i + 1);
                operationals.remove(i);
                i--;
            }
        }
        return removeInvalidZero(numbers.get(0));
    }

    public static String removeInvalidZero(String number) {
        if (number.length() == 1) return number;
        if (zeroCount(number) == number.length() - 1 && number.contains(".")) {
            number = "0";
        }
        while (number.charAt(0) == '0' && number.length() > 1 && number.charAt(1) != '.') {
            number = number.substring(1);
        }
        if (!number.contains(".")) return number;
        while (number.charAt(number.length() - 1) == '0') {
            number = number.substring(0, number.length() - 1);
        }
        return number.endsWith(".") ? number.substring(0, number.length() - 1) : number;
    }

    private static int zeroCount(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') {
                num++;
            }
        }
        return num;
    }

    private static int getOperationals(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 48 && c <= 57) && c != '.') {
                sum++;
            }
        }
        return sum;
    }

    private static List<Integer> getOperationalsPosition(String str) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 48 && c <= 57) && c != '.') {
                integers.add(i);
            }
        }
        return integers;
    }

    public static boolean isDivisorZero(String str) {
        boolean result = false;
        List<Integer> integers = getOperationalsPosition(str);
        for (int i = 0; i < integers.size(); i++) {
            if (str.charAt(integers.get(i)) == '/') {
                String number = str.substring(integers.get(i) + 1, i + 1
                        >= integers.size() ? str.length() : integers.get(i + 1));
                if (zeroCount(number) == number.length() ||
                        (zeroCount(number) == number.length() - 1 && number.contains("."))) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private static int getNumberStr(String str) {
        int position = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c >= 48 && c <= 57) && c != '.') {
                position = i;
                break;
            }
        }
        return position;
    }

    public static boolean isOperationalsChar(char c) {
        return c == 'C' || c == '/' || c == '*' || c == 'D'
                || c == '-' || c == '+' || c == '%' || c == '.';
    }

    public static boolean isOperationals(char c) {
        return c == '/' || c == '*'
                || c == '-' || c == '+' ;
    }

}

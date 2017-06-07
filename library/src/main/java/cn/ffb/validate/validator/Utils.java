package cn.ffb.validate.validator;

import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lingfei on 2017/6/7.
 */

public class Utils {
    /**
     * 验证是否为空
     */
    public static boolean isEmpty(String text) {
        return text == null || TextUtils.getTrimmedLength(text) <= 0;
    }
    /**
     * 手机验证
     */

    public static boolean isPhone(String phoneNum) {
        return isPhone(null, phoneNum);
    }

    /**
     * 手机验证
     * regex 自定义的正则
     * phoneNum 手机号
     * @return
     */
    public static boolean isPhone(String regex, String phoneNum) {
        if (regex == null) {
            regex = "^1[3|4|5|8][0-9]\\d{4,8}$";
        }
        return regex(regex, phoneNum);
    }

    /**
     * email验证
     */
    public static boolean isEmail(String email) {
        return isEmail(null, email);

    }

    /**
     * email验证
     */
    public static boolean isEmail(String regex, String email) {
        if (regex == null) {
            regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        }
        return regex(regex, email);
    }

    /**
     * 字符串是否相等
     */
    public static boolean equalsString(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 最小只验证
     */
    public static boolean minValue(String text, int value) {
        try {
            int _value = Integer.valueOf(text);
            return _value >= value;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 最大值验证
     */
    public static boolean maxValue(String text, int value) {
        try {
            int _value = Integer.valueOf(text);
            return _value <= value;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 最小长度验证
     */
    public static boolean minLength(String text, int length) {
        return text.length() >= length;
    }

    /**
     * 最大长度验证
     */
    public static boolean maxLength(String text, int length) {
        return text.length() <= length;
    }


    /**
     * 正则验证
     */
    public static boolean regex(String regex, String text) {
        if (isEmpty(text)) {
            return true;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        return m.find();
    }

}

package cn.ffb.validate;

/**
 * 验证的类型
 * Created by lingfei on 2017/6/7.
 */

public class ValidateType {
    static final int EMPTY = -600; // 是否为空
    static final int EMAIL = -601; // 邮箱
    static final int PHONE = -602; // 手机号
    static final int REGEX = -603; // 正则表达式
    static final int MAX_LENGTH = -604; // 最大长度
    static final int MIN_LENGTH = -605; // 最小长度
    static final int MAX_VALUE = -606; // 最大值
    static final int MIN_VALUE = -607; // 最小值
    static final int REPASSWORD = -608; // edittext内容相等
}

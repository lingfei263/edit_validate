package cn.ffb.validate;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingfei on 2017/6/7.
 */

public class ValidateItem {
    private int type;
    private String message;
    private Map<String, Object> extras = new HashMap<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    public final static String EXTRA_VALUE = "value";
    public final static String EXTRA_LENGTH = "length";
    public final static String EXTRA_TEXT = "text";
    public final static String EXTRA_REGEX = "regex";
    public final static String EXTRA_EDITTEXT = "edittext";

    static ValidateItem createValueItem(int type, String message, int value) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras.put(EXTRA_VALUE, value);
        return item;
    }

    static ValidateItem createLengthItem(int type, String message, int length) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras.put(EXTRA_LENGTH, length);
        return item;
    }

    static ValidateItem createEqualsItem(int type, String message,
                                                         EditText editText) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras.put(EXTRA_EDITTEXT, editText);
        return item;
    }


    static ValidateItem createEqualsItem(int type, String message, String text) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras.put(EXTRA_TEXT, text);
        return item;
    }

    static ValidateItem createRegexItem(int type, String message, String regex) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras.put(EXTRA_REGEX, regex);
        return item;
    }

    static ValidateItem createItem(int type, String message) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        return item;
    }

    static ValidateItem createItem(int type, String message, Map<String, Object> extras) {
        ValidateItem item = new ValidateItem();
        item.type = type;
        item.message = message;
        item.extras = extras;
        return item;
    }

    public static ValidateItem createRequiredItem(String message) {
        return createRegexItem(ValidateType.EMPTY, message, "");
    }

    public static ValidateItem createEmailItem(String message) {
        return createRegexItem(ValidateType.EMAIL, message, "");
    }

    public static ValidateItem createPhoneItem(String message) {
        return createRegexItem(ValidateType.PHONE, message, "");
    }
}

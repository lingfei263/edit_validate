package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.ffb.validate.validator.Utils.isEmail;

/**
 * Created by lingfei on 2017/6/7.
 */

public class EmailValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!isEmail(text)) {
            return false;
        }
        return true;
    }
}

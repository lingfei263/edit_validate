package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

/**
 * Created by lingfei on 2017/6/7.
 */

public interface IValidator {
    boolean validate(int validateType, EditText editText, String text,
                     Map<String, Object> extras);
}

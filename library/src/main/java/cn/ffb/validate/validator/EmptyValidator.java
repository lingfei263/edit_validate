package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import static cn.ffb.validate.validator.Utils.isEmpty;

/**
 * Created by lingfei on 2017/6/7.
 */

public class EmptyValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (isEmpty(text)) {
            return false;
        }
        return true;
    }


}

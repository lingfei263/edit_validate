package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import static cn.ffb.validate.validator.Utils.isPhone;

/**
 * Created by Administrator on 2017/6/7.
 */

public class PhoneValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!isPhone(text)) {
            return false;
        }
        return true;
    }
}

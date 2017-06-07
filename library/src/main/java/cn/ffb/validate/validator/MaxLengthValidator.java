package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import cn.ffb.validate.ValidateManager;

import static cn.ffb.validate.validator.Utils.maxLength;

/**
 * Created by lingfei on 2017/6/7.
 */

public class MaxLengthValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!maxLength(text, (int) extras.get(ValidateManager.ValidateItem.EXTRA_LENGTH))) {
            return false;
        }
        return true;
    }
}

package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import cn.ffb.validate.ValidateManager;

import static cn.ffb.validate.validator.Utils.minValue;

/**
 * Created by lingfei on 2017/6/7.
 */

public class MinValueValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!minValue(text, (int) extras.get(ValidateManager.ValidateItem.EXTRA_VALUE))) {
            return false;
        }
        return true;
    }
}

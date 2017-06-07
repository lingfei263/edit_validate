package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import cn.ffb.validate.ValidateItem;

import static cn.ffb.validate.validator.Utils.minLength;

/**
 * Created by lingfei on 2017/6/7.
 */

public class MinLengthValidator implements IValidator{
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!minLength(text, (int) extras.get(ValidateItem.EXTRA_LENGTH))) {
            return false;
        }
        return true;
    }
}

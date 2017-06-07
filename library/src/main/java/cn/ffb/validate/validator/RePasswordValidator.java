package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import cn.ffb.validate.ValidateManager;

import static cn.ffb.validate.validator.Utils.equalsString;

/**
 * Created by lingfei on 2017/6/7.
 */

public class RePasswordValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!equalsString(text,
                ((EditText) extras.get(ValidateManager.ValidateItem.EXTRA_EDITTEXT)).getText().toString())) {
            return false;
        }
        return true;
    }
}

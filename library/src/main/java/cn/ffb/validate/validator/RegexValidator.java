package cn.ffb.validate.validator;

import android.widget.EditText;

import java.util.Map;

import cn.ffb.validate.ValidateItem;
import cn.ffb.validate.ValidateManager;

import static cn.ffb.validate.validator.Utils.regex;

/**
 * Created by lingfei on 2017/6/7.
 */

public class RegexValidator implements IValidator {
    @Override
    public boolean validate(int validateType, EditText editText, String text, Map<String, Object> extras) {
        if (!regex((String) extras.get(ValidateItem.EXTRA_REGEX), text)) {
            return false;
        }
        return true;
    }
}

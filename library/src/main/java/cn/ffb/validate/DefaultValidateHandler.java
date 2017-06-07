package cn.ffb.validate;

import android.widget.EditText;

/**
 * Created by lingfei on 2017/6/7.
 */

public class DefaultValidateHandler implements IValidateHandler {
    @Override
    public void onValidateHandler(EditText editText, String validateMessage) {
        editText.setError(validateMessage);
    }
}

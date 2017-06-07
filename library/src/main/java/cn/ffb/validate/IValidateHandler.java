package cn.ffb.validate;

import android.widget.EditText;

/**
 * Created by lingfei on 2017/6/7.
 */

public interface IValidateHandler {
    void onValidateHandler(EditText editText, String validateMessage);
}

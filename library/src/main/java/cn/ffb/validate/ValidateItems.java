package cn.ffb.validate;

import android.widget.EditText;

import java.util.List;

/**
 * Created by lingfei on 2017/6/7.
 */

public class ValidateItems {
    private EditText editText;
    private List<ValidateItem> validateItems;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public List<ValidateItem> getValidateItems() {
        return validateItems;
    }

    public void setValidateItems(List<ValidateItem> validateItems) {
        this.validateItems = validateItems;
    }
}

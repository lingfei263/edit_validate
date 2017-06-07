package cn.ffb.validate;

import android.widget.EditText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.ffb.validate.annotation.Email;
import cn.ffb.validate.annotation.Empty;
import cn.ffb.validate.annotation.EqualsString;
import cn.ffb.validate.annotation.Length;
import cn.ffb.validate.annotation.MaxLength;
import cn.ffb.validate.annotation.MaxValue;
import cn.ffb.validate.annotation.MinLength;
import cn.ffb.validate.annotation.MinValue;
import cn.ffb.validate.annotation.Phone;
import cn.ffb.validate.annotation.RePassword;
import cn.ffb.validate.annotation.Regex;
import cn.ffb.validate.annotation.Value;
import cn.ffb.validate.validator.EmailValidator;
import cn.ffb.validate.validator.EmptyValidator;
import cn.ffb.validate.validator.IValidator;
import cn.ffb.validate.validator.MaxLengthValidator;
import cn.ffb.validate.validator.MaxValueValidator;
import cn.ffb.validate.validator.MinLengthValidator;
import cn.ffb.validate.validator.MinValueValidator;
import cn.ffb.validate.validator.PhoneValidator;
import cn.ffb.validate.validator.RePasswordValidator;
import cn.ffb.validate.validator.RegexValidator;

/**
 * 表单验证管理器
 *
 * @author lingfei 2017-6-6
 */
public class ValidateManager {
    private Map<EditText, ValidateItems> validates;
    private static IValidateHandler mValidateHandler = new DefaultValidateHandler();

    private static Map<Integer, IValidator> validators = new HashMap<>();

    public ValidateManager() {
        validates = new LinkedHashMap<>();
    }

    public static void setValidateHandler(IValidateHandler validateHandler) {
        mValidateHandler = validateHandler;
    }


    private ValidateManager addValidateItem(EditText editText, ValidateItem item) {
        ValidateItems items = validates.get(editText);
        if (items == null) {
            items = new ValidateItems();
            items.setEditText(editText);
            items.setValidateItems(new ArrayList<ValidateItem>());
            validates.put(editText, items);
        }
        items.getValidateItems().add(item);
        return this;
    }


    private ValidateManager addValidateEmptyItem(EditText editText, String validateMessage) {
        addValidateItem(editText, ValidateType.EMPTY, validateMessage);
        return this;
    }

    private ValidateManager addValidateEmailItem(EditText editText, String validateMessage) {
        addValidateItem(editText, ValidateType.EMAIL, validateMessage);
        return this;
    }

    private ValidateManager addValidatePhoneItem(EditText editText, String validateMessage) {
        addValidateItem(editText, ValidateType.PHONE, validateMessage);
        return this;
    }

    private ValidateManager addValidateMinLengthItem(EditText editText, String validateMessage,
                                                     int length) {
        addValidateItem(editText,
                ValidateItem.createLengthItem(ValidateType.MIN_LENGTH, validateMessage, length));
        return this;
    }

    private ValidateManager addValidateMaxLengthItem(EditText editText, String validateMessage,
                                                     int value) {
        addValidateItem(editText,
                ValidateItem.createLengthItem(ValidateType.MAX_LENGTH, validateMessage, value));
        return this;
    }

    private ValidateManager addValidateMinValueItem(EditText editText, String validateMessage,
                                                    int value) {
        addValidateItem(editText,
                ValidateItem.createValueItem(ValidateType.MIN_VALUE, validateMessage, value));
        return this;
    }


    private ValidateManager addValidateMaxValueItem(EditText editText, String validateMessage,
                                                    int value) {
        addValidateItem(editText,
                ValidateItem.createValueItem(ValidateType.MAX_VALUE, validateMessage, value));
        return this;
    }

    private ValidateManager addValidateEqualsItem(EditText editText1, String validateMessage,
                                                  EditText editText2) {
        addValidateItem(editText1,
                ValidateItem.createEqualsItem(ValidateType.REPASSWORD, validateMessage, editText2));
        return this;
    }

    private ValidateManager addValidateEqualsItem(EditText editText, String validateMessage,
                                                  String text) {
        addValidateItem(editText,
                ValidateItem.createEqualsItem(ValidateType.REPASSWORD, validateMessage, text));
        return this;
    }

    private ValidateManager addValidateRegexItem(EditText editText, String validateRegex,
                                                 String validateMessage) {
        addValidateItem(editText, ValidateItem.createRegexItem(ValidateType.REGEX, validateMessage, validateRegex));
        return this;
    }


    /**
     * 设置验证项
     *
     * @param editText
     * @param type
     * @param validateMessage
     */
    private ValidateManager addValidateItem(EditText editText, int type,
                                           String validateMessage) {
        addValidateItem(editText, ValidateItem.createItem(type, validateMessage));
        return this;
    }

    private ValidateManager addValidateItem(EditText editText, int type,
                                           String validateMessage, Map<String, Object> extras) {
        addValidateItem(editText, ValidateItem.createItem(type, validateMessage, extras));
        return this;
    }

    /**
     * 清空验证器
     */
    public void clear() {
        this.validates.clear();
    }

    //验证失败的EditText获取焦点
    private void requestFocus(EditText editText) {
        editText.requestFocus();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    static {
        validators.put(ValidateType.EMPTY, new EmptyValidator());
        validators.put(ValidateType.EMAIL, new EmailValidator());
        validators.put(ValidateType.REPASSWORD, new RePasswordValidator());
        validators.put(ValidateType.MAX_LENGTH, new MaxLengthValidator());
        validators.put(ValidateType.MAX_VALUE, new MaxValueValidator());
        validators.put(ValidateType.MIN_LENGTH, new MinLengthValidator());
        validators.put(ValidateType.MIN_VALUE, new MinValueValidator());
        validators.put(ValidateType.PHONE, new PhoneValidator());
        validators.put(ValidateType.REGEX, new RegexValidator());
    }

//    public static void addCustomValidator(int validateType, IValidator validator) {
//        validators.put(validateType, validator);
//    }


    public static boolean validate(Object target) {
        ValidateManager manager = new ValidateManager();
        try {
            Class<?> clazz = target.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(target);
                if (o instanceof EditText) {
                    EditText editText = (EditText) o;
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Email) {
                            Email email = (Email) annotation;
                            String message = email.message();
                            manager.addValidateEmailItem(editText, message);
                        } else if (annotation instanceof EqualsString) {
                            EqualsString equals = (EqualsString) annotation;
                            String message = equals.message();
                            String stringName = equals.string();
                            Field stringField = clazz.getDeclaredField(stringName);
                            stringField.setAccessible(true);
                            String stringValue = (String) stringField.get(target);
                            manager.addValidateEqualsItem(editText, message, stringValue);
                        } else if (annotation instanceof RePassword) {
                            RePassword repassword = (RePassword) annotation;
                            String message = repassword.message();
                            String edittextName = repassword.editext();
                            Field edittextField = clazz.getDeclaredField(edittextName);
                            edittextField.setAccessible(true);
                            EditText editext = (EditText) edittextField.get(target);
                            manager.addValidateEqualsItem(editText, message, editext);
                        } else if (annotation instanceof Length) {
                            Length length = (Length) annotation;
                            String message = length.message();
                            int maxLength = length.maxLength();
                            int minLength = length.minLength();
                            manager.addValidateMinLengthItem(editText, message, minLength);
                            manager.addValidateMaxLengthItem(editText, message, maxLength);
                        } else if (annotation instanceof Phone) {
                            Phone phone = (Phone) annotation;
                            String message = phone.message();
                            manager.addValidatePhoneItem(editText, message);
                        } else if (annotation instanceof Regex) {
                            Regex regex = (Regex) annotation;
                            String message = regex.message();
                            String value = regex.regex();
                            manager.addValidateRegexItem(editText, value, message);
                        } else if (annotation instanceof Empty) {
                            Empty empty = (Empty) annotation;
                            String message = empty.message();
                            manager.addValidateEmptyItem(editText, message);
                        } else if (annotation instanceof Value) {
                            Value value = (Value) annotation;
                            String message = value.message();
                            int minValue = value.minValue();
                            int maxValue = value.maxValue();
                            manager.addValidateMinValueItem(editText, message, minValue);
                            manager.addValidateMaxValueItem(editText, message, maxValue);
                        } else if (annotation instanceof MaxLength) {
                            MaxLength length = (MaxLength) annotation;
                            String message = length.message();
                            int maxLength = length.maxLength();
                            manager.addValidateMaxLengthItem(editText, message, maxLength);
                        } else if (annotation instanceof MinLength) {
                            MinLength length = (MinLength) annotation;
                            String message = length.message();
                            int minLength = length.minLength();
                            manager.addValidateMinLengthItem(editText, message, minLength);
                        } else if (annotation instanceof MaxValue) {
                            MaxValue value = (MaxValue) annotation;
                            String message = value.message();
                            int maxValue = value.maxValue();
                            manager.addValidateMaxValueItem(editText, message, maxValue);
                        } else if (annotation instanceof MinValue) {
                            MinValue value = (MinValue) annotation;
                            String message = value.message();
                            int minValue = value.minValue();
                            manager.addValidateMinValueItem(editText, message, minValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return manager.validate();
    }

    private boolean validate(EditText editText) {
        boolean v = true;
        ValidateItems items = validates.get(editText);
        List<ValidateItem> list = items.getValidateItems();
        for (int i = 0; i < list.size(); i++) {
            ValidateItem item = list.get(i);
            int type = item.getType();
            String validateMessage = item.getMessage();
            String text = editText.getText().toString();

            IValidator validator = validators.get(type);
            if (!validator.validate(type, editText, text, item.getExtras())) {
                mValidateHandler.onValidateHandler(editText, validateMessage);
                requestFocus(editText);
                v = false;
            }
        }

        return v;
    }

    /**
     * 验证
     */
    private boolean validate() {
        boolean v = true;
        for (Map.Entry<EditText, ValidateItems> entry : validates.entrySet()) {
            v = v & validate(entry.getKey());
            if (!v) {
                break;
            }
        }
        return v;
    }


}

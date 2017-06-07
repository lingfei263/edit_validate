package cn.ffb.validate;

import android.widget.EditText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public interface IValidateHandler {
        void onValidateHandler(EditText editText, String validateMessage);
    }


    public static void setValidateHandler(IValidateHandler validateHandler) {
        mValidateHandler = validateHandler;
    }

    private static class DefaultValidateHandler implements IValidateHandler {

        @Override
        public void onValidateHandler(EditText editText, String validateMessage) {
            editText.setError(validateMessage);
        }

    }

    public static class ValidateItem {
        private int type;
        public String message;
        private Map<String, Object> extras = new HashMap<>();

        public final static String EXTRA_VALUE = "value";
        public final static String EXTRA_LENGTH = "length";
        public final static String EXTRA_TEXT = "text";
        public final static String EXTRA_REGEX = "regex";
        public final static String EXTRA_EDITTEXT = "edittext";

        static ValidateItem createValueItem(int type, String message, int value) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras.put(EXTRA_VALUE, value);
            return item;
        }

        static ValidateItem createLengthItem(int type, String message, int length) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras.put(EXTRA_LENGTH, length);
            return item;
        }

        static ValidateItem createEqualsItem(int type, String message,
                                             EditText editText) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras.put(EXTRA_EDITTEXT, editText);
            return item;
        }


        static ValidateItem createEqualsItem(int type, String message, String text) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras.put(EXTRA_TEXT, text);
            return item;
        }

        static ValidateItem createRegexItem(int type, String message, String regex) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras.put(EXTRA_REGEX, regex);
            return item;
        }

        static ValidateItem createItem(int type, String message) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            return item;
        }

        static ValidateItem createItem(int type, String message, Map<String, Object> extras) {
            ValidateItem item = new ValidateItem();
            item.type = type;
            item.message = message;
            item.extras = extras;
            return item;
        }

        public static ValidateItem createRequiredItem(String message) {
            return createRegexItem(ValidateType.EMPTY, message, "");
        }

        public static ValidateItem createEmailItem(String message) {
            return createRegexItem(ValidateType.EMAIL, message, "");
        }

        public static ValidateItem createPhoneItem(String message) {
            return createRegexItem(ValidateType.PHONE, message, "");
        }
    }

    private static class ValidateItems {
        private EditText editText;
        private List<ValidateItem> validateItems;
    }

    private ValidateManager addValidateItem(EditText editText, ValidateItem item) {
        ValidateItems items = validates.get(editText);
        if (items == null) {
            items = new ValidateItems();
            items.editText = editText;
            items.validateItems = new ArrayList<>();
            validates.put(editText, items);
        }
        items.validateItems.add(item);
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
    public ValidateManager addValidateItem(EditText editText, int type,
                                           String validateMessage) {
        addValidateItem(editText, ValidateItem.createItem(type, validateMessage));
        return this;
    }

    public ValidateManager addValidateItem(EditText editText, int type,
                                           String validateMessage, Map<String, Object> extras) {
        addValidateItem(editText, ValidateItem.createItem(type, validateMessage, extras));
        return this;
    }



    /**
     * 验证类型
     */
    private interface ValidateType {
        int EMPTY = -100; // 是否为空
        int EMAIL = -101; // 邮箱
        int PHONE = -102; // 手机号
        int REGEX = -103; // 正则表达式
        int MAX_LENGTH = -104; // 最大长度
        int MIN_LENGTH = -105; // 最小长度
        int MAX_VALUE = -106; // 最大值
        int MIN_VALUE = -107; // 最小值
        int REPASSWORD = -108; // edittext内容相等
    }

    public void clear() {
        this.validates.clear();
    }

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

    public static void register(int validateType, IValidator validator) {
        validators.put(validateType, validator);
    }


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
                        }  else if (annotation instanceof EqualsString) {
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

    public boolean validate(EditText editText) {
        boolean v = true;
        ValidateItems items = validates.get(editText);
        List<ValidateItem> list = items.validateItems;
        for (int i = 0; i < list.size(); i++) {
            ValidateItem item = list.get(i);
            int type = item.type;
            String validateMessage = item.message;
            String text = editText.getText().toString();

            IValidator validator = validators.get(type);
            if (!validator.validate(type, editText, text, item.extras)) {
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
    public boolean validate() {
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

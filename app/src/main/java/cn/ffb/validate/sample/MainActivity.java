package cn.ffb.validate.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.ffb.validate.IValidateHandler;
import cn.ffb.validate.ValidateManager;
import cn.ffb.validate.annotation.Email;
import cn.ffb.validate.annotation.Empty;
import cn.ffb.validate.annotation.MaxLength;
import cn.ffb.validate.annotation.MaxValue;
import cn.ffb.validate.annotation.MinLength;
import cn.ffb.validate.annotation.MinValue;
import cn.ffb.validate.annotation.Phone;
import cn.ffb.validate.annotation.RePassword;
import cn.ffb.validate.annotation.Regex;


public class MainActivity extends AppCompatActivity {
    private ValidateManager validateManager = new ValidateManager();

    static {
        ValidateManager.setValidateHandler(new IValidateHandler() {
            @Override
            public void onValidateHandler(EditText editText, String validateMessage) {
                editText.setError(validateMessage);
            }
        });
    }


    @Empty(message = "该项为必填项，不可为空")
    @MaxLength(message = "该项的长度不可超过5个字符", maxLength = 5)
    private EditText editText1;
    @Email(message = "请输入正确的邮箱")
    private EditText editText2;
    @Phone(message = "请输入正确的手机号")
    private EditText editText3;
    @Regex(regex = "^[1-9]\\d*$", message = "正则表达式不匹配（整数）")
    private EditText editText4;
    @MaxLength(message = "该项的长度不可超过5个字符", maxLength = 5)
    private EditText editText5;
    @MinLength(message = "该项的长度不可低于5个字符", minLength = 5)
    private EditText editText6;
    @MaxValue(message = "该项的值不可超过100", maxValue = 100)
    private EditText editText7;
    @MinValue(message = "该项的值不可少于100", minValue = 100)
    private EditText editText8;
    @RePassword(editext = "editText8", message = "该项的值和上面的editText的内容不相等")
    private EditText editText9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText) this.findViewById(R.id.editText1);
        editText2 = (EditText) this.findViewById(R.id.editText2);
        editText3 = (EditText) this.findViewById(R.id.editText3);
        editText4 = (EditText) this.findViewById(R.id.editText4);
        editText5 = (EditText) this.findViewById(R.id.editText5);
        editText6 = (EditText) this.findViewById(R.id.editText6);
        editText7 = (EditText) this.findViewById(R.id.editText7);
        editText8 = (EditText) this.findViewById(R.id.editText8);
        editText9 = (EditText) this.findViewById(R.id.editText9);
        Button validateButton = (Button) this.findViewById(R.id.button);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateManager.validate(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "通过了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "没通过", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

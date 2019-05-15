package com.ape.bananarecharge.Login;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ape.bananarecharge.Adapter.CountryIdAdapter;
import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.MyReceiver;
import com.ape.bananarecharge.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import Util.MapUtil;
import Util.URLUtils;

public class LoginActivity extends AppCompatActivity {
    private Spinner mCountryCode;
    //    private TextView mCountryCode;
    private AutoCompleteTextView mPhoneNum;
    private EditText mVertificationCode;
    private TextView mButton;
    private Button mLoginButton;
    private Map<String, String> mUsrInfoMap;
    private CountryIdAdapter mCountryIdAdapter;
    private ListView mCountryList;
    private GoodsManager mGoodsManager;
    private int mTime = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        mUsrInfoMap = new HashMap<>();
    }

    private void initViews() {
        mCountryCode = findViewById(R.id.country_id);
        mPhoneNum = findViewById(R.id.phone_num);
        mVertificationCode = findViewById(R.id.verificationCode);
        mButton = findViewById(R.id.verificationCodeBtn);
        mLoginButton = findViewById(R.id.login_btn);

        mButton.setOnClickListener(listener);
        mLoginButton.setOnClickListener(listener);

        String[] mItems = getResources().getStringArray(R.array.cities_data);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cities_data, R.layout.country_id_item_layout);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountryCode.setSelection(0);
        mCountryCode.setAdapter(arrayAdapter);

        mGoodsManager = new GoodsManager(this);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.verificationCodeBtn:
                    Message message = handler.obtainMessage(1);
                    handler.sendMessage(message);
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.button_unenabled_text_color));
                    mUsrInfoMap.clear();
                    mUsrInfoMap.put("phone", mPhoneNum.getText().toString());
                    mGoodsManager.doPostRequest(mUsrInfoMap, URLUtils.GET_VERTIFICATION_CODE, URLUtils.RequestType.SEND_CODE);
                    break;
                case R.id.login_btn:
                    mUsrInfoMap.clear();
                    mUsrInfoMap.put("phone", mPhoneNum.getText().toString());
                    mUsrInfoMap.put("code", mVertificationCode.getText().toString());
                    mGoodsManager.doPostRequest(mUsrInfoMap, URLUtils.LOGIN_URL, URLUtils.RequestType.LOGIN);
                    finish();
                    break;
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mTime--;
                    mButton.setText(mTime + "s");
                    if (mTime > 0) {
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);
                    } else {
                        mButton.setEnabled(true);
                        mButton.setText(getResources().getString(R.string.vertification_code));
                        mButton.setTextColor(getResources().getColor(R.color.main_theme_color));
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };
}

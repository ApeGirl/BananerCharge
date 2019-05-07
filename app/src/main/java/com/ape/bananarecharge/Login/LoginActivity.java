package com.ape.bananarecharge.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.ape.bananarecharge.Adapter.CountryIdAdapter;
import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.R;

import java.util.HashMap;
import java.util.Map;

import Util.MapUtil;
import Util.URLUtils;

public class LoginActivity extends AppCompatActivity {
    private Spinner mCountryCode;
//    private TextView mCountryCode;
    private AutoCompleteTextView mPhoneNum;
    private EditText mVertificationCode;
    private Button mButton;
    private Button mLoginButton;
    private Map<String, String> mUsrInfoMap;
    private CountryIdAdapter mCountryIdAdapter;
    private ListView mCountryList;
    private GoodsManager mGoodsManager;

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
                    mUsrInfoMap.clear();
                    mUsrInfoMap.put("phone", mPhoneNum.getText().toString());
                    mGoodsManager.doPostRequest(mUsrInfoMap, URLUtils.GET_VERTIFICATION_CODE, URLUtils.RequestType.SEND_CODE);
                    break;
                case R.id.login_btn:
                    mUsrInfoMap.clear();
                    mUsrInfoMap.put("phone", mPhoneNum.getText().toString());
                    mUsrInfoMap.put("code", mVertificationCode.getText().toString());
                    mGoodsManager.doPostRequest(mUsrInfoMap, URLUtils.LOGIN_URL, URLUtils.RequestType.LOGIN);
                    break;
            }
        }
    };

}

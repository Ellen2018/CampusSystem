package com.zheng.zhi.campussystem.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.DrawerChooseColorAdapter;
import com.zheng.zhi.campussystem.bean.CalculatorSetting;
import com.zheng.zhi.campussystem.bean.SkinColor;
import com.zheng.zhi.campussystem.helper.SharedPreferencesHelper;
import com.zheng.zhi.campussystem.utils.MathUtil;
import com.zheng.zhi.campussystem.utils.statusutil.StatusUtils;
import com.zheng.zhi.campussystem.view.CalculatorTextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculatorActivity extends Activity implements View.OnClickListener {

    private Context context;

    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.equation_tv)
    EditText equation;
    @BindView(R.id.left_number_zero)
    CalculatorTextView leftNumberZero;
    @BindView(R.id.left_number_one)
    CalculatorTextView leftNumberOne;
    @BindView(R.id.left_number_two)
    CalculatorTextView leftNumberTwo;
    @BindView(R.id.left_number_three)
    CalculatorTextView leftNumberThree;
    @BindView(R.id.left_number_four)
    CalculatorTextView leftNumberFour;
    @BindView(R.id.left_number_five)
    CalculatorTextView leftNumberFive;
    @BindView(R.id.left_number_six)
    CalculatorTextView leftNumberSix;
    @BindView(R.id.left_number_seven)
    CalculatorTextView leftNumberSeven;
    @BindView(R.id.left_number_eight)
    CalculatorTextView leftNumberEight;
    @BindView(R.id.left_number_nine)
    CalculatorTextView leftNumberNine;
    @BindView(R.id.left_point)
    CalculatorTextView leftPoint;
    @BindView(R.id.left_equals)
    CalculatorTextView leftEquals;
    @BindView(R.id.right_delete)
    CalculatorTextView rightDelete;
    @BindView(R.id.right_div)
    CalculatorTextView rightDiv;
    @BindView(R.id.right_mul)
    CalculatorTextView rightMul;
    @BindView(R.id.right_sub)
    CalculatorTextView rightSub;
    @BindView(R.id.right_add)
    CalculatorTextView rightAdd;

    @BindView(R.id.skin)
    ImageView skinSetting;
    @BindView(R.id.skin_setting_dl)
    DrawerLayout skinSettingDl;

    @BindView(R.id.choose_skin_recyclerview)
    RecyclerView chooseSkinRecyclerView;

    private String equationStr = "";
    private String password;
    private SharedPreferencesHelper passwordPsHelper, skinPsHelper;
    private List<CalculatorTextView> leftItems;
    private List<CalculatorTextView> rightItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
        initView();
        context = this;
        initData();
    }

    private void initView() {

        leftItems = new LinkedList<>();
        rightItems = new LinkedList<>();

        leftItems.add(leftNumberZero);
        leftItems.add(leftNumberOne);
        leftItems.add(leftNumberTwo);
        leftItems.add(leftNumberThree);
        leftItems.add(leftNumberFour);
        leftItems.add(leftNumberFive);
        leftItems.add(leftNumberSix);
        leftItems.add(leftNumberSeven);
        leftItems.add(leftNumberEight);
        leftItems.add(leftNumberNine);
        leftItems.add(leftPoint);
        leftItems.add(leftEquals);

        rightItems.add(rightDelete);
        rightItems.add(rightAdd);
        rightItems.add(rightSub);
        rightItems.add(rightMul);
        rightItems.add(rightDiv);

        skinSetting.setOnClickListener(this);
    }

    private void toChangeSkinByString(String colorTop, String colorLeft, String colorRight) {
        toChangeSkinByInt(Color.parseColor(colorTop), Color.parseColor(colorLeft), Color.parseColor(colorRight));
        saveSkin(colorTop, colorLeft, colorRight);
    }

    private void saveSkin(String colorTop, String colorLeft, String colorRight) {
        skinPsHelper.put(CalculatorSetting.SKIN_COLOR_TOP, colorTop);
        skinPsHelper.put(CalculatorSetting.SKIN_COLOR_LEFT, colorLeft);
        skinPsHelper.put(CalculatorSetting.SKIN_COLOR_RIGHT, colorRight);
    }

    private void toChangeSkinByInt(int colorTop, int colorLeft, int colorRight) {
        StatusUtils.setStatusBarColor(this, colorRight);
        top.setBackgroundColor(colorTop);
        for (CalculatorTextView leftItem : leftItems) {
            leftItem.setUpColor(colorLeft);
        }
        for (CalculatorTextView rightItem : rightItems) {
            rightItem.setUpColor(colorRight);
        }
    }

    private void initData() {
        skinPsHelper = new SharedPreferencesHelper(context, CalculatorSetting.TAG);
        if (skinPsHelper.getSharedPreference(CalculatorSetting.SKIN_COLOR_TOP, null) == null) {
            toChangeSkinByString(SkinColor.TOP_COLOR, SkinColor.LEFT_COLOR, SkinColor.RIGHT_COLOR);
        } else {
            String topColor = (String) skinPsHelper.getSharedPreference(CalculatorSetting.SKIN_COLOR_TOP, SkinColor.TOP_COLOR);
            String leftColor = (String) skinPsHelper.getSharedPreference(CalculatorSetting.SKIN_COLOR_LEFT, SkinColor.LEFT_COLOR);
            String rightColor = (String) skinPsHelper.getSharedPreference(CalculatorSetting.SKIN_COLOR_RIGHT, SkinColor.RIGHT_COLOR);
            toChangeSkinByString(topColor, leftColor, rightColor);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chooseSkinRecyclerView.setLayoutManager(linearLayoutManager);
        SkinColor skinColor = new SkinColor();
        chooseSkinRecyclerView.setAdapter(new DrawerChooseColorAdapter(context, skinColor.getSkinColors(), new DrawerChooseColorAdapter.CallBack() {
            @Override
            public void chooseSkin(SkinColor skinColor) {
                toChangeSkinByString(skinColor.getTopColor(), skinColor.getLeftColor(), skinColor.getRightColor());
                skinSettingDl.closeDrawers();
            }
        }));
    }

    public void onKeyboardClick(View view) {
        TextView textView = (TextView) view;
        String currStr = textView.getText().toString().trim();
        if (currStr.equals("÷")) {
            currStr = "/";
        }
        if (currStr.equals("×")) {
            currStr = "*";
        }
        handlerEnter(currStr);
    }

    private void handlerEnter(String currentStr) {

        if (currentStr.endsWith("DEL")) {

            if (equationStr != null && equationStr.length() > 0)
                equationStr = equationStr.substring(0, equationStr.length() - 1);

        } else if (currentStr.equals("=")) {

            if (equationStr == null || equationStr.length() == 0) {
                return;
            }

            if (!MathUtil.isOperationalsChar(equationStr.charAt(equationStr.length() - 1))) {
                if (MathUtil.isDivisorZero(equationStr)) {
                    Toast.makeText(CalculatorActivity.this, getString(R.string.divisor_zero_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                equationStr = toMadeComputations(equationStr);
            }

        } else {
            if (equationStr.length() > 0 && MathUtil.isOperationalsChar(equationStr.charAt(equationStr.length() - 1))) {
                if (!MathUtil.isOperationalsChar(currentStr.charAt(0))) {
                    equationStr = equationStr + currentStr;
                }
            } else {
                if ((equationStr == null || equationStr.length() == 0) && MathUtil.isOperationalsChar(currentStr.charAt(0))) {
                    return;
                }
                if (equationStr.equals("0") && !MathUtil.isOperationalsChar(currentStr.charAt(0))) {
                    equationStr = "";
                }
                if (currentStr.charAt(0) == '.') {
                    String currentNumber = equationStr;
                    for (int i = equationStr.length() - 1; i >= 0; i--) {
                        if (MathUtil.isOperationals(equationStr.charAt(i))) {
                            currentNumber = equationStr.substring(i + 1, equationStr.length());
                            break;
                        }
                    }
                    if (currentNumber.contains(".")) return;
                }
                equationStr = equationStr + currentStr;
            }
        }
        String equationStr1 = equationStr.replace("*", "×").replace("/", "÷");
        equation.setText(equationStr1);
        toScrollRight(equation);
    }

    private String toMadeComputations(String str) {
        String result = MathUtil.evalBigNumber(str) + "";
        result = result.endsWith(".0") ? result.substring(0, result.indexOf(".")) : result;
        equation.setText(result);
        return result;
    }

    private void toScrollRight(EditText editText) {
        Editable etext = editText.getText();
        Selection.setSelection(etext, etext.length());

    }

    private void toHomeActivity() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toHomeActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.skin:
                skinSettingDl.openDrawer(Gravity.RIGHT);
                break;
        }
    }
}

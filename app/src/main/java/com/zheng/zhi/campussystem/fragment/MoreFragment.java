package com.zheng.zhi.campussystem.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.CalculatorActivity;
import com.zheng.zhi.campussystem.activity.NoteBookActivity;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.dialog.DeveloperMessageDialog;
import com.zheng.zhi.campussystem.dialog.EditTextDialg;
import com.zheng.zhi.campussystem.helper.MyMMKV;
import com.zheng.zhi.campussystem.utils.PermissionUtils;
import com.zheng.zhi.campussystem.utils.ToastUtils;
import com.zheng.zhi.campussystem.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class MoreFragment extends BaseFragment implements BaseFragment.ButterKnifeInterface {

    @BindView(R.id.profile_image)
    ImageView imageView;
    @BindView(R.id.ll_user_message)
    LinearLayout llUserMessage;
    @BindView(R.id.llCalculator)
    LinearLayout llCalculator;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_content)
    TextView tvUserContent;

    private Unbinder unbinder;
    private final static int CHOOSE_IMAGE_RETRUEN_CODE = 1;
    private final static int GET_FILE_RW_PERMISSION = 2;
    private PermissionUtils permissionUtils;
    private MyMMKV myMMKV;
    private final String USER_MESSAGE = "User_Message";
    private UserMessage userMessage;
    private  EditTextDialg editTextDialg;

    @OnClick({R.id.profile_image,R.id.llCalculator,R.id.llNotebook,R.id.llMessage,R.id.tv_user_name,R.id.tv_user_content})
    void onClick(View view){
        switch (view.getId()){
            case R.id.profile_image:
                //申请文件读写权限
                List<String> stringList = new ArrayList<>();
                stringList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                stringList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionUtils.checkPermissions(stringList, GET_FILE_RW_PERMISSION, new PermissionUtils.PermissionCallback() {

                    @Override
                    public void success() {
                        //调用系统相册选取图片
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, CHOOSE_IMAGE_RETRUEN_CODE);
                    }

                    @Override
                    public void failure() {
                        ToastUtils.toast(getActivity(), "您拒绝了文件权限！");
                    }
                });
                break;
            case R.id.llCalculator:
                //点击了计算器
                Intent intent = new Intent(getActivity(), CalculatorActivity.class);
                startActivity(intent);
                break;
            case R.id.llNotebook:
                //点击了备忘录
                Intent intentNotebook = new Intent(getActivity(), NoteBookActivity.class);
                startActivity(intentNotebook);
                break;
            case R.id.llMessage:
                //点击了关于开发者
                DeveloperMessageDialog developerMessageDialog = new DeveloperMessageDialog(userMessage.getImagePath());
                developerMessageDialog.show(getFragmentManager(),"message");
                break;
            case R.id.tv_user_name:
                 editTextDialg = new EditTextDialg(userMessage.getUserName(), "编辑用户名", new EditTextDialg.Callback() {
                    @Override
                    public void ok(String content) {
                        if(TextUtils.isEmpty(content)) {
                            tvUserName.setText("ClAndEllen");
                        }else {
                            tvUserName.setText(content);
                        }
                        //保存用户修改完成的用户名
                        userMessage.setUserName(content);
                        toSaveUserMessage();
                        //让编辑对话框消失
                        editTextDialg.dismiss();
                        editTextDialg = null;
                    }
                });
                editTextDialg.show(getFragmentManager(),"edit");
                break;
            case R.id.tv_user_content:
                editTextDialg = new EditTextDialg(userMessage.getContent(), "编辑介绍", new EditTextDialg.Callback() {
                    @Override
                    public void ok(String content) {
                        if(TextUtils.isEmpty(content)) {
                            tvUserContent.setText("积极面对生活");
                        }else {
                            tvUserContent.setText(content);
                        }
                        userMessage.setContent(content);
                        toSaveUserMessage();
                        editTextDialg.dismiss();
                        editTextDialg = null;
                    }
                });
                editTextDialg.show(getFragmentManager(),"edit");
                break;
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        permissionUtils = new PermissionUtils(getActivity(), getActivity());
        myMMKV = new MyMMKV(this.getClass().getName());
        String json = (String) myMMKV.getValue(USER_MESSAGE, "");
        if (json.length() > 0) {
            userMessage = new Gson().fromJson(json, UserMessage.class);
            showUserMessage();
        } else {
            userMessage = new UserMessage();
        }
    }

    private void showUserMessage() {
        if (!TextUtils.isEmpty(userMessage.getImagePath())) {
            Glide.with(getActivity()).load(userMessage.getImagePath()).into(imageView);
        }
        if (!TextUtils.isEmpty(userMessage.getUserName())) {
            tvUserName.setText(userMessage.getUserName());
        }
        if (!TextUtils.isEmpty(userMessage.getContent())) {
            tvUserContent.setText(userMessage.getContent());
        }
    }

    private void toSaveUserMessage() {
        myMMKV.save(USER_MESSAGE, new Gson().toJson(userMessage));
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_more;
    }

    @Override
    public void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_IMAGE_RETRUEN_CODE) {
                //获取用户选择的相册中的图片地址
                Uri uri = data.getData();
                String imagePath = UriUtils.getRealFilePath(getActivity(), uri);
                Log.e("用户显示的图片",imagePath);
                Glide.with(getActivity()).load(imagePath).into(imageView);
                userMessage.setImagePath(imagePath);
                toSaveUserMessage();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static class UserMessage {
        private String userName;
        private String content;
        private String imagePath;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }

}

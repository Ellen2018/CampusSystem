package com.zheng.zhi.campussystem.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.utils.PermissionUtils;
import com.zheng.zhi.campussystem.utils.ToastUtils;
import com.zheng.zhi.campussystem.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class MoreFragment extends BaseFragment implements BaseFragment.ButterKnifeInterface {

    @BindView(R.id.profile_image)
    ImageView imageView;
    @BindView(R.id.ll_user_message)
    LinearLayout llUserMessage;

    private Unbinder unbinder;
    private final static int CHOOSE_IMAGE_RETRUEN_CODE = 1;
    private final static int GET_FILE_RW_PERMISSION = 2;
    private PermissionUtils permissionUtils;

    @Override
    protected void initData() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> stringList = new ArrayList<>();
                stringList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                stringList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionUtils.checkPermissions(stringList, GET_FILE_RW_PERMISSION, new PermissionUtils.PermissionCallback() {

                    @Override
                    public void success() {
                        Log.e("执行没？","dsad");
                        //调用系统相册选取图片
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, CHOOSE_IMAGE_RETRUEN_CODE);
                    }

                    @Override
                    public void failure() {
                        ToastUtils.toast(getActivity(),"您拒绝了文件权限！");
                    }
                });
            }
        });

    }

    @Override
    protected void initView() {
        permissionUtils = new PermissionUtils(getActivity(),getActivity());
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_more;
    }

    @Override
    public void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == CHOOSE_IMAGE_RETRUEN_CODE) {
                Uri uri = data.getData();
                String imagePath = UriUtils.getRealFilePath(getActivity(), uri);
                Glide.with(getActivity()).load(imagePath).into(imageView);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

package com.zheng.zhi.campussystem.dialog;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class DeveloperMessageDialog extends BaseDialogFragment implements BaseDialogFragment.ButterKnifeInterface {

    private String imagePath = "";
    private Unbinder unbinder;

    private String studentId = "学号：";
    private String name = "姓名：";
    private String professionName = "专业名：";
    private String departmentName = "院系名：";
    private String tutorName = "导师名：";
    private String version = "版本号：1.0";
    private String date = "日期：2019-04-02";

    @BindView(R.id.civ_user_icon)
    ImageView civUserIcon;
    @BindView(R.id.tv_student_id)
    TextView tvStudentId;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_profession)
    TextView tvProfession;
    @BindView(R.id.tv_department)
    TextView tvDepartmentName;
    @BindView(R.id.tv_teacher_name)
    TextView tvTutorName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_date)
    TextView tvDate;

    public DeveloperMessageDialog(String imagePath){
        this.imagePath = imagePath;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        if(!TextUtils.isEmpty(imagePath)) {
            Glide.with(getActivity()).load(imagePath).into(civUserIcon);
        }
        tvStudentId.setText(studentId);
        tvName.setText(name);
        tvProfession.setText(professionName);
        tvDepartmentName.setText(departmentName);
        tvTutorName.setText(tutorName);
        tvVersion.setText(version);
        tvDate.setText(date);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_user_message;
    }

    @Override
    protected Boolean setCancelable() {
        return true;
    }

    @Override
    protected Boolean setCanceledOnTouchOutside() {
        return true;
    }

    @Override
    protected Boolean setWinowTransparent() {
        return true;
    }

    @Override
    public void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }
}

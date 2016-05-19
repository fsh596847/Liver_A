package com.android.doctor.ui.plan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.base.SingleTapConfirm;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/30.
 */
public class EditPlanItemActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_NEW_PROJECT_CODE = 200;
    public static final int REQUEST_UPDATE_PROJECT_CODE = 201;

    @InjectView(R.id.tbtn_close_this)
    protected ToggleButton mToggleBtn;
    @InjectView(R.id.ll_container)
    protected LinearLayout mLlContainer;
    @InjectView(R.id.ll_switch)
    protected View mLlSwitch;

    private PlanDeta.PlanDetaEntity mDataItems;
    private boolean mChanged = false;
    private int mReqCode ;
    private boolean bClosethis = false;

    private GestureDetector gestureDetector;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_edit_scheme_item);
    }

    @Override
    protected void initView() {
        super.initView();
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        Intent intent = getIntent();
        mReqCode = intent.getIntExtra("requestCode", 0);
        mDataItems = intent.getParcelableExtra("data");
        setActionBar(mDataItems.getName());
        setViewData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data == null) return;
            PlanDeta.PlanDetaEntity.ItemsEntity item = data.getParcelableExtra("data");
            if (requestCode == REQUEST_NEW_PROJECT_CODE) {
                mLlContainer.addView(getCaptionView(item));
                if (mDataItems.getItems() == null) {
                    List <PlanDeta.PlanDetaEntity.ItemsEntity> list = new ArrayList<>();
                    list.add(item);
                    mDataItems.setItems(list);
                } else {
                    mDataItems.getItems().add(item);
                }
                mChanged = true;
            } else if (requestCode == REQUEST_UPDATE_PROJECT_CODE) {
                int index = data.getIntExtra("index", -1);
                Log.d(AppConfig.TAG, "onActivityResult. index = " + index);
                if (index == -1) return;
                updateCaptionView(index, item);
                updateItemData(index, item);
                mChanged = true;
            }
        }
    }

    private void setViewData() {
        if (mDataItems == null || mDataItems.getItems() == null) return;
        boolean bHide = "false".equals(mDataItems.getShow());
        mToggleBtn.setChecked(bHide);
        //if (!bHide) {
            mLlContainer.removeAllViews();
            for (PlanDeta.PlanDetaEntity.ItemsEntity item : mDataItems.getItems()) {
                mLlContainer.addView(getCaptionView(item));
            }
        //}
    }

    private View getCaptionView(PlanDeta.PlanDetaEntity.ItemsEntity item) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_swipe_caption, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView tvHint = (TextView) view.findViewById(R.id.tv_content_ex);
        tvTitle.setText(item.getName());
        tvContent.setText(item.getContent());
        tvHint.setText(item.getHint());
        setItemListener(view);
        return view;
    }

    private void updateCaptionView(int index, PlanDeta.PlanDetaEntity.ItemsEntity item) {
        Log.d(AppConfig.TAG, "onActivityResult. index = " + index+ ", " + mLlContainer.getChildCount());
        if (0 <= index && index < mLlContainer.getChildCount()) {
            View view = mLlContainer.getChildAt(index);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            TextView tvHint = (TextView) view.findViewById(R.id.tv_content_ex);
            tvTitle.setText(item.getName());
            tvContent.setText(item.getContent());
            tvHint.setText(item.getHint());
        }
    }


    private void updateItemData(int index , PlanDeta.PlanDetaEntity.ItemsEntity item) {
        List<PlanDeta.PlanDetaEntity.ItemsEntity> list = mDataItems.getItems();
        if (list != null && 0 <= index && index < list.size()) {
            list.remove(index);
            list.add(item);
        }
    }


    private void deleteCaptionView(final View v, final int index) {
        String msg_tip = getString(R.string.are_you_sure_to_delete_this_item);
        AlertDialog.Builder builder = DialogUtils.getConfirmDialog(this, msg_tip,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLlContainer.removeView(v);
                        mDataItems.getItems().remove(index);
                        mChanged  = true;
                    }
                }).setCancelable(false).setTitle(R.string.tips);
        Dialog d = builder.create();
        d.show();
    }

    private int findViewIndexInContainer(View v) {
        for (int i = 0; i < mLlContainer.getChildCount(); ++i) {
            View view = mLlContainer.getChildAt(i);
            if (view == v) return i;
        }
        return -1;
    }

    private void setItemListener(View item) {
        item.findViewById(R.id.tv_delete).setOnClickListener(this);
        //item.findViewById(R.id.rl_content).setOnClickListener(this);
        //item.setOnClickListener(this);
        item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    onSubItem(v);
                    return true;
                }
                return false;
            }
        });
    }

    @OnCheckedChanged(R.id.tbtn_close_this)
    protected void onToggleBtnChanged(CompoundButton btn, boolean check) {
        mLlSwitch.setVisibility(check ? View.INVISIBLE : View.VISIBLE);
        mDataItems.setShow(check ? "false" : "1");
        mChanged  = true;
    }

    /**
     * 子项点击事件
     */
    @Override
    public void onClick(View v) {
        View p = (View) v.getParent();
        int index = findViewIndexInContainer(p);
        if (v.getId() == R.id.tv_delete){
            Log.d(AppConfig.TAG, " delete index : " + index);
            if (index != -1) {
                deleteCaptionView(p, index);
            }
        }
    }

    /**
     * 查看/编辑子项
     * @param v
     */
    private void onSubItem(View v) {
        int index = findViewIndexInContainer(v);
        Log.d(AppConfig.TAG, "onSubItem. index = " + index);
        if (mDataItems == null || mDataItems.getItems() == null) return;
        if (0 <= index && index < mDataItems.getItems().size()) {
            Intent intent = new Intent();
            intent.putExtra("index", index);
            intent.putExtra("data", mDataItems.getItems().get(index));
            intent.putExtra("requestCode", mReqCode);
            if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_MEDICINE_CODE) {
                intent.setClass(this, EditPlanMedicItemActivity.class);
            } else {
                intent.setClass(this, EditPlanItemDetaActivity.class);
            }
            startActivityForResult(intent, REQUEST_UPDATE_PROJECT_CODE);
        }
    }

    /**
     * 添加新项目
     */
    @OnClick(R.id.btn_add_project)
    protected void onAddNewProject() {
        Intent intent = new Intent();
        intent.putExtra("requestCode", mReqCode);
        if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_MEDICINE_CODE) {
            intent.setClass(this, EditPlanMedicItemActivity.class);
        } else {
            intent.setClass(this, EditPlanItemDetaActivity.class);
        }
        startActivityForResult(intent, REQUEST_NEW_PROJECT_CODE);
    }

    /**
     * 完成
     */
    @OnClick(R.id.imgbtn_complete)
    protected void onComplete() {
        Intent intent = new Intent();
        boolean bCheck = mToggleBtn.isChecked();
        Log.d(AppConfig.TAG, "" + bCheck);
        intent.putExtra("data", mDataItems);
        setResult(RESULT_OK, intent);
        AppManager.getAppManager().finishActivity(EditPlanItemActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (mChanged) {
            onNavBackTips();
        }
        super.onBackPressed();
    }

    /**
     * 修改返回提示
     * @return
     */

    private boolean onNavBackTips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.are_sure_to_give_up_change);
        builder.setPositiveButton(R.string.btn_continue_edit, null);
        builder.setNegativeButton(R.string.btn_give_up_change, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return false;
    }

}

package com.wma.library.select;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wma.library.R;
import com.wma.library.base.BaseActivity;
import com.wma.library.databinding.ActivityChooseFileBinding;
import com.wma.library.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/12/8 0008
 */
public class SelectActivity extends BaseActivity<ActivityChooseFileBinding> implements View.OnClickListener {
    public static final int REQUEST_CHOOSE_FILE = 50;
    public static final int REQUEST_PERMISSION = 50;
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    String[] needPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    PermissionUtils mPermissionUtils;
    private String mSelectType;
    private int mLimit;
    FileAdapter mAdapter;
    ArrayList<FileItem> mSelectList;

    /**
     * 开始选择文件
     */
    public static void chooseFile(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, SelectActivity.class);
        intent.putExtra(KEY_BUNDLE, bundle);
        int requestCode = bundle.getInt(SelectDialog.KEY_REQUEST_CODE, SelectDialog.REQUEST_SELECT_CODE);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public String getTitleStr() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        mBinding.iconBack.setOnClickListener(this);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3, RecyclerView.VERTICAL, false));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 5;
                outRect.bottom = 5;
                outRect.right = 5;
                outRect.left = 5;
            }
        });
        Bundle bundle = getIntent().getBundleExtra(KEY_BUNDLE);
        if (bundle != null) {

            mLimit = bundle.getInt(SelectDialog.KEY_LIMIT);

            mSelectType = bundle.getString(SelectDialog.KEY_TYPE);

            mSelectList = bundle.getParcelableArrayList(SelectDialog.KEY_SELECT_LIST);
        }
        mPermissionUtils = PermissionUtils.getInstance(getApplicationContext());
        boolean allPermissionGranted = mPermissionUtils.isAllPermissionGranted(needPermissions);
        if (allPermissionGranted) {
            queryMedia();
        } else {
            List<String> list = mPermissionUtils.checkIsPermissionAllGranted(needPermissions);
            mPermissionUtils.requestPermissions(this, list, REQUEST_PERMISSION);
        }

        mBinding.tvFinish.setOnClickListener(this);
    }

    /**
     * 查询媒体库
     */
    private void queryMedia() {
        SelectUtils selectUtils = new SelectUtils(getApplicationContext());
        List<FileItem> originList = selectUtils.Select(mSelectType);
        mAdapter = new FileAdapter(originList, getApplicationContext());
        mAdapter.setSelectType(mSelectType);
        mAdapter.setLimit(mLimit);
        mAdapter.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelect(List<FileItem> selectItem) {
                StringBuilder sb = new StringBuilder();
                sb.append("(").append(selectItem.size()).append("/").append(mLimit).append(")").append("完成");
                mBinding.tvFinish.setText(sb.toString());
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_file;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> notGrandPermission = mPermissionUtils.requestResult(permissions, grantResults);
        if (notGrandPermission.size() == 0) {
            queryMedia();
        } else {
            showNoticeDialog(notGrandPermission);
        }
    }


    private void showNoticeDialog(final List<String> notGrandPermission) {
        StringBuilder message = new StringBuilder();
        message.append("您还需要一下权限: ").append("\n");
        for (int i = 0; i < notGrandPermission.size(); i++) {
            String permission = notGrandPermission.get(i);
            String chineseByPermission = mPermissionUtils.getChineseByPermission(permission);
            message.append(chineseByPermission).append("\n");
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage(message.toString())
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SelectActivity.this.finish();
                    }
                })
                .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPermissionUtils.requestPermissions(SelectActivity.this, notGrandPermission, REQUEST_PERMISSION);
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.iconBack) {
            onBackPressed();
        } else if (v == mBinding.tvFinish) {
            ArrayList<FileItem> selectFileItems = mAdapter.getSelectFileItems();
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(SelectDialog.KEY_SELECT_LIST, selectFileItems);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}

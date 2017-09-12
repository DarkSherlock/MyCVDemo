package com.liang.tind.mycv.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.liang.tind.mycv.R;
import com.liang.tind.mycv.utils.PackageUtil;
import com.liang.tind.mycv.utils.ToastUtil;

/**
 * Created by Sherlock on 2017/9/3.
 */

public class WeChatFragment extends BaseFragment {

    private Button mBtnCopyWechat;
    private Button mBtnOpenWechat;

    @Override
    protected void initView(View view) {
        mBtnCopyWechat = view.findViewById(R.id.btn_copy_we_chat);
        mBtnOpenWechat = view.findViewById(R.id.btn_open_we_chat);
    }

    @Override
    protected void initData() {
        //复制微信号
        mBtnCopyWechat.setOnClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(getString(R.string.we_chat));
            ToastUtil.showToast(getActivity(), getString(R.string.tip_copyed_we_chat_to_clipboard));
        });
        //打开微信
        mBtnOpenWechat.setOnClickListener(view -> {
            if (PackageUtil.isAppAvilible(getActivity(), PackageUtil.WE_CHAT_PACKAGE_NAME)) {
                Intent intent = PackageUtil.getOtherAppIntent(PackageUtil.WE_CHAT_PACKAGE_NAME, PackageUtil.WE_CHAT_LAUNCHER);
                startActivity(intent);
            } else {
                ToastUtil.showToast(getActivity(), getString(R.string.tip_no_install_we_chat));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }


}

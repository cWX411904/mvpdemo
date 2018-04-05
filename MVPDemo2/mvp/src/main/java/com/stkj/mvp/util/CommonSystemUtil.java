package com.stkj.mvp.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Karma on 2018/3/21.
 */

public class CommonSystemUtil
{
    /**
     * 通讯录
     *
     * @param context
     */
    public static void startSystemContacts(Context context) {
        Uri uri = Uri.parse("content://contacts/people");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        context.startActivity(intent);
    }

    /**
     * 复制内容到粘贴板
     *
     * @param context
     * @param content
     */
    public static void copyText(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(content.trim());
    }
}

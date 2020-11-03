package com.mjb.ytmp.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

public class DialogBuilder {

    private static Context context;
    private int contentView = 0;
    private int style = 0;
    private boolean cancelable = true;
    private boolean transparent = false;

    public static DialogBuilder with(Context c) {
        context = c;
        return HOLDER.instance;
    }

    public DialogBuilder contentView(int id) {
        this.contentView = id;
        return this;
    }

    public DialogBuilder style(int id) {
        style = id;
        return this;
    }

    public DialogBuilder cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public DialogBuilder transparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }


    public Dialog build() {
        if (contentView == 0) {
            throw new RuntimeException("set content view with contentView(int id)");
        }
        Dialog dialog;
        if (style == 0) {
            dialog = new Dialog(context);
        } else {
            dialog = new Dialog(context, style);
        }
        dialog.setContentView(contentView);
        if (transparent) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.setCancelable(cancelable);
        return dialog;
    }


    private static class HOLDER {
        static final DialogBuilder instance = new DialogBuilder();
    }
}


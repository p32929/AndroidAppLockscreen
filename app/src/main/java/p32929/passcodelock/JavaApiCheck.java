package p32929.passcodelock;

import android.content.Context;
import android.graphics.Color;

import p32929.easypasscodelock.Utils.EasyLock;

/** Not used by the sample. It only proves the 1.x Java calls still compile. */
final class JavaApiCheck {
    static void calls(Context context) {
        EasyLock.setPassword(context, MainActivity.class);
        EasyLock.changePassword(context, MainActivity.class);
        EasyLock.disablePassword(context, MainActivity.class);
        EasyLock.checkPassword(context);
        EasyLock.setBackgroundColor(Color.BLACK);
        EasyLock.forgotPassword(view -> { });
        EasyLock.setPassword(context);
        EasyLock.setBiometricEnabled(true);
        boolean set = EasyLock.isPasswordSet(context);
    }
}

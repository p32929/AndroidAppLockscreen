package p32929.passcodelock;

import android.os.Bundle;

import p32929.easypasscodelock.Utils.LockscreenHandler;

public class TestActivity extends LockscreenHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}

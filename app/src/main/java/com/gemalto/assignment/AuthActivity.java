package com.gemalto.assignment;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gemalto.assignment.auth.BioMetricsUtils;
import com.gemalto.assignment.auth.FingerprintHandler;
import com.gemalto.assignment.auth.GenerateKeyCipher;

public class AuthActivity extends AppCompatActivity {
    FingerprintManager fingerprintManager;
    KeyguardManager keyguardManager;
    BioMetricsUtils bioMetricsUtils = new BioMetricsUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        keyguardManager =
                (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        fingerprintManager =
                (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);
        if(checkAllPermissions()) {
            GenerateKeyCipher generateKeyCipher = new GenerateKeyCipher();
            generateKeyCipher.generateKey();
            if (generateKeyCipher.cipherInit()) {
                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(generateKeyCipher.getcipher());
                FingerprintHandler helper = new FingerprintHandler(this);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }

    private boolean checkAllPermissions(){
        return (bioMetricsUtils.isSdkVersionSupported() &&
                bioMetricsUtils.isFingerprintAvailable(this) &&
                bioMetricsUtils.isHardwareSupported(this) &&
                bioMetricsUtils.isPermissionGranted(this));
    }
}

package com.bloder.shelf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bloder.Shelf;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Shelf.forTest().inProdEnvironment().test();
    }
}

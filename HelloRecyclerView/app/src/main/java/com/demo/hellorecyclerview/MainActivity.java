package com.demo.hellorecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.hellorecyclerview.test1.ActTest1;
import com.demo.hellorecyclerview.test2.ActTest2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        startActivity(new Intent(this, ActTest1.class));
        startActivity(new Intent(this, ActTest2.class));
    }

}

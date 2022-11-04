package com.example.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.textViewid);
        //初始化ViewModel
        myViewModel=new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(new Application())).get(MyViewModel.class);
        //显示ViewModel中初始的时间
        textView.setText(String.valueOf(myViewModel.getCurrentSecond().getValue()) );
        //观察到ViewModel改变之后，TextView发生更新
        myViewModel.getCurrentSecond().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                textView.setText(String.valueOf(i));
            }
        });
        startTimer();
    }
    //创建了一个定时器，每隔一秒钟去改变viewModel中的时间
    private void startTimer(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //非UI线程用postValue
                //UI线程用setValue
                myViewModel.getCurrentSecond().postValue(myViewModel.getCurrentSecond().getValue()+1);
            }
        },1000,1000);
    }
}
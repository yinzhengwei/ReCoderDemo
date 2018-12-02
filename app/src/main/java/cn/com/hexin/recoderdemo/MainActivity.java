package cn.com.hexin.recoderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.hexin.recoderdemo.text.MarqueeText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MarqueeText text = findViewById(R.id.text);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setEllipsize(null);
            }
        });


        final TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaRecordFunc mRecord = MediaRecordFunc.getInstance();
                if (mRecord.isRecord()) {
                    tv.setText(String.format("录音已停止.录音文件:%s\n文件大小：%d",
                            AudioFileFunc.getAMRFilePath(),
                            mRecord.getRecordFileSize()));
                    mRecord.stopRecordAndFile();
                } else {
                    tv.setText("已开始");
                    mRecord.startRecordAndFile();
                }
            }
        });
    }
}

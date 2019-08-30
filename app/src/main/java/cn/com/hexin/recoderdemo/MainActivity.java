package cn.com.hexin.recoderdemo;

import android.Manifest;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int x = 0;
    int y = 0;

    View btn_shangceng = null;

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT >= 19) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu(this);

        setContentView(R.layout.activity_main);

//        final MarqueeText text = findViewById(R.id.text);
//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            }
//        });
//
//        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                text.setEllipsize(null);
//            }
//        });


        final TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] s = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(s, 0);
                }

                MediaRecordFunc mRecord = MediaRecordFunc.getInstance();
                if (mRecord.isRecord()) {
                    tv.setText(String.format("录音已停止.录音文件:%s\n文件大小：%d",
                            AudioFileFunc.getAMRFilePath(),
                            mRecord.getRecordFileSize()));
                    mRecord.stopRecordAndFile();
                } else {
                    tv.setText("已开始");
                    //mRecord.startRecordAndFile();

                    mRecord.startRecording(MediaRecorder.OutputFormat.THREE_GPP,view.getContext());
                }
            }
        });

//        btn_shangceng = findViewById(R.id.btn_shangceng);
//        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, ((Button) v).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        findViewById(R.id.btn_showsecend).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, ((Button) v).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        findViewById(R.id.btn_showthree).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, ((Button) v).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        findViewById(R.id.btn_onclick).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int[] it = new int[2];
//                btn_shangceng.getLocationInWindow(it);
//                x = it[0] + 5;
//                y = it[1] + 10;
//                setMouseClick(x, y);
//            }
//        });
//        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float x = btn_shangceng.getX();
//
//                btn_shangceng.setX(x - 15);
//            }
//        });
//        addVIewTouch(R.id.btn_left, "l");

//        findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float x = btn_shangceng.getX();
//
//                btn_shangceng.setX(x + 15);
//            }
//        });
//        addVIewTouch(R.id.btn_right, "r");

//        findViewById(R.id.btn_top).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float y = btn_shangceng.getTranslationY();
//                btn_shangceng.setTranslationY(y - 15);
//            }
//        });
//        addVIewTouch(R.id.btn_top, "t");

//        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float y = btn_shangceng.getTranslationY();
//                btn_shangceng.setTranslationY(y + 15);
//            }
//        });
//        addVIewTouch(R.id.btn_down, "d");
    }

    private void addVIewTouch(int id, final String tag) {
        findViewById(id).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setonTouch(tag, event);
                return false;
            }
        });
    }

    public void setMouseClick(int x, int y) {
        MotionEvent evenDownt = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis() + 100, MotionEvent.ACTION_DOWN, x, y, 0);
        dispatchTouchEvent(evenDownt);
        MotionEvent eventUp = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        dispatchTouchEvent(eventUp);
        evenDownt.recycle();
        eventUp.recycle();
    }

    boolean isCancel = false;

    public void setonTouch(final String tag, final MotionEvent event) {
        isCancel = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isCancel) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (tag) {
                                        case "l":
                                            float x = btn_shangceng.getX();
                                            btn_shangceng.setX(x - 15);
                                            break;
                                        case "t":
                                            float y = btn_shangceng.getTranslationY();
                                            btn_shangceng.setTranslationY(y - 15);
                                            break;
                                        case "r":
                                            x = btn_shangceng.getX();
                                            btn_shangceng.setX(x + 15);
                                            break;
                                        case "d":
                                            y = btn_shangceng.getTranslationY();
                                            btn_shangceng.setTranslationY(y + 15);
                                            break;
                                    }
                                }
                            });
                            break;
                        case MotionEvent.ACTION_UP:
                            isCancel = true;
                            break;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}


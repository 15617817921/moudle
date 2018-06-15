package com.liu.anew.activity.home;

import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.view.View;

import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MsgAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_msg;
    }

    @OnClick({R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postNetwork();

                    }
                });
                break;
            case R.id.bt_2:
                writeToExternalStorage();
                break;
            case R.id.bt_3:
                thred();
                break;
                case R.id.bt_4:
                colse();
                break;
        }
    }

    private void colse() {
        File newxmlfile = new File(Environment.getExternalStorageDirectory(), "castiel.txt");
        try {
            newxmlfile.createNewFile();
            FileWriter fw = new FileWriter(newxmlfile);
            fw.write("猴子搬来的救兵WooYun");
//            fw.close(); //我们在这里特意没有关闭 fw
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void thred() {
        TaskExecutor executor = new TaskExecutor();
        executor.executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 网络连接的操作
     */
    private void postNetwork() {
        try {
            URL url = new URL("http://www.wooyun.org");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines = null;
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 文件系统的操作
     */
    public void writeToExternalStorage() {
        File externalStorage = Environment.getExternalStorageDirectory();
        File mbFile = new File(externalStorage, "castiel.txt");
        try {
            OutputStream output = new FileOutputStream(mbFile, true);
            output.write("www.wooyun.org".getBytes());
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    class TaskExecutor {
        private  long SLOW_CALL_THRESHOLD = 500;
        public void executeTask(Runnable task) {
            long startTime = SystemClock.uptimeMillis();
            task.run();
            long cost = SystemClock.uptimeMillis() - startTime;
            if (cost > SLOW_CALL_THRESHOLD) {
                StrictMode.noteSlowCall("slowCall cost=" + cost);
            }
        }
    }

}

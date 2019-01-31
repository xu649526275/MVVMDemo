/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-8-29 下午5:30
 *
 *
 */

package debug;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.wanandroid.ui.WanHomeActivity;


/**
 * The type Qa test activity.
 *
 * @Description:
 * @Author: xuyangyang
 * @Email: xuyangyang @ebrun.com
 * @Version: V4.9.0
 * @Create: 2018 /7/5 15:15
 * @Modify:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText("组件化模块");
        setContentView(textView);
        textView.setOnClickListener((view) -> {
            Toast.makeText(MainActivity.this, "欢迎进入WanAndroid", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, WanHomeActivity.class);
            startActivity(intent);
        });

    }

}

package me.mundane.acceleratedwebview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.mundane.acceleratedwebview.R;

public class IndexActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
    
    public void yugangshuoBeforeOptimize(View view) {
        YuGangShuoWebActivity.go2YuGangShuoActivity(this, false);
    }
    
    public void yugangshuoAfterOptimize(View view) {
        YuGangShuoWebActivity.go2YuGangShuoActivity(this, true);
    }
}

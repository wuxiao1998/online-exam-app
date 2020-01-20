package edu.sandau.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.online_exam_app.R;

public class MeanActivity extends Activity {
    private EditText account,password;
    private Button loginBtn;
    private Button backBtn;
    private TextView registerTxT;
    private Button changeUrlBtn;
    private ProgressBar pBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);

    }
}

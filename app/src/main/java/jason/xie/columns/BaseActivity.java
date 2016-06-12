package jason.xie.columns;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jason Xie on 2016/6/12.
 */

public class BaseActivity extends AppCompatActivity {

    protected void setTitle(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv = (TextView) toolbar.findViewById(R.id.tv_title);
        if(tv!=null) tv.setText(title);

        View back = findViewById(R.id.iv_toolbar_left);
        if(back!=null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}

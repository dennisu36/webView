package edu.temple.webviewlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements WebFragment.GetURL {

    //variables, widgets, etc.
    Button enterButton; 
    TextView textViewURL; 
    ArrayList <WebFragment> fragmentList = new ArrayList(); 
    Integer index; 
    ViewPager viewPager; 


    //pager adapter
    FragmentStatePagerAdapter fSpace = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterButton = findViewById(R.id.enterButton);
        textViewURL = findViewById(R.id.urlEditText);
        viewPager = findViewById(R.id.viewPager);
        fragmentList.add(WebFragment.newInstance(""));
        viewPager.setAdapter(fSpace);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textViewURL.setText(fragmentList.get(position).URL);
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                fragmentList.get(i).loadURL(textViewURL.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.move_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newId:{
                fragmentList.add(WebFragment.newInstance(""));
                fSpace.notifyDataSetChanged();
                index = fragmentList.size()-1;
                viewPager.setCurrentItem(index);
                return true;
            }
            case R.id.prev:{
                index = viewPager.getCurrentItem();
                if(index>0){
                    index--;
                    fSpace.notifyDataSetChanged();
                    viewPager.setCurrentItem(index);
                    return true;
                }else  if (index == 0){ 
                    index = fragmentList.size()-1;
                    fSpace.notifyDataSetChanged();
                    viewPager.setCurrentItem(index);
                    return true;
                }
            }
            case R.id.forw:{
                index = viewPager.getCurrentItem();
                if(index < fragmentList.size()-1){
                    index++;
                    fSpace.notifyDataSetChanged();
                    viewPager.setCurrentItem(index);
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void getURL(String Current, WebView Success) {
        if(fragmentList.get(viewPager.getCurrentItem()).webView == Success) textViewURL.setText(Current);
    }
}
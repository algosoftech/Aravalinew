package com.ais.eduworld.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.ais.eduworld.R;

public class FilterActivity extends AppCompatActivity {
    ArrayList<String> sliderlist;
    private ImageView[] dots;
    private LinearLayout indicator;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setTitle("Filter Images");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sliderlist = new ArrayList<>();
        sliderlist = getIntent().getStringArrayListExtra("FilterList");
        System.out.println("lsize--->"+sliderlist.size());
         viewPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        viewPager.setAdapter(new ViewPagerAdapter(sliderlist));
        displayProductimage(sliderlist);

      //  mIndicator.setViewPager(viewPager);
    }

    public void displayProductimage(final ArrayList<String> slist) {
      //  itemname.setText(productDetailsBean.getProductName());
        dots = new ImageView[slist.size()];

        for (int i = 0; i < slist.size(); i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < slist.size(); i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public class ViewPagerAdapter extends PagerAdapter {
        ArrayList<String> getimglist = new ArrayList<>();

        public ViewPagerAdapter(ArrayList<String> getimglist) {
            this.getimglist = getimglist;
        }

        @Override
        public int getCount() {
            return (null != getimglist) ? getimglist.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = LayoutInflater.from(FilterActivity.this);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.viewpager_item, container, false);
            ImageView iconView = layout.findViewById(R.id.demoView);
            Picasso.with(getApplicationContext())
                    .load(getimglist.get(position))
//                    .apply(RequestOptions.placeholderOf(R.drawable.flowers)
//                            .error(R.drawable.flowers).override(512,512))
                    .into(iconView);


            container.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}

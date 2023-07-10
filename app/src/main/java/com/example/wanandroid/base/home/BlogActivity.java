package com.example.wanandroid.base.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.person.PersonFragment;
import com.example.wanandroid.base.sqaure.SquareFragment;

/**
 * @className BlogActivity
 * @description 通过container包含博客，发现和个人三个界面
 * @author Voyager 
 */
public class BlogActivity extends AppCompatActivity {

    Fragment homeFragment;
    Fragment squareFragment;
    Fragment personFragment;
    FragmentManager fragmentManager;
    /**
     * 被选中的按钮的序号
     */
    private int selectedTab =1;
    FragmentContainerView fragmentContainerView;
    LinearLayout homeLayout,squareLayout,personLayout;
    ImageView homeImageView,personImageView,squareImageView;
    TextView homeTextView,personTextView,squareTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeTestFragment();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, homeFragment, "HomeFragment")
                .commit();
        iniView();
    }

    private void iniView() {
        fragmentContainerView = (FragmentContainerView) findViewById(R.id.fragment_container);
        homeLayout = (LinearLayout) findViewById(R.id.homeLayout);
        squareLayout = findViewById(R.id.squareLayout);
        personLayout = findViewById(R.id.personLayout);
        homeImageView = findViewById(R.id.homeImageView);
        personImageView = findViewById(R.id.personImageView);
        squareImageView = findViewById(R.id.squareImageView);
        homeTextView = findViewById(R.id.homeTextView);
        personTextView = findViewById(R.id.personTextView);
        squareTextView = findViewById(R.id.squareTextView);

        homeLayout.setOnClickListener(v -> {
            //检查是否被选中
            if (selectedTab!=1){

                //设置界面为HomeFragment
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.fragment_container,HomeFragment.class,null)
//                        .addToBackStack(null)
//                        .commit();


                homeFragment = fragmentManager.findFragmentByTag("HomeFragment");
                if (homeFragment == null) {
                    homeFragment = new HomeTestFragment();
                    fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.fragment_container, homeFragment, "HomeFragment")
                                .commit();
                } else {
                    if(personFragment!=null&&squareFragment==null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(personFragment)
                                .show(homeFragment)
                                .commit();
                    }    else if(squareFragment!=null&&personFragment!=null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(squareFragment)
                                .hide(personFragment)
                                .show(homeFragment)
                                .commit();
                    }else {
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(squareFragment)
                                .show(homeFragment)
                                .commit();
                    }

                }
//                Intent intent2=new Intent(BlogActivity.this,SquareActivity.class);
//                startActivity(intent2);
                //设置其他按钮为未选中状态
                personTextView.setVisibility(View.GONE);
                squareTextView.setVisibility(View.GONE);

                personImageView.setImageResource(R.drawable.person);
                squareImageView.setImageResource(R.drawable.square);


                personLayout.setBackgroundColor(getResources().getColor(R.color.white));
                squareLayout.setBackgroundColor(getResources().getColor(R.color.white));

                //设置home按钮的选中状态
                homeTextView.setVisibility(View.VISIBLE);
                homeImageView.setImageResource(R.drawable.home_selected);
                homeLayout.setBackgroundResource(R.drawable.home_round_100);

                //添加动画
                ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                homeLayout.startAnimation(scaleAnimation);

                selectedTab=1;
            }
        });
        squareLayout.setOnClickListener(v -> {
            //检查是否被选中
            if (selectedTab!=2){

                //设置界面为LikeFragment

                squareFragment = fragmentManager.findFragmentByTag("SquareFragment");
                if (squareFragment == null) {
                    squareFragment = new SquareFragment();
                    if(personFragment!=null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .hide(personFragment)
                                .add(R.id.fragment_container, squareFragment, "SquareFragment")
                                .commit();
                    }else {
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .add(R.id.fragment_container, squareFragment, "SquareFragment")
                                .commit();
                    }
                } else {
                    if(personFragment!=null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .hide(personFragment)
                                .show(squareFragment)
                                .commit();
                    }else {
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .show(squareFragment)
                                .commit();
                    }

                }
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.fragment_container,SquareFragment.class,null)
//                        .addToBackStack(null)
//                        .commit();


                //设置其他按钮为未选中状态
                homeTextView.setVisibility(View.GONE);
                personTextView.setVisibility(View.GONE);

                homeImageView.setImageResource(R.drawable.home);
                personImageView.setImageResource(R.drawable.person);

                homeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                personLayout.setBackgroundColor(getResources().getColor(R.color.white));

                //设置like按钮的选中状态
                squareTextView.setVisibility(View.VISIBLE);
                squareImageView.setImageResource(R.drawable.square_selected);
                squareLayout.setBackgroundResource(R.drawable.square_round_100);

                //添加动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                squareLayout.startAnimation(scaleAnimation);
                selectedTab = 2;
            }


        });
        personLayout.setOnClickListener(v -> {
            //检查是否被选中
            if (selectedTab!=3){

                //设置界面为LikeFragment

                personFragment = fragmentManager.findFragmentByTag("personFragment");
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    if(squareFragment!=null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .hide(squareFragment)
                                .add(R.id.fragment_container, personFragment, "personFragment")
                                .commit();
                    }else {
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .add(R.id.fragment_container, personFragment, "personFragment")
                                .commit();
                    }

                } else {
                    if(squareFragment!=null){
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .hide(squareFragment)
                                .show(personFragment)
                                .commit();
                    }else {
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .hide(homeFragment)
                                .show(personFragment)
                                .commit();
                    }

                }
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.fragment_container,SquareFragment.class,null)
//                        .addToBackStack(null)
//                        .commit();


                //设置其他按钮为未选中状态
                homeTextView.setVisibility(View.GONE);
                squareTextView.setVisibility(View.GONE);

                homeImageView.setImageResource(R.drawable.home);
                squareImageView.setImageResource(R.drawable.square);

                homeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                squareLayout.setBackgroundColor(getResources().getColor(R.color.white));

                personTextView.setVisibility(View.VISIBLE);
                personImageView.setImageResource(R.drawable.person_selected);
                personLayout.setBackgroundResource(R.drawable.person_round_100);

                //添加动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                personLayout.startAnimation(scaleAnimation);
                selectedTab = 3;
            }

        });
    }

}
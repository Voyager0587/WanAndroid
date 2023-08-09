package com.example.wanandroid.base.person;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.MainActivity;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.bean.UserDataBean;
import com.example.wanandroid.sharedPreference.SaveAccount;
import com.example.wanandroid.utils.HttpUtils;
import com.example.wanandroid.utils.ImageFilter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Voyager
 * @className PersonFragment
 * @description 个人界面
 * @createTime
 */
public class PersonFragment extends Fragment {
    RelativeLayout info_layout, logout_layout,website_layout;
    ImageView iv_bg,collectArticle,message;
    TextView coinCount,rank,publicName;

    public PersonFragment() {
        // Required empty public constructor
    }
//TODO 消息外面显示多少未读，里面的话先加载未读消息再加载已读消息，记住UI模仿
//TODO 登出要添加“是否确定注销”对话框


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        collectArticle = view.findViewById(R.id.collectArticle);
        message = view.findViewById(R.id.message);
        iv_bg=view.findViewById(R.id.iv_bg);
        info_layout = view.findViewById(R.id.info_layout);
        logout_layout = view.findViewById(R.id.logout_layout);
        coinCount=view.findViewById(R.id.coinCount);
        rank=view.findViewById(R.id.rank);
        publicName=view.findViewById(R.id.tv_nickname);
        website_layout=view.findViewById(R.id.website_layout);
        //TODO Activity转场
        //TODO 拓展：信息界面里面还可以加入软件分享链接或者二维码
        initListener();
        initUserData();
        initView();
        return view;

    }

    private void initView() {
        //拿到初始图
        Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.img1);
        //处理得到模糊效果的图
        Bitmap blurBitmap = ImageFilter.blurBitmap(getContext(), bmp, 25f);
        iv_bg.setImageBitmap(blurBitmap);
    }

    private  void initUserData(){
        Call<UserDataBean> call=HttpUtils.getUserService().getUserData();
        call.enqueue(new Callback<UserDataBean>() {
            @Override
            public void onResponse(@NonNull Call<UserDataBean> call, @NonNull Response<UserDataBean> response) {
                if(response.isSuccessful()){
                    UserDataBean userDataBean=response.body();
                    assert userDataBean != null;
                    UserDataBean.DataBean.CoinInfoBean coinInfoBean=userDataBean.getData().getCoinInfo();
                    UserDataBean.DataBean.UserInfoBean userInfoBean=userDataBean.getData().getUserInfo();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                publicName.setText(userInfoBean.getPublicName());
                                coinCount.setText(String.valueOf(coinInfoBean.getCoinCount()));
                                rank.setText(String.valueOf(coinInfoBean.getRank()));
                            }
                        });
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDataBean> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),"网络问题",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        message.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MessageActivity.class);
            requireActivity().startActivity(intent);
        });
        website_layout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), WebsiteActivity.class);
            requireActivity().startActivity(intent);
        });
        collectArticle.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CollectArticleActivity.class);
            requireActivity().startActivity(intent);
        });

        info_layout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "学习并创建属于你自己的博客！", Toast.LENGTH_SHORT).show();
        });

        logout_layout.setOnClickListener(v -> {
            new XPopup.Builder(getContext())
                    .asConfirm("提醒", "    是否确认注销？",
                            "关闭", "确定",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    Toast.makeText(getContext(), "确认注销",Toast.LENGTH_SHORT).show();
                                    Call<MessageBean> logout = HttpUtils.getUserService().logout();
                                    logout.enqueue(new Callback<MessageBean>() {
                                        @Override
                                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                                            if (response.isSuccessful()) {
                                                assert response.body() != null;
                                                if (response.body().getErrorCode() == 0) {
                                                    Toast.makeText(getContext(), "注销成功", Toast.LENGTH_SHORT).show();
                                                    SaveAccount.clearUpUserData(requireContext());
                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                    requireContext().startActivity(intent);
                                                    requireActivity().finish();
                                                } else {
                                                    Toast.makeText(getContext(), "注销失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }, null,false,R.layout.my_confim_popup)
                    .show();





        });

    }
}
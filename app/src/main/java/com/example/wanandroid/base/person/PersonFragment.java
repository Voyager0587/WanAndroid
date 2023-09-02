package com.example.wanandroid.base.person;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.MainActivity;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.CommentCountBean;
import com.example.wanandroid.bean.ImageUriBean;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.bean.UserDataBean;
import com.example.wanandroid.sharedPreference.SaveAccount;
import com.example.wanandroid.utils.HttpUtils;
import com.example.wanandroid.utils.ImageFilter;
import com.google.android.material.imageview.ShapeableImageView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import org.litepal.LitePal;

import java.io.IOException;

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
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    RelativeLayout info_layout, logout_layout, website_layout,wxArticle_layout;
    ImageView iv_bg, collectArticle, message;
    TextView coinCount, rank, publicName,message_count;
    ShapeableImageView userImageView;

    public PersonFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        collectArticle = view.findViewById(R.id.collectArticle);
        message = view.findViewById(R.id.message);
        iv_bg = view.findViewById(R.id.iv_bg);
        userImageView = view.findViewById(R.id.iv_user);
        info_layout = view.findViewById(R.id.info_layout);
        logout_layout = view.findViewById(R.id.logout_layout);
        coinCount = view.findViewById(R.id.coinCount);
        rank = view.findViewById(R.id.rank);
        publicName = view.findViewById(R.id.tv_nickname);
        website_layout = view.findViewById(R.id.website_layout);
        message_count = view.findViewById(R.id.message_count);
        wxArticle_layout = view.findViewById(R.id.wxArticle_layout);

        initListener();
        initUserData();
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;

    }

    private void initView() throws IOException {
        ImageUriBean imageUriBean=LitePal.findLast(ImageUriBean.class);
        Bitmap bitmap;
        if(imageUriBean!= null){
            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), Uri.parse(imageUriBean.getImageUri()));
        }else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
        }
        //处理得到模糊效果的图
        Bitmap blurBitmap = ImageFilter.blurBitmap(getContext(), bitmap, 25f);
        iv_bg.setImageBitmap(blurBitmap);
        userImageView.setImageBitmap(bitmap);
    }

    private void initUserData() {
        Call<UserDataBean> call = HttpUtils.getUserService().getUserData();
        call.enqueue(new Callback<UserDataBean>() {
            @Override
            public void onResponse(@NonNull Call<UserDataBean> call, @NonNull Response<UserDataBean> response) {
                if (response.isSuccessful()) {
                    UserDataBean userDataBean = response.body();
                    assert userDataBean != null;
                    UserDataBean.DataBean.CoinInfoBean coinInfoBean = userDataBean.getData().getCoinInfo();
                    UserDataBean.DataBean.UserInfoBean userInfoBean = userDataBean.getData().getUserInfo();
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
                Toast.makeText(getActivity(), "网络问题", Toast.LENGTH_SHORT).show();
            }
        });

        HttpUtils.getwAndroidService().getUnreadCommentsCount().enqueue(new Callback<CommentCountBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<CommentCountBean> call, @NonNull Response<CommentCountBean> response) {
                if(response.body()!=null){
                    CommentCountBean commentCountBean=response.body();
                    if(commentCountBean.getData()==0){
                        message_count.setText("");
                        message_count.setVisibility(View.GONE);
                    }else {
                        message_count.setVisibility(View.VISIBLE);
                        message_count.setText(""+commentCountBean.getData());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentCountBean> call, @NonNull Throwable t) {

            }
        });

    }

    private void initListener() {
        userImageView.setOnClickListener(v -> {
            if (checkPermission()) {
                openGallery();
            } else {
                requestPermission();
            }
        });
        wxArticle_layout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), WXAccountActivity.class);
            requireActivity().startActivity(intent);
        });
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
                    .asConfirm("提醒", "   是否确认注销？",
                            "关闭", "确定",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
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
                                            Toast.makeText(getContext(), "网络问题", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }, null, false, R.layout.my_confim_popup)
                    .show();


        });

    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + requireActivity().getPackageName()));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(requireContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                LitePal.deleteAll(ImageUriBean.class);
                ImageUriBean imageUriBean1=new ImageUriBean(selectedImageUri.toString());
                imageUriBean1.save();
                //处理得到模糊效果的图
                Bitmap blurBitmap = ImageFilter.blurBitmap(getContext(), bitmap, 25f);
                iv_bg.setImageBitmap(blurBitmap);
                userImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
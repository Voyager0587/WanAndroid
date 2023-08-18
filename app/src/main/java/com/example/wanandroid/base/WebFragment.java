package com.example.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

/**
 * @author Voyager
 * @title WebFragment
 * @description 显示文章网页界面的
 * @updateTime 2023/6/28 19:24
 */

public class WebFragment extends Fragment {
    AgentWeb mAgentWebView;
    LinearLayout layout;
    public String strWebURL;

    public WebFragment(String strWebURL) {
        this.strWebURL = strWebURL;
    }

    public WebFragment() {
    }

    @NonNull
    public static WebFragment newInstance(String url) {
        WebFragment myFragment = new WebFragment(url);
        Bundle args = new Bundle();
        args.putString("Web", url);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        layout = view.findViewById(R.id.web_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            mAgentWebView = AgentWeb.with(this)
                    .setAgentWebParent((ViewGroup) view.findViewById(R.id.web_layout), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                    .useDefaultIndicator(-1, 3)
                    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                    .interceptUnkownUrl()
                    .createAgentWeb()
                    .ready()
                    .go(strWebURL);
        }

    }

    @Override
    public void onPause() {
        mAgentWebView.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWebView.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWebView.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }


}
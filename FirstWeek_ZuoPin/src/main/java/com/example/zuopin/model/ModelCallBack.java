package com.example.zuopin.model;

import com.example.zuopin.bean.Bean;

/**
 * model类接口，成功和失败的方法
 */
public interface ModelCallBack {
    public void onSuccess(Bean bean);
    public void onFailure(Exception e);
}

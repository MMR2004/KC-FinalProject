package com.example.final_project.listener;

import com.example.final_project.models.MyCartModel;

import java.util.List;

public interface IResinLoadListener {

    void onResinLoadSuccess(List<MyCartModel> myCartModelList);
    void onResinLoadFailed(String message);

}

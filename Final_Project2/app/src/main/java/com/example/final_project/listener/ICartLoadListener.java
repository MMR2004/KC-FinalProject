package com.example.final_project.listener;

import com.example.final_project.models.CartModel;
import com.example.final_project.models.MyCartModel;

import java.util.List;

public interface ICartLoadListener {

    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);

}

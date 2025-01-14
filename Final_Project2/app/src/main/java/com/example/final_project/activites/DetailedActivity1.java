package com.example.final_project.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.models.MyCartModel;
import com.example.final_project.models.NewProductsModel;
import com.example.final_project.models.PopularProductsModel;
import com.example.final_project.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DetailedActivity1 extends AppCompatActivity {

    private Context context;
    List<MyCartModel> list;



    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNom;
    ImageView addItems, removeItems;
    ImageView img_url;

    Toolbar toolbar;

    int totalQuantity = 1;
    int totalPrice = 0;

    //New Products
    NewProductsModel newProductsModel = null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //Show All
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;


    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed1);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        firestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        img_url = findViewById(R.id.imgUrl);

        addToCart = findViewById(R.id.add_to_cart);
        buyNom = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        //New Products
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());


            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        //Popular Products
        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());


            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        //Show All Products
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());


            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        //Buy Now
        buyNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity1.this, AdressActivity.class);

                if (newProductsModel != null) {
                    intent.putExtra("item", newProductsModel);

                }
                if (popularProductsModel != null) {
                    intent.putExtra("item", popularProductsModel);

                }
                if (showAllModel != null) {
                    intent.putExtra("item", showAllModel);

                }

                startActivity(intent);
            }
        });

        //Add to Cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity < 15) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel != null) {

                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {

                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null) {

                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }


    private void addToCart() {

        String saveCurrantTime, saveCurrantDate;

        Calendar calForDate = Calendar.getInstance();


        SimpleDateFormat currantDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrantDate = currantDate.format(calForDate.getTime());

        SimpleDateFormat currantTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrantTime = currantTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrantTime);
        cartMap.put("currentDate", saveCurrantDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);





        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity1.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });



    }

}
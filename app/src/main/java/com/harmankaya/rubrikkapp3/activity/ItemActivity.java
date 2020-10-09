package com.harmankaya.rubrikkapp3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextView textItemName;
    private TextView textTextPrice;
    private TextView textDescription;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        initViews();

        //Setting up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTextView();

        buyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UsersPrefs usersPrefs = new UsersPrefs(v.getContext());
                if (usersPrefs.getToken().equals(""))
                {
                    Toast.makeText(ItemActivity.this, "You have to log in or register to buy items", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    buyItem();
                }
            }
        });
    }

    public void buyItem()
    {
        final UsersPrefs usersPrefs = new UsersPrefs(this);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = api.buyItem(usersPrefs.getToken(), getIntent().getStringExtra("id"));

        final int buyerId = getIntent().getIntExtra("buyerId", 0);

        if (buyerId == usersPrefs.getId())
        {
            Toast.makeText(ItemActivity.this, "You can't buy your own item", Toast.LENGTH_SHORT).show();
        }
        else
        {
            call.enqueue(new Callback<ResponseBody>()
            {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(ItemActivity.this, "You have successfully bought, " + getIntent().getStringExtra("itemName") + "!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ItemActivity.this, "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t)
                {
                    Toast.makeText(ItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setTextView()
    {
        textItemName.setText(getIntent().getStringExtra("itemName"));
        textTextPrice.setText(getIntent().getStringExtra("price") + "kr");
        textDescription.setText(getIntent().getStringExtra("description"));
    }

    private void initViews()
    {
        setTitle("");

        toolbar = findViewById(R.id.toolbar);
        textItemName = findViewById(R.id.textBuyItemName);
        textTextPrice = findViewById(R.id.textBuyPrice);
        textDescription = findViewById(R.id.textBuyDescription);
        buyButton = findViewById(R.id.buttonBuy);
    }
}

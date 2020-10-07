package com.harmankaya.rubrikkapp3.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.model.Item;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemFragment extends Fragment
{
    private EditText editTextItemName;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private Button buttonAddItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        initViews(view);

        buttonAddItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int price = Integer.parseInt(editTextPrice.getText().toString());
                if (editTextItemName.getText().toString().length() == 0 ||
                    editTextDescription.getText().toString().length() == 0 ||
                    price <= 0)
                {
                    Toast.makeText(getContext(), "Please type in empty fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insertItem();
                }
            }
        });

        return view;
    }

    public void insertItem()
    {
        final String itemName = editTextItemName.getText().toString();
        final String description = editTextDescription.getText().toString();
        final int price = Integer.parseInt(editTextPrice.getText().toString());

        UsersPrefs usersPrefs = new UsersPrefs(getContext());
        Item item = new Item(itemName, price, description);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<Item> call = api.addItem(usersPrefs.getToken(), item);
        call.enqueue(new Callback<Item>()
        {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(), "You have successfully added item!", Toast.LENGTH_SHORT).show();
                    Fragment newFragment = new ItemsListFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_container, newFragment).commit();
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t)
            {

            }
        });
    }

    public void initViews(View view)
    {
        editTextItemName = view.findViewById(R.id.editTextItemName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        buttonAddItem = view.findViewById(R.id.buttonAddItem);

        editTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}

package com.harmankaya.rubrikkapp3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.adapter.ItemsListAdapter;
import com.harmankaya.rubrikkapp3.model.Item;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsListFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView itemsRecView;
    private ArrayList<Item> items = new ArrayList<>();
    private ItemsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        getActivity().setTitle("Home page");

        initViews(view);

        setItemsList();

        adapter = new ItemsListAdapter(getContext());

        adapter.setItems(items);

        itemsRecView.setAdapter(adapter);
        itemsRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                setItemsList();
            }
        });

        return view;
    }


    public void setItemsList()
    {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Item>> call = api.getItems();

        call.enqueue(new Callback<List<Item>>()
        {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response)
            {
                if (response.isSuccessful())
                {
                    adapter.clear();
                    items = (ArrayList<Item>) response.body();
                    adapter.setItems(items);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong to server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t)
            {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViews(View view)
    {
        itemsRecView = view.findViewById(R.id.itemsRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_home);
    }
}

package com.harmankaya.rubrikkapp3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.activity.MainActivity;
import com.harmankaya.rubrikkapp3.model.User;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment
{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (editTextEmail.getText().toString().length() == 0 ||
                    editTextPassword.getText().toString().length() == 0)
                {
                    Toast.makeText(view.getContext(), "You have to type in the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginUser();
                }
            }
        });

        return view;
    }

    public void loginUser()
    {
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        final UsersPrefs usersPrefs = new UsersPrefs(getContext());

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = api.loginUser(email, password);

        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject json = new JSONObject(response.body().string());
                        usersPrefs.setToken(json.getString("token"));
                    }
                    catch (JSONException | IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Login failed, please type valid email/password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });

        Call<User> call2 = api.getUser(usersPrefs.getToken());
        call2.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                if(response.isSuccessful())
                {
                    usersPrefs.setName(response.body().getFirstName());
                    usersPrefs.setUserPassword(response.body().getPassword());
                    usersPrefs.setUserEmail(response.body().getEmail());

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().recreate();
                }
                else
                {
                    Toast.makeText(getContext(), "Login failed, please type valid mail/password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {

            }
        });
    }

    public void initViews(View view)
    {
        editTextEmail = view.findViewById(R.id.editTextEmailLogin);
        editTextPassword = view.findViewById(R.id.editTextPasswordLogin);
        buttonLogin = view.findViewById(R.id.buttonLogin);
    }
}

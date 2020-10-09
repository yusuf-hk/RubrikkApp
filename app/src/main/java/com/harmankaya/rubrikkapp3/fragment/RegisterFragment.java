package com.harmankaya.rubrikkapp3.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.activity.MainActivity;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment
{
    private EditText editTextName, editTextEmail, editTextPassword, editTextPasswordAgain;
    private Button buttonPickImage, buttonRegister;
    private TextView textWarningName, textWarningEmail, textWarningPassword, textWarningPasswordAgain;
    private Spinner spinnerCountries;
    private RadioGroup rgGender;
    private CheckBox agreementCheck;
    private ConstraintLayout parent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        getActivity().setTitle("Register");

        initViews(view);

        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            /** when the register button is clicked, it will checks if
             *  all fields is not empty and checks if agreement box is check.
             *  It will send data to server if fields is OK
             *  if not, then error will come up
             *
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                if (editTextName.getText().toString().length() == 0 ||
                        editTextPassword.getText().toString().length() == 0 ||
                        editTextPassword.getText().toString().length() == 0 ||
                        editTextPasswordAgain.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "invalid inputs, please type", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (agreementCheck.isChecked())
                    {
                        insertData();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please agree to agreement", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    public void insertData()
    {
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = api.registerUser(name, name, email, password);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(getActivity(), "nice", Toast.LENGTH_SHORT).show();

                    UsersPrefs usersPrefs = new UsersPrefs(getContext());
                    usersPrefs.setUserEmail(email);
                    usersPrefs.setName(name);
                    usersPrefs.setUserPassword(password);
                    try
                    {
                        JSONObject json = new JSONObject(response.body().string());
                        usersPrefs.setToken(json.getString("token"));
                    }
                    catch (JSONException | IOException e)
                    {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(), "Email is already in use!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
            }
        });
        call.cancel();
    }

    private void initViews(View view)
    {
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPasswordAgain = view.findViewById(R.id.editTextPasswordAgain);

        buttonPickImage = view.findViewById(R.id.buttonPickImage);
        buttonRegister = view.findViewById(R.id.buttonRegister);

        textWarningName = view.findViewById(R.id.textWarningName);
        textWarningEmail = view.findViewById(R.id.textWarningEmail);
        textWarningPassword = view.findViewById(R.id.textWarningPassword);
        textWarningPasswordAgain = view.findViewById(R.id.textWarningPasswordAgain);

        spinnerCountries = view.findViewById(R.id.spinnerCountry);
        rgGender = view.findViewById(R.id.rgGender);
        agreementCheck = view.findViewById(R.id.agreementCheck);
        parent = view.findViewById(R.id.parent);
    }
}

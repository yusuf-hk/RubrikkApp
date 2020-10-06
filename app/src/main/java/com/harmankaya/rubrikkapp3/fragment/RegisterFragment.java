package com.harmankaya.rubrikkapp3.fragment;

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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Register");

        initViews();

        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
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
                Toast.makeText(getActivity(), "nice", Toast.LENGTH_SHORT).show();

                UsersPrefs usersPrefs = new UsersPrefs(getContext());
                usersPrefs.setUserEmail(email);
                usersPrefs.setUserFirstName(name);
                usersPrefs.setUserLastName(name);
                usersPrefs.setUserPassword(password);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
            }
        });
    }

    private void initViews()
    {
        editTextName = getActivity().findViewById(R.id.editTextName);
        editTextEmail = getActivity().findViewById(R.id.editTextEmail);
        editTextPassword = getActivity().findViewById(R.id.editTextPassword);
        editTextPasswordAgain = getActivity().findViewById(R.id.editTextPasswordAgain);

        buttonPickImage = getActivity().findViewById(R.id.buttonPickImage);
        buttonRegister = getActivity().findViewById(R.id.buttonRegister);

        textWarningName = getActivity().findViewById(R.id.textWarningName);
        textWarningEmail = getActivity().findViewById(R.id.textWarningEmail);
        textWarningPassword = getActivity().findViewById(R.id.textWarningPassword);
        textWarningPasswordAgain = getActivity().findViewById(R.id.textWarningPasswordAgain);

        spinnerCountries = getActivity().findViewById(R.id.spinnerCountry);
        rgGender = getActivity().findViewById(R.id.rgGender);
        agreementCheck = getActivity().findViewById(R.id.agreementCheck);
        parent = getActivity().findViewById(R.id.parent);
    }
}

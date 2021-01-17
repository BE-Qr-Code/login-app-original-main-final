package com.example.loginapp;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText EditTextmoodleId, EditTextfname, EditTextlname, EditTextdept, EditTextpassword, EditTextrepassword;
    private Button register;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditTextmoodleId=view.findViewById(R.id.moodleId);
        EditTextfname=view.findViewById(R.id.fname);
        EditTextlname=view.findViewById(R.id.lname);
        EditTextdept=view.findViewById(R.id.department);
        EditTextpassword=view.findViewById(R.id.password);
        EditTextrepassword=view.findViewById(R.id.repassword);
        register = view.findViewById(R.id.signup_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void validateUserData(){
        final String moodleId = EditTextmoodleId.getText().toString();
        final  String fname = EditTextfname.getText().toString();
        final  String lname = EditTextlname.getText().toString();
        final String dept = EditTextdept.getText().toString();
        final  String password = EditTextpassword.getText().toString();
        final  String repassword = EditTextrepassword.getText().toString();

        //checking if username and password is empty
        if(TextUtils.isEmpty(moodleId) || moodleId.length()>8 ||moodleId.length()<8){
            EditTextmoodleId.setError("Please enter a valid MoodleId");
            EditTextmoodleId.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fname)){
            EditTextfname.setError("Please enter a valid First name");
            EditTextfname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(lname)){
            EditTextfname.setError("Please enter a valid Last name");
            EditTextfname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(dept)){
            EditTextdept.setError("Please enter a valid Department");
            EditTextdept.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            EditTextpassword.setError("Please enter the password");
            EditTextpassword.requestFocus();
            return;
        }
        if(password!=repassword){
            EditTextrepassword.setError("The passwords do not match!");
            EditTextrepassword.requestFocus();
            return;
        }
        registerUser(moodleId, fname, lname, dept, password);
    }

    private void registerUser(String moodleId, String fname, String lname, String dept, String password){
        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<User> call = api.register(moodleId, fname, lname, dept, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getIsSuccess() == 1){
                    String moodleId = response.body().getMoodleId();

                    Toast.makeText(getActivity(), "Registering user", Toast.LENGTH_LONG).show();
                    //create a delay of 3 seconds
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(), LoginFragment.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }
                else {
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}

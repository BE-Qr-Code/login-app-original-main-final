package com.example.loginapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText EditTextmoodleId, EditTextpassword;
    private Button login;
    private ProgressBar progressBar;

    public LoginFragment() {
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        //---------- Check if user is logged in ----------
       if(SharedPref.getInstance(getActivity()).isLoggedIn()){
            Intent intent = new Intent(getActivity(), HomeScreen.class);
            startActivity(intent);
        }

       View view = inflater.inflate(R.layout.fragment_login, container, false);

       EditTextmoodleId = view.findViewById(R.id.moodleId);
       EditTextpassword = view.findViewById(R.id.password);
       login = view.findViewById(R.id.login_button);
       progressBar = view.findViewById(R.id.progress_circular);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditTextmoodleId.setError(null);
               EditTextpassword.setError(null);

               validateUserData();
           }
       });
       // Inflate the layout for this fragment
       return view;
   }

    private void validateUserData(){
        final String moodleId = EditTextmoodleId.getText().toString();
        final String password = EditTextpassword.getText().toString();

        //checking if username and password is empty
        if(TextUtils.isEmpty(moodleId) || moodleId.length()>8 ||moodleId.length()<8){
            EditTextmoodleId.setError("Please enter a valid MoodleId");
            EditTextmoodleId.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            EditTextpassword.setError("Please enter the password");
            EditTextpassword.requestFocus();
            return;
        }
        //if everything is fine
        loginUser(moodleId,password);
    }

    private void loginUser(String moodleId, String password){
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<User> call = api.login(moodleId, password);

        //progress bar start
        progressBar.setVisibility(ProgressBar.VISIBLE);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getIsSuccess() == 1){

                    Log.d("moodleId_forshared: ", moodleId);

                    //------------storing the user in shared preferences
                    SharedPref.getInstance(getActivity()).storeMoodleId(moodleId);

                    //progress bar end
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    Intent intent = new Intent(getActivity(), HomeScreen.class);
                    startActivity(intent);

//                    Toast.makeText(getActivity(), "Logging in", Toast.LENGTH_LONG).show();
//                    //create a delay of 3 seconds
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(getActivity(), qrScan.class);
//                            startActivity(intent);
//                        }
//                    }, 2000);
                }
                else {
                    //progress bar end
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    Toast.makeText(getActivity(), response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("error", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //progress bar end
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }
}

package com.example.scorpion1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button loginBtn;
    EditText nameR,passwordR,numberR,emailR;
    TextView accountR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        loginBtn=(Button)findViewById(R.id.loginBR);
        nameR=(EditText)findViewById(R.id.nameR);
        passwordR=(EditText)findViewById(R.id.passwordR);
        numberR=(EditText)findViewById(R.id.brojMob);
        emailR=(EditText)findViewById(R.id.emailR);
        accountR=(TextView)findViewById(R.id.textViewV);
        loginBtn.setOnClickListener(this);
        accountR.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginBR:
                String name = nameR.getText().toString();
                String password = passwordR.getText().toString();
                String email = emailR.getText().toString();
                String number = numberR.getText().toString();
                if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !number.isEmpty() && isValid(email)) {
                    if (isValid(email)) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String[] field = new String[4];
                                field[0] = "name";
                                field[1] = "password";
                                field[2] = "phone_num";
                                field[3] = "email";
                                String[] data = new String[4];
                                data[0] = name;
                                data[1] = password;
                                data[2] = number;
                                data[3] = email;
                                PutData putData = new PutData("http://192.168.1.10/LoginRegister/signup.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Sign Up Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        }
                                        //Log.i("PutData", result);
                                    }
                                }
                            }


                        });
                    }


                }else{
                    Toast.makeText(Register.this, "Unesite sve potrebne podatke", Toast.LENGTH_LONG).show();
                }
                    break;
                    case R.id.textViewV:
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        break;
                }

    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
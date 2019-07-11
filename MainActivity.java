package com.example.smarthomesolutions;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static final String Extra_Text="com.example.smarthomesolutions.Extra_Text";
    private EditText userid;
    private EditText pass;
    private Button login;
    private TextView tView,signup;
    private int counter=3;
    FirebaseAuth fbAuth;
    //ProgressBar prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid=findViewById(R.id.etUserID);
        pass=findViewById(R.id.etPass);
        login=findViewById(R.id.bLogin);
        tView=findViewById(R.id.tView);
        signup=findViewById(R.id.tVNewUser);
        fbAuth = FirebaseAuth.getInstance();
//          FirebaseUser user= fbAuth.getCurrentUser();
//
//        if(user!=null){
//            finish();
//            startActivity(new Intent(MainActivity.this,SecondActivity.class));
//        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegistrationActivity2.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                validate(userid.getText().toString(),pass.getText().toString());
            }
        });

    }



    private void validate(final String user, String pass){

        fbAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent inte=new Intent(MainActivity.this,SecondActivity2.class);
                   inte.putExtra(Extra_Text,user);

                    startActivity(inte);
                }else{
                    Toast.makeText(MainActivity.this,"LOGIN FAILED",Toast.LENGTH_LONG).show();
                    counter--;
                    if(counter==0){
                        login.setEnabled(false);
                        tView.setText("Contact ADMIN for the pass");
                    }
                }
            }
        });
    }

}

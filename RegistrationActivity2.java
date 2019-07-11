package com.example.smarthomesolutions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity2 extends AppCompatActivity {
    private EditText userid,email,lampid,plantid,pass;
    private Button register;
    private TextView t;
    private FirebaseAuth mAuth;
    DatabaseReference databaseDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        setupViews();
        databaseDetails= FirebaseDatabase.getInstance().getReference("details");
        mAuth=FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.setText("HEY");
                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),pass.getText().toString().trim())
                        .addOnCompleteListener(RegistrationActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegistrationActivity2.this,"Successful",Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity2.this,"Failed",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    Toast.makeText(RegistrationActivity2.this,"Successful",Toast.LENGTH_SHORT).show();
                addData();
                Intent intent=new Intent(RegistrationActivity2.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(RegistrationActivity2.this,"Now Sign In",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setupViews(){
        userid=findViewById(R.id.etUserID);
        email=findViewById(R.id.eTEmail);
        lampid=findViewById(R.id.deviceIDLamp);
        lampid.setText("0");
        plantid=findViewById(R.id.deviceIDPlant);
        plantid.setText("0");
        t=findViewById(R.id.tVLamp);
        register=findViewById(R.id.btnRegister);
        pass=findViewById(R.id.password);
    }


private void addData(){
    String mail=email.getText().toString();
    String id1=lampid.getText().toString().trim();
    String id2=plantid.getText().toString().trim();
    if(!TextUtils.isEmpty(mail)||!TextUtils.isEmpty(id1)||!TextUtils.isEmpty(id2)){
        String id=removChar(mail);
        Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
        Details det=new Details(mail,id1,id2);
        databaseDetails.child(id).setValue(det);
        register.setText("DONE ALREADY");

    }else{
        Toast.makeText(this,"Enter all the fields",Toast.LENGTH_LONG).show();
    }
}

    public String removChar(String input){
        String result="";
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)!='@'&&input.charAt(i)!='_'&&input.charAt(i)!='.'){
                result+=input.charAt(i);
            }
        }
        return result;
    }


}

package com.example.smarthomesolutions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class GardenMaintainerFragment extends Fragment implements View.OnClickListener{
    private TextView tv,tvmail,tvdevid;
    private Button btn;
    DatabaseReference ref;
    View w;
    String topicPub,topicSub;

    MqttAndroidClient client;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        w= inflater.inflate(R.layout.fragment_gardenmaintainer1,container,false);
        String myStr="BHARAT";
        Bundle bundle=this.getArguments();
        if (bundle != null) {
            myStr = bundle.getString("my_key");
//
//            tv1=w.findViewById(R.id.textView6);
//            tv1.setText(myStr);
        }
//      else{
//           tv1=w.findViewById(R.id.textView6);
//            tv1.setText(myStr);
//        }
        String email=removChar(myStr);

        ref= FirebaseDatabase.getInstance().getReference().child("details").child(email);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       String gardenid=dataSnapshot.child("devId1").getValue().toString();
                       topicSub="IOT/SmartHomeSolutions/Sub/"+gardenid;
                       topicPub="IOT/SmartHomeSolutions/Pub/"+gardenid;
                       String emailid=dataSnapshot.child("mailId").getValue().toString();
                       tvmail=w.findViewById(R.id.textView3);
                       tvmail.setText(emailid);
                         tvdevid=w.findViewById(R.id.textView5);
                        tvdevid.setText(gardenid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn=w.findViewById(R.id.buttontest);
        btn.setOnClickListener(this);
        String clientId = MqttClient.generateClientId();
        tv=w.findViewById(R.id.textView2);
        client = new MqttAndroidClient(this.getContext(), "tcp://broker.hivemq.com:1883",clientId);



        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(getContext(),"Connected",Toast.LENGTH_LONG).show();
                    setSubscriber(topicSub);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getContext(),"Connection Failure",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                tv.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        return w;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttontest:
//                tv=w.findViewById(R.id.textView2);
//                tv.setText("BHARAT");
                pub(w,topicPub);
                break;
        }
    }
    public void pub(View v,String topic){

        String payload = "water";
        try {
            client.publish(topic, payload.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }
    public void setSubscriber(String topic){

        int qos = 1;
        try {
            client.subscribe(topic,0);

        } catch (MqttException e) {
            e.printStackTrace();
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

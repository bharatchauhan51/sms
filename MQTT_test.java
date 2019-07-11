package com.example.smarthomesolutions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT_test extends AppCompatActivity {


    MqttAndroidClient client;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_test);
        String clientId = MqttClient.generateClientId();
        t=findViewById(R.id.tView);
        client = new MqttAndroidClient(MQTT_test.this, "tcp://broker.hivemq.com:1883",clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MQTT_test.this,"Connected",Toast.LENGTH_LONG).show();
                    setSubscriber();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MQTT_test.this,"Connection Failure",Toast.LENGTH_LONG).show();
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
                t.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    public void pub(View v){
        String topic = "IOT123";
        String payload = "the payload";
        try {
            client.publish(topic, payload.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }
    public void setSubscriber(){
        String topic = "IOT123";
        int qos = 1;
        try {
            client.subscribe(topic,0);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

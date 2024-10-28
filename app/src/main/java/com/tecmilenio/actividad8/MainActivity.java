package com.tecmilenio.actividad8;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private ListView listaNumerosTelefono;
    private ArrayList<String> numerosTelefono;
    private EditText editTextMessage;
    private Button buttonSendSms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.SEND_SMS
            }, 1);
        }

        listaNumerosTelefono = findViewById(R.id.lista_numeros_telefono);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSendSms = findViewById(R.id.button_send_sms);

        numerosTelefono = new ArrayList<>(Arrays.asList("+5511347863", "+987654321"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosTelefono);
        listaNumerosTelefono.setAdapter(adapter);

        buttonSendSms.setOnClickListener(v -> sendSmsMessage());

        Log.d("MainActivity", "Lista de números configurada: " + numerosTelefono.toString());

    }

    private void sendSmsMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un mensaje", Toast.LENGTH_SHORT).show();
            return;
        }

        String phoneNumber = numerosTelefono.get(0); // Example: Send to the first number in the list
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Mensaje enviado a " + phoneNumber, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Mensaje enviado: " + message);
        } catch (Exception e) {
            Toast.makeText(this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error al enviar mensaje", e);
        }
    }

}
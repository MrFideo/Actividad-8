package com.tecmilenio.actividad8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String strMessage = "";

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String phoneNumber = msgs[i].getOriginatingAddress();
                String messageBody = msgs[i].getMessageBody();

                Log.d(TAG, "Mensaje recibido de: " + phoneNumber);
                Log.d(TAG, "Contenido del mensaje: " + messageBody);

                // Muestra un Toast solo si el mensaje proviene de un número en la lista
                if (phoneNumber != null && isNumberInList(context, phoneNumber)) {
                    Toast.makeText(context, "Nuevo mensaje de: " + phoneNumber, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Notificación enviada para el mensaje de: " + phoneNumber);
                }
            }
        }
    }

    private boolean isNumberInList(Context context, String phoneNumber) {
        // Aquí podrías buscar en la lista de números de la MainActivity (simplificación):
        ArrayList<String> numerosPermitidos = new ArrayList<>(Arrays.asList("+5511347863", "+987654321"));
        return numerosPermitidos.contains(phoneNumber);
    }
}


package com.fjavmvazquez.clientesocket;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
public class MainActivity extends AppCompatActivity {
    private TextView txtSalida;
    private EditText edtMensaje;
    private Button btnEnviar;
    String msj;
    DataInputStream entrada;
    DataOutputStream salida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSalida = findViewById(R.id.txtSalida);
        edtMensaje = findViewById(R.id.edtMensaje);
        btnEnviar = findViewById(R.id.btnMensaje);
        /*
        *  Consiste en una herramienta de desarrollo que detecta cosas que podrías
        * estar haciendo mal y te llama la atención
        * para que las corrijas. Esta herramienta aparece en el nivel de API 9.
        * */
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarCliente();
            }
        });
    }

    private void ejecutarCliente() {
        String ip = "172.25.13.20";
        int puerto = 8089;
        log("Conectado a traves de Socket: " + ip + " Puerto: " + puerto);
        try{
            /*
                Esta clase implementa sockets de clientes.
                Un socket es un end point para la comunicación entre dos máquinas.
             */

            Socket cliente = new Socket(ip, puerto);
            //Creamos un canal o puente para transferir la información
           // BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            //PrintWriter salida = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream()),true);
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());


            log("Enviado mensaje al servidor.....");
            msj = edtMensaje.getText().toString();
            //Enviamos mensaje al servidor
            salida.writeUTF(msj);
            salida.flush();
            //Mensaje devuelto por el servidor
            log("Mensaje recibido del servidor: " + entrada.read());
            cliente.close();
        }
        catch (Exception ex){
            log("Error: " + ex.toString());
        }
    }

    private void log(String cadena){
        //Muestro los datos al TextView
        txtSalida.append(cadena + "\n");
    }

}

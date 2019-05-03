package com.huangjuite.control_app;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by User on 12/21/2016.
 */

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionServ";

    private static final String appName = "wheely";

    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;


    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;
    private Boolean connected = false;

    private ConnectedThread mConnectedThread;
    private TextView connectedText;

    public BluetoothConnectionService(Context context, BluetoothDevice device, TextView textView) {
        mContext = context;
        connectedText = textView;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG, "startClient: Started.");
        mmDevice = device;
        if(connected){
            mConnectedThread.cancel();
        }
        new ConnectBT().execute();
    }

    public Boolean isConnected() {
        return connected;
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;
        private BluetoothSocket btSocket;
        private BluetoothDevice dispositivo;
        @Override
        protected  void onPreExecute () {
            mProgressDialog = ProgressDialog.show(mContext,"Connecting Bluetooth"
                    ,"Please Wait...",true);
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                dispositivo = mBluetoothAdapter.getRemoteDevice(mmDevice.getAddress());
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(MY_UUID_SECURE);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();
            } catch (IOException e) {
                ConnectSuccess = false;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            //dismiss the progressdialog when connection is established
            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            connectedText.setText("Connected to : " + mmDevice.getName());
            if (!ConnectSuccess) {
                connectedText.setText("");
                Log.d(TAG,"Connection Failed. Is it a SPP Bluetooth? Try again.");
            } else {
                startCommunication(btSocket,dispositivo);
            }

        }
    }



    /**
     Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     receiving incoming data through input/output streams respectively.
     **/
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];  // buffer store for the stream

            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                // Read from the InputStream
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                } catch (IOException e) {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
                    break;
                }
            }
        }

        //Call this from the main activity to send data to the remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                connectedText.setText("");
                connected = false;
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private void startCommunication(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "connected: Starting.");
        connected = true;
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(String string) {
        byte[] bytes = string.getBytes(Charset.defaultCharset());

        Log.d(TAG, "write: Write Called.");
        //perform the write
        if(mConnectedThread!=null)
            mConnectedThread.write(bytes);
    }

}
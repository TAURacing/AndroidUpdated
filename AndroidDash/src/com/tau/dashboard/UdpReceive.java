package com.tau.dashboard;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * UdpReceive creates runnable thread for constantly listening for UDP packets.
 * 
 * @param passedHandler
 *            A Handler connected to main UI thread, for sending UI element
 *            values to update.
 */
public class UdpReceive implements Runnable {
	private final static int PORT = 5555;
	private final String TAG = "UDPReceive";
	private Handler mainHandler;
	private String message;
	Message msg = new Message();
	Bundle bundle = new Bundle();

	public UdpReceive(Handler passedHandler) {
		mainHandler = passedHandler;
	}

	@Override
	public void run() {
		try {
			Log.d(TAG, "Opening port to listen!");
			DatagramSocket socket = new DatagramSocket(PORT);

			while (true) {
				Log.d(TAG, "Listening...");
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				message = new String(packet.getData()).trim();
				Log.d(TAG, "Received: '" + message + "'");

				msg = mainHandler.obtainMessage();

				if (message.contains("RPM")) {
					bundle.putString("RPM", message.replace("RPM:", ""));
				} else if (message.contains("OIL")) {
					bundle.putString("OIL", message.replace("OIL:", ""));
				}

				msg.setData(bundle);
				mainHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			Log.e(TAG, "Receiver error", e);
		}

	}
}
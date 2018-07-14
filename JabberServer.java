package software;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberServer {
	public static int PORT; // ポート番号を設定する。

	public static void main(String[] args) throws IOException {
		PORT = Integer.parseInt(args[0]);
		ServerSocket s = new ServerSocket(PORT); // ソケットを作成する。
		System.out.println("Started: " + s);
		try {
			Socket socket = s.accept();
			try {
				System.out.println("Connection accepted:" + socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
						true); // 送信バッファ設定
				while (true) {
					String str = in.readLine(); // データの受信
					if (str.equals("END"))
						break;
					System.out.println("Echoing :");
					out.println(str); // データの送信
				}
			} finally {
				System.out.println("closing...");
				socket.close();
			}
		} finally {
			s.close();
		}
	}
}

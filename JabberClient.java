package software;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class JabberClient {
	public static void main(String[] args) throws IOException {
		InetAddress addr = InetAddress.getByName("localhost"); // ＩＰアドレスへの変換
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, JabberServer.PORT); // ソケットの生成
		try {
			System.out.println("socket =" + socket);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true); // 送信バッファ設定
			for (int i = 0; i < 10; i++) {
				out.println("howdy" + i); // データ送信
				String str = in.readLine(); // データ受信
				System.out.println(str);
			}
			out.println("END");
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
}

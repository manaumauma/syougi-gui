package software;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class SyougiServer extends Thread {
	private static int maxConnection = 2; // 最大人数
	private static Socket socket[] = new Socket[3];
	private static BufferedReader in[] = new BufferedReader[3];
	private static PrintWriter out[] = new PrintWriter[3];
	private static SyougiServerThread ST[] = new SyougiServerThread[3];
	private static String name[] = new String[5]; // 名前

	/* ServerThreadから名前が送られてきたら登録 */
	static void setName(int n, String str) {
		name[n] = str;
	}

	/* 自分(number)以外の全員にデータを送信 */
	static void SendOthers(int number, String str) {
		for (int i = 1; i <= maxConnection; i++) {
			if (i == number)
				continue;
			out[i].println(str);
			out[i].flush();
		}
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(8080);

			/* 最大人数分受け付ける */
			for (int i = 1; i <= maxConnection; i++) {
				/* ソケット通信受け付け */
				socket[i] = server.accept();

				/* 専用のServerTHreadに通信割り振り */
				in[i] = new BufferedReader(new InputStreamReader(socket[i].getInputStream()));
				out[i] = new PrintWriter(socket[i].getOutputStream(), true);
				ST[i] = new SyougiServerThread(i, in[i]);
				ST[i].start();

				/* プレイヤー番号送信 */
				out[i].println(i);

				/* i-1人分の名前送信 */
				for (int k = 1; k < i; k++) {
					out[i].println(name[k]);
				}
				/* 名前が送られてきたら他の人たちに送信 */
				while (name[i] == null) {
					Thread.sleep(100);
				}
				for (int k = 1; k < i; k++) {
					out[k].println(name[i]);
				}
			}
		} catch (Exception ex) {
			Main.errorMessage(ex);
		}
	}
}
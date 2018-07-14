package software;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class SyougiServer extends Thread {
	private static int maxConnection = 2; // �ő�l��
	private static Socket socket[] = new Socket[3];
	private static BufferedReader in[] = new BufferedReader[3];
	private static PrintWriter out[] = new PrintWriter[3];
	private static SyougiServerThread ST[] = new SyougiServerThread[3];
	private static String name[] = new String[5]; // ���O

	/* ServerThread���疼�O�������Ă�����o�^ */
	static void setName(int n, String str) {
		name[n] = str;
	}

	/* ����(number)�ȊO�̑S���Ƀf�[�^�𑗐M */
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

			/* �ő�l�����󂯕t���� */
			for (int i = 1; i <= maxConnection; i++) {
				/* �\�P�b�g�ʐM�󂯕t�� */
				socket[i] = server.accept();

				/* ��p��ServerTHread�ɒʐM����U�� */
				in[i] = new BufferedReader(new InputStreamReader(socket[i].getInputStream()));
				out[i] = new PrintWriter(socket[i].getOutputStream(), true);
				ST[i] = new SyougiServerThread(i, in[i]);
				ST[i].start();

				/* �v���C���[�ԍ����M */
				out[i].println(i);

				/* i-1�l���̖��O���M */
				for (int k = 1; k < i; k++) {
					out[i].println(name[k]);
				}
				/* ���O�������Ă����瑼�̐l�����ɑ��M */
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
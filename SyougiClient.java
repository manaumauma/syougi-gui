package software;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SyougiClient extends Thread {
	private static int maxConnection = 2; // �ő�l��
	private static int myNumber; // �����̔ԍ�
	private static String myName; // �����̖��O
	private static String name[] = new String[3]; // ���O
	private static Socket socket;
	private static BufferedReader in;
	private static PrintWriter out;
	private static SyougiPlay frame;

	/* �R���X�g���N�^ */
	SyougiClient(String str, Socket s) {
		myName = str;
		socket = s;
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
		} catch (Exception ex) {
			Main.errorMessage(ex);
		}
	}

	public void run() {
		try {
			/* �����̖��O���M */
			out.println(myName);
			/* �����̔ԍ���M */
			myNumber = Integer.parseInt(in.readLine());

			/* �S���̖��O��M */
			for (int i = 1; i <= maxConnection; i++) {
				if (i == myNumber)
					name[i] = myName;
				else
					name[i] = in.readLine();
				Main.waitRoom(i, name[i]);
			}

			/* �Q�[���J�n */
			sleep(500);
			Main.setflag();
			String receiveData;
			String recieveNumber[];
			while (true) {
				receiveData = in.readLine();
				recieveNumber = receiveData.split("\\.");
				/* receiveNumver[0],receiveNumver[1]�̍��W�̐΂����� */
				// frame.put_stone(Integer.parseInt(recieveNumber[0]),
				// Integer.parseInt(recieveNumber[1]);
			}

		} catch (Exception ex) {
			Main.errorMessage(ex);
		}
	}

	/* "I���W.J���W"�̌`�ō��W�f�[�^�𑗐M */
	static void SendData(int x, int y) {
		out.println(String.valueOf(x) + "." + String.valueOf(y));
	}

	/* SyougiPlay�J�n */
	void OpenGame(String font) {
		frame = new SyougiPlay("�͂��ݏ���", name, myNumber, font);
		frame.setVisible(true);
	}
}

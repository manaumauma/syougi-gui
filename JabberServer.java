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
	public static int PORT; // �|�[�g�ԍ���ݒ肷��B

	public static void main(String[] args) throws IOException {
		PORT = Integer.parseInt(args[0]);
		ServerSocket s = new ServerSocket(PORT); // �\�P�b�g���쐬����B
		System.out.println("Started: " + s);
		try {
			Socket socket = s.accept();
			try {
				System.out.println("Connection accepted:" + socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // �f�[�^��M�p�o�b�t�@�̐ݒ�
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
						true); // ���M�o�b�t�@�ݒ�
				while (true) {
					String str = in.readLine(); // �f�[�^�̎�M
					if (str.equals("END"))
						break;
					System.out.println("Echoing :");
					out.println(str); // �f�[�^�̑��M
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

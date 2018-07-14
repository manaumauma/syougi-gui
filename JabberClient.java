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
		InetAddress addr = InetAddress.getByName("localhost"); // �h�o�A�h���X�ւ̕ϊ�
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, JabberServer.PORT); // �\�P�b�g�̐���
		try {
			System.out.println("socket =" + socket);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // �f�[�^��M�p�o�b�t�@�̐ݒ�
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true); // ���M�o�b�t�@�ݒ�
			for (int i = 0; i < 10; i++) {
				out.println("howdy" + i); // �f�[�^���M
				String str = in.readLine(); // �f�[�^��M
				System.out.println(str);
			}
			out.println("END");
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
}

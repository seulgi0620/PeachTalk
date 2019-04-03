package chat.client;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer extends Thread {
	private ServerSocket server;
	private Socket sock;
	private String upDir = "C:\\Download Folder";
	private ObjectInputStream ois; // ���ϸ�, ���� ���� ���� �� �ִ� ��Ʈ��
	private FileOutputStream fos; // ���Ϸ� �������� ��Ʈ�� - (������, �̹���) ���� ���´� 1����Ʈ ����� ���� / ���� ���´� 2����Ʈ ���

	public FTPServer() {
		try {
			server = new ServerSocket(7788);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		System.out.println("FTPServer Started...");
		try {
			while (true) {
				sock = server.accept();
				System.out.println("������ : " + sock.getInetAddress());
				ois = new ObjectInputStream(sock.getInputStream());
				// ������ ���ϸ��� �������� ���� ����
				String fileName = ois.readUTF(); // ���ڿ��� �ޱ� ���� readUTF ���
				System.out.println(fileName);
				String path = upDir + "/" + fileName; // ������ ����
				fos = new FileOutputStream(path); // �����ο� ������ �������� �����Ѵ�.
				System.out.println("������� ��.");

				// ������ ������ �������� ���� ����
				int input = 0, count = 0; // count�� ���� ũ�� �����ϱ� ����
				byte[] data = new byte[1024]; // 1kb��. ������ �Ϸ��� ���� �� ũ�� �൵ �ȴ�
				System.out.println("���� ���� �غ��.");

				// C:\\ ��ο� ������ ��������
				while ((input = ois.read(data)) != -1) {
					fos.write(data, 0, input);
					fos.flush();
					count += input;
					System.out.println(count + " byte ���ε���...");
				}

				if (fos != null)
					fos.close();
				if (ois != null)
					ois.close();
				if (sock != null)
					sock.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

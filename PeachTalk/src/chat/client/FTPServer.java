package chat.client;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer extends Thread {
	private ServerSocket server;
	private Socket sock;
	private String upDir = "C:\\Download Folder";
	private ObjectInputStream ois; // 파일명, 파일 등을 받을 수 있는 스트림
	private FileOutputStream fos; // 파일로 내보내는 스트림 - (동영상, 이미지) 파일 형태는 1바이트 기반이 적합 / 문자 형태는 2바이트 기반

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
				System.out.println("접속자 : " + sock.getInetAddress());
				ois = new ObjectInputStream(sock.getInputStream());
				// 서버가 파일명을 보내오는 것을 받자
				String fileName = ois.readUTF(); // 문자열을 받기 위해 readUTF 사용
				System.out.println(fileName);
				String path = upDir + "/" + fileName; // 절대경로 지정
				fos = new FileOutputStream(path); // 절대경로에 파일을 내보내어 생성한다.
				System.out.println("경로지정 됨.");

				// 서버가 파일을 보내오는 것을 받자
				int input = 0, count = 0; // count는 파일 크기 측정하기 위해
				byte[] data = new byte[1024]; // 1kb씩. 빠르게 하려면 값을 더 크게 줘도 된다
				System.out.println("파일 수신 준비됨.");

				// C:\\ 경로에 파일을 내보내기
				while ((input = ois.read(data)) != -1) {
					fos.write(data, 0, input);
					fos.flush();
					count += input;
					System.out.println(count + " byte 업로드중...");
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

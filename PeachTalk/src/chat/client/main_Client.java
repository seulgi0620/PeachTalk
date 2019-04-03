package chat.client;

import chat.client.view.LoginView2;

public class main_Client {
	public static void main(String[] args) {
		
		new LoginView2();

        // FTP 서버를 같이 시작함
		FTPServer fserver = new FTPServer();
		fserver.start();
	}
}
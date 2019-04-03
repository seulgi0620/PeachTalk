package chat.client.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FileUploadGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	Socket sock;
	ObjectOutputStream oos;
	FileInputStream fis;
	File file;
	PrintWriter pw;

	public FileUploadGUI() {
		initComponents();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		tfHost = new javax.swing.JTextField();
		tfFile = new javax.swing.JTextField();
		btFile = new javax.swing.JButton();
		btUpload = new javax.swing.JButton();
		lb = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("FileUpload");

		jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

		tfHost.setText("localhost");
		tfHost.setBorder(javax.swing.BorderFactory.createTitledBorder("업로드 할 서버 IP 주소"));

		tfFile.setBorder(javax.swing.BorderFactory.createTitledBorder("업로드 할 파일명"));

		btFile.setText("파일찾기");
		btFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btFileActionPerformed(evt);
			}
		});

		btUpload.setText("Upload");
		btUpload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btUploadActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(tfFile)
								.addComponent(tfHost, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(btFile, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
								.addComponent(btUpload, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(tfHost, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
								.addComponent(btUpload, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(btFile, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tfFile, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
						.addContainerGap(25, Short.MAX_VALUE)));

		lb.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(26, Short.MAX_VALUE)));

		pack();
	
		
		
	}// </editor-fold>

	JFileChooser fileDial = new JFileChooser("C:\\Download Folder"); // 파일이 저장될 기준 경로

	private void btFileActionPerformed(java.awt.event.ActionEvent evt) {

		// 파일 다이얼로그를 띄워 업로드할 파일을 선택
		fileDial.showOpenDialog(this);
		File selFile = fileDial.getSelectedFile();
		tfFile.setText(selFile.getAbsolutePath());
		String filename = selFile.getName();
		// 파일이 이미지 파일이면 lb에 미리보기를 표시
		filename = filename.toLowerCase(); // 파일 이름을 전부 소문자로 바꾸기
		if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".gif")) {
			lb.setIcon(new ImageIcon(selFile.getAbsolutePath()));
			lb.setText("");
			lb.setHorizontalAlignment(JLabel.CENTER);
		}
	}

	private void btUploadActionPerformed(java.awt.event.ActionEvent evt) {

		// 파일을 서버에 전송하는 스레드 생성 및 동작
		SenderThread tr = new SenderThread();
		tr.start();
	}
	

	class SenderThread extends Thread {

		public void run() {
			// ftpserver에 접속 (아이피, 포트번호)
			String serverip = tfHost.getText();
			
			int port = 7788;
			if (serverip == null || serverip.trim().isEmpty()) {
				JOptionPane.showMessageDialog(lb, "서버의 IP 주소를 입력하세요");
				tfHost.requestFocus();
				return;
			} // if------

			try {
				// 소켓 생성
				sock = new Socket(serverip, port);
				// 타이틀에 연결 결과 출력
				setTitle("FTP 서버 접속됨");
				// 소켓 출력스트림=>ObjectOutputStream 필터링
				// 클라이언트(OjbectInputStream)에게 파일을 보낸다
				oos = new ObjectOutputStream(sock.getOutputStream());
				// 파일 입력스트림=>FileInputStream
				// File chooser에서 선택한 파일을 읽어들인다
				file = fileDial.getSelectedFile();
				fis = new FileInputStream(file);
				// 파일명을 서버에 전송한다.
				String fname = file.getName();
				long fileSize = file.length(); // 파일 사이즈 측정
				pw = new PrintWriter(sock.getOutputStream(), true);
				oos.writeUTF(fname);
				oos.flush();
				// 파일을 읽으면서 소켓출력 스트림을 통해 파일 데이터를 내보낸다.
				int input = 0;
				long count = 0;
				byte[] data = new byte[1024];
				while ((input = fis.read(data)) != -1) {
					oos.write(data, 0, input);
					oos.flush();
					count += input;
					//new JFrameTest04();
					/*String returnValue = "";
					if (count > 1073741824)
					{
						returnValue = String.format(count+" Gb", count / 1073741824);
					}
					else if (count > 1048576)
					{
						returnValue = String.format(count+" Mb", count / 1048576);
					}
					else
					{
						returnValue = String.format(count+" Kb", count / 1024);
					}
					if (returnValue == "0 Kb")
						returnValue = "1 Kb"; // min
					System.out.println(returnValue);*/
				   System.out.println("In progress: " + count + "/"
				      + fileSize + " Byte(s) ("
				      + (count * 100 / fileSize) + " %)");
				    }

				if (pw != null)
					pw.close();
				if (oos != null)
					oos.close();
				if (fis != null)
					fis.close();
				if (sock != null)
					sock.close();
				JOptionPane.showMessageDialog(lb, "업로드 완료!");
				dispose();
				
				// MessagesNotRead.send(Protocol.file_send(chatroom_code, Protocol.file_send, user_id, " 님이", fname, " 파일을", serverip, "님에게 전송했습니다."));
			} catch (Exception e) {
				System.out.println("예외 발생 : " + e);
			}
			
		}
		
	}
	
	/*class JFrameTest04 extends JFrame{
	    JProgressBar progress;

	    public JFrameTest04(){
	        setLayout(new FlowLayout());//배치관리자 설정
	        progress=new JProgressBar();
	        //최소값이 0,최대값이 100까지 표시
	        //progress.setValue(0);//0부터 시작.시작 지점값을 
	        //표시
	        progress.setStringPainted(true);
	        //true로 설정하면 현재 진행상황을 %로 표시함.
	        add(progress);//스윙 프레임윈도우에 프로그래스바
	        //배치
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(300,150); setVisible(true);
	        progress_start();//progress_start()메서드를
	        //호출
	    }//생성자 정의
	    
	    public void progress_start(){
	        int i;
	        try{
	            for(i=0;i<=100;i++){
	                progress.setValue(i);
	                Thread.sleep(37);//밀리세컨드 단위로
	                //지연시간을 설정.
	            }
	        }catch(InterruptedException ie){
	            ie.printStackTrace();
	        }
	    }//progress_start()끝
	}
	*/

	// Variables declaration - do not modify
	private javax.swing.JButton btFile;
	private javax.swing.JButton btUpload;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lb;
	private javax.swing.JTextField tfFile;
	private javax.swing.JTextField tfHost;
	// End of variables declaration
}
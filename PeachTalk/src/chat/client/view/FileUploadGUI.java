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
		tfHost.setBorder(javax.swing.BorderFactory.createTitledBorder("���ε� �� ���� IP �ּ�"));

		tfFile.setBorder(javax.swing.BorderFactory.createTitledBorder("���ε� �� ���ϸ�"));

		btFile.setText("����ã��");
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

	JFileChooser fileDial = new JFileChooser("C:\\Download Folder"); // ������ ����� ���� ���

	private void btFileActionPerformed(java.awt.event.ActionEvent evt) {

		// ���� ���̾�α׸� ��� ���ε��� ������ ����
		fileDial.showOpenDialog(this);
		File selFile = fileDial.getSelectedFile();
		tfFile.setText(selFile.getAbsolutePath());
		String filename = selFile.getName();
		// ������ �̹��� �����̸� lb�� �̸����⸦ ǥ��
		filename = filename.toLowerCase(); // ���� �̸��� ���� �ҹ��ڷ� �ٲٱ�
		if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".gif")) {
			lb.setIcon(new ImageIcon(selFile.getAbsolutePath()));
			lb.setText("");
			lb.setHorizontalAlignment(JLabel.CENTER);
		}
	}

	private void btUploadActionPerformed(java.awt.event.ActionEvent evt) {

		// ������ ������ �����ϴ� ������ ���� �� ����
		SenderThread tr = new SenderThread();
		tr.start();
	}
	

	class SenderThread extends Thread {

		public void run() {
			// ftpserver�� ���� (������, ��Ʈ��ȣ)
			String serverip = tfHost.getText();
			
			int port = 7788;
			if (serverip == null || serverip.trim().isEmpty()) {
				JOptionPane.showMessageDialog(lb, "������ IP �ּҸ� �Է��ϼ���");
				tfHost.requestFocus();
				return;
			} // if------

			try {
				// ���� ����
				sock = new Socket(serverip, port);
				// Ÿ��Ʋ�� ���� ��� ���
				setTitle("FTP ���� ���ӵ�");
				// ���� ��½�Ʈ��=>ObjectOutputStream ���͸�
				// Ŭ���̾�Ʈ(OjbectInputStream)���� ������ ������
				oos = new ObjectOutputStream(sock.getOutputStream());
				// ���� �Է½�Ʈ��=>FileInputStream
				// File chooser���� ������ ������ �о���δ�
				file = fileDial.getSelectedFile();
				fis = new FileInputStream(file);
				// ���ϸ��� ������ �����Ѵ�.
				String fname = file.getName();
				long fileSize = file.length(); // ���� ������ ����
				pw = new PrintWriter(sock.getOutputStream(), true);
				oos.writeUTF(fname);
				oos.flush();
				// ������ �����鼭 ������� ��Ʈ���� ���� ���� �����͸� ��������.
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
				JOptionPane.showMessageDialog(lb, "���ε� �Ϸ�!");
				dispose();
				
				// MessagesNotRead.send(Protocol.file_send(chatroom_code, Protocol.file_send, user_id, " ����", fname, " ������", serverip, "�Կ��� �����߽��ϴ�."));
			} catch (Exception e) {
				System.out.println("���� �߻� : " + e);
			}
			
		}
		
	}
	
	/*class JFrameTest04 extends JFrame{
	    JProgressBar progress;

	    public JFrameTest04(){
	        setLayout(new FlowLayout());//��ġ������ ����
	        progress=new JProgressBar();
	        //�ּҰ��� 0,�ִ밪�� 100���� ǥ��
	        //progress.setValue(0);//0���� ����.���� �������� 
	        //ǥ��
	        progress.setStringPainted(true);
	        //true�� �����ϸ� ���� �����Ȳ�� %�� ǥ����.
	        add(progress);//���� �����������쿡 ���α׷�����
	        //��ġ
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(300,150); setVisible(true);
	        progress_start();//progress_start()�޼��带
	        //ȣ��
	    }//������ ����
	    
	    public void progress_start(){
	        int i;
	        try{
	            for(i=0;i<=100;i++){
	                progress.setValue(i);
	                Thread.sleep(37);//�и������� ������
	                //�����ð��� ����.
	            }
	        }catch(InterruptedException ie){
	            ie.printStackTrace();
	        }
	    }//progress_start()��
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
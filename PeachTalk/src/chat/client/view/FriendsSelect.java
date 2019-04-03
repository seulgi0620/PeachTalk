package chat.client.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chat.client.method.MyfriendsListPanel;

@SuppressWarnings("serial")
public class FriendsSelect extends JDialog{
	public JPanel panel 	= new JPanel();
	public JButton jb_startChat 	= new JButton("Ω√¿€");
	public JScrollPane jsp_myfriends = null;
	public FriendsPage fp = null;
	public MyfriendsListPanel mflp = null;
	
	
	public FriendsSelect(FriendsPage fp) {
		this.fp = fp;
		this.setLayout(null);
		
		jsp_myfriends = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		jsp_myfriends.setBounds(10,10,315,200);
		jb_startChat.setBounds(140,250,70,30);
		jb_startChat.setBackground(new Color(248,136,137));
		jb_startChat.setForeground(Color.white);
		jb_startChat.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		this.add(jb_startChat);
		mflp = new MyfriendsListPanel();
		mflp.initDisplay(fp,this);
		jsp_myfriends.setViewportView(panel);
		this.add(jsp_myfriends);
		this.getContentPane().setBackground(Color.white);
		this.setTitle("ƒ£±∏ º±≈√");
		this.setSize(350,330);
		this.setVisible(true);
	
		mflp = new MyfriendsListPanel(fp,this);
		jb_startChat.addActionListener(mflp);	
		
	}
}

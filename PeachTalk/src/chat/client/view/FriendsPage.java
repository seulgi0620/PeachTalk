package chat.client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chat.client.event.CreatingRoom;
import chat.client.method.MemLogic;
import chat.client.method.MyfriendsListPanel;
import chat.client.method.SearchFriendsListPanel;
import chat.util.DBConnectionMgr;
import chat.util.Protocol;

@SuppressWarnings("serial")
public class FriendsPage extends JPanel implements ActionListener {

	public Vector<String> v_selected = new Vector<String>();
	
	public UserMainFrame umf = null;
	public MyfriendsListPanel mflp = null;
	public SearchFriendsListPanel sflp = null;
	FriendSearch sf = null;

	public String user_id = null;
	public String user_name = null;

	DBConnectionMgr dbMgr = new DBConnectionMgr();
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

	public JButton jbtn_start_chat = new JButton("방 만들기");

	JLabel jl_icon = new JLabel();

	ImageIcon icon_search = new ImageIcon("C:\\웨딩피치img\\search.png");

	JButton jl_profile = new JButton();
	ImageIcon img5 = new ImageIcon(MemLogic.mvo.getPath());
	Image p = img5.getImage();
	Image img_profile = p.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon_profile = new ImageIcon(img_profile);
	JLabel jl_myName = new JLabel(MemLogic.mvo.getM_name());

	
	public JPanel jp_myfriends = null;
	public JScrollPane jsp_myfriends = null;
	public static HashMap<String, JPanel> friends_map = new HashMap<String, JPanel>();
	public Vector<String> friends_list = null;
	
	public static HashMap<String, JPanel> searchFriends_map = new HashMap<String, JPanel>();
	public Vector<String> searchFriends_list = null;

	
	Font f = new Font("맑은 고딕", Font.PLAIN, 20);
	Font f2 = new Font("맑은 고딕", Font.BOLD, 25);
	
	JButton jb_search = new JButton();
	ImageIcon img = new ImageIcon("C:\\웨딩피치img\\fsearch.png");
	Image p2 = img.getImage();
	Image img_search = p2.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon = new ImageIcon(img_search);
	public FriendSearch fs = null;

	public FriendsPage(UserMainFrame umf, String m_ID) {

		this.umf = umf;
		this.user_id = m_ID;
		
		jp_myfriends = new JPanel(new GridLayout());
		jsp_myfriends = new JScrollPane(jp_myfriends,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		jl_profile.addActionListener(this);
		jb_search.addActionListener(this);

		friends_list = new Vector<String>();
		mflp = new MyfriendsListPanel();
		
		searchFriends_list = new Vector<String>();
		sflp = new SearchFriendsListPanel();
		
		try {
			System.out.println("여기");
			this.umf.mnr.send(Protocol.msg("myfriends", Protocol.myfriend, m_ID, "친구 찾아줘"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		initDisplay();
		
		this.repaint();
		this.revalidate();

		CreatingRoom er = new CreatingRoom(this);
		jbtn_start_chat.addActionListener(er);
		

	}

	public void initDisplay() {
		this.setLayout(null);
		this.setBackground(Color.white);
		jbtn_start_chat.setBounds(130, 460, 150, 80);
		jbtn_start_chat.setBackground(Color.pink);
		jbtn_start_chat.setFont(f2);

		jl_profile.setBorderPainted(false);
		jl_profile.setFocusPainted(false);
		jl_profile.setContentAreaFilled(false);
		jl_profile.setBounds(40, 35, icon_profile.getIconWidth(), icon_profile.getIconHeight());
		jl_profile.setIcon(icon_profile);

		jl_myName.setBounds(120, 43, 60, 30);
		jl_myName.setFont(f);
		jb_search.setBorderPainted(false);
	    jb_search.setFocusPainted(false);
	    jb_search.setContentAreaFilled(false);
	    jb_search.setIcon(icon);
	    jb_search.setBounds(320, 50, icon.getIconWidth(), icon.getIconHeight());
	    add(jb_search);

		jsp_myfriends.setBounds(20, 120, 380, 320);
		this.setSize(400,600);
		this.setVisible(true);
		this.add(jsp_myfriends);
		jsp_myfriends.setViewportView(jp_myfriends);
		
		
		this.add(jbtn_start_chat);
		this.add(jl_profile);
		this.add(jl_myName);
	}
	
	

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj == jl_profile || obj == jl_myName) {

			Profile p = new Profile();
			p.setVisible(true);

		} 
		else if (obj == jbtn_start_chat) {

		}
		
		else if(obj==jb_search) {
			fs = new FriendSearch(this);
		}
			

	}
}

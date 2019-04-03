package chat.client.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import chat.client.event.SearchFriend;

@SuppressWarnings("serial")
public class FriendSearch extends JDialog{
	public JPanel panel 	= new JPanel();
	JLabel jl_search 		= new JLabel("ID°Ë»ö");
	public JTextField tf_search 	= new JTextField();
	ImageIcon icon_search   = new ImageIcon("src/chat/imgs/search.png");
	JLabel jl_icon 			=new JLabel();
	public JButton jb_add 	= new JButton("Ãß°¡");
	public JScrollPane jsp_myfriends = null;
	public FriendsPage fp = null;
	SearchFriend sf = null;
	
	public FriendSearch(FriendsPage fp) {
		this.fp = fp;
		this.setLayout(null);
		
		jsp_myfriends = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jl_icon.setBounds(15,45,icon_search.getIconWidth(),icon_search.getIconHeight());
		jl_icon.setIcon(icon_search);
		this.add(jl_icon);
		
		jl_search.setBounds(0,10,350,30);
		jl_search.setHorizontalAlignment(SwingConstants.CENTER);
		jl_search.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 13));
		this.add(jl_search);
		
		tf_search.setBounds(50,40,250,30);
		this.add(tf_search);
		
		//friendScroll.setBounds(10,90,315,145);
		//panel.add(friendScroll);
		jsp_myfriends.setBounds(10,90,315,145);
		jb_add.setBounds(140,250,70,30);
		jb_add.setBackground(new Color(248,136,137));
		jb_add.setForeground(Color.white);
		jb_add.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		this.add(jb_add);
		
		
		this.getContentPane().setBackground(Color.white);
		this.add(jsp_myfriends);
		this.setTitle("»ç¶÷ °Ë»ö");
		this.setSize(350,330);
		this.setVisible(true);
	
		sf = new SearchFriend(fp, this);
		tf_search.addActionListener(sf);
		jb_add.addActionListener(sf);	
		
	}


}

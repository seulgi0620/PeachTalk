package chat.client.method;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import chat.client.view.FriendsPage;
import chat.client.view.FriendsSelect;
import chat.client.view.Profile;
import chat.util.FriendsPageVO;
import chat.util.Protocol;

public class MyfriendsListPanel implements ActionListener{

	int rows = 5;

	public FriendsPage fp = null;
	FriendsSelect fs = null;
	public JLabel jl_friendName = null;
	public JLabel jl_profile = null;
	//public JCheckBox jcb_friendName = null;
	public JLabel jl_profile2 = null;
	Vector<FriendsPageVO> mflvo = null;
	ImageIcon icon_profile  = null;
	Font f = new Font("¸¼Àº °íµñ",Font.BOLD,20);  
	Vector<String> v_selected = null;
	
	
	public MyfriendsListPanel(FriendsPage fp,FriendsSelect fs) {
		this.fp = fp;
		this.fs = fs;
	}
	public MyfriendsListPanel() {
		mflvo = new Vector<FriendsPageVO>();
	}

	public MyfriendsListPanel(Vector<FriendsPageVO> mflvo) {
		this.mflvo = mflvo;
	}

	public void initDisplay(FriendsPage fp) {
		this.fp = fp;

		Vector<FriendsPageVO> vv = null;
		if (mflvo.size() != 0) {
			vv = mflvo;
		} else {
			vv = new Vector<FriendsPageVO>();
		}

		fp.friends_list.removeAllElements();
		FriendsPage.friends_map.clear();
		fp.jp_myfriends.removeAll();

		for (int i = 0; i < mflvo.size(); i++) {

			JPanel jp_friendsList = new JPanel();
			jp_friendsList.setLayout(new BorderLayout());
			jp_friendsList.setBackground(Color.pink);
			Border bd = new LineBorder(Color.BLACK);
			jp_friendsList.setBorder(bd);
			jl_friendName = new JLabel("",JLabel.CENTER);
			jl_profile = new JLabel();
			
			String path = vv.get(i).getUser_profile();
			icon_profile   = new ImageIcon(path);
			Image img_profile = icon_profile.getImage();
			Image img_profile2 = img_profile.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon icon_profile2 = new ImageIcon(img_profile2);
			jp_friendsList.add("Center",jl_friendName);
			jl_profile.setIcon(icon_profile2);
			jp_friendsList.add("West",jl_profile);
			jl_friendName.setText(String.valueOf(vv.get(i).getFriendName()));
			jl_friendName.setOpaque(true);
			jl_friendName.setBackground(new Color(255,120,120));
			jl_friendName.setFont(f);
			String name = String.valueOf(vv.get(i).getFriendName());
			jl_profile.addMouseListener(new MouseAdapter() {
				@Override
			      public void mouseClicked(MouseEvent e) {
			    		  Profile p = new Profile(path, name);
			  				p.setVisible(true);
				    	  }
			    });
			FriendsPage.friends_map.put(vv.get(i).getFriendName(), jp_friendsList);
			fp.friends_list.add(vv.get(i).getFriendID());
			fp.jp_myfriends.add(FriendsPage.friends_map.get(vv.get(i).getFriendName()));
			
			
			if (mflvo.size() > 6) {
				rows = mflvo.size();
			}
		}
		fp.jp_myfriends.setLayout(new GridLayout(rows, 1));
	}
	
	public void initDisplay(FriendsPage fp, FriendsSelect fs) {
		this.fp = fp;
		this.fs = fs;
		for (int i = 0; i < fp.friends_list.size(); i++) {
			JPanel jp_friendsList = new JPanel();
			jp_friendsList.setLayout(new BorderLayout());
			jp_friendsList.setBackground(Color.pink);
			Border bd = new LineBorder(Color.BLACK);
			jp_friendsList.setBorder(bd);
			JCheckBox jcb_friendName = new JCheckBox();
			jl_profile2 = new JLabel();
			jp_friendsList.add("Center",jcb_friendName);
			jcb_friendName.setText(fp.friends_list.get(i));
			jcb_friendName.setOpaque(true);
			jcb_friendName.setBackground(Color.pink);
			jcb_friendName.setFont(f);
			
			jcb_friendName.addItemListener(new ItemListener() {
			      public void itemStateChanged(ItemEvent e) {
			    	  if(jcb_friendName.isSelected()==true) {
			    		 fp.v_selected.add(jcb_friendName.getText());
			    	  }
			    	  if(jcb_friendName.isSelected()==false) {
				    	fp.v_selected.remove(jcb_friendName.getText());
				    	  }
			      }
			    });
			//FriendsPage.friends_map.put(vv.get(i).getFriendName(), jp_friendsList);
			fs.panel.add(jp_friendsList);
			
			
			if (fp.friends_list.size() > 6) {
				rows = fp.friends_list.size();
			}
		}
		fs.panel.setLayout(new GridLayout(rows, 1));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == fs.jb_startChat) {
			v_selected = new Vector<String>();
			System.out.println(fp.v_selected);
			for(int i=0; i<fp.v_selected.size(); i++) {
				v_selected.add(fp.v_selected.get(i));
			}
				fp.umf.mnr.send(Protocol.msg(v_selected));
			fs.dispose();
			fp.v_selected.removeAllElements();
			v_selected.removeAllElements();
		}
	}

	

}

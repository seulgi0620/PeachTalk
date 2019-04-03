package chat.client.method;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import chat.client.event.SearchFriend;
import chat.client.view.FriendSearch;
import chat.client.view.FriendsPage;
import chat.util.FriendsPageVO;

public class SearchFriendsListPanel{

	int rows = 10;

	public FriendsPage fp = null;
	FriendSearch fs = null;
	SearchFriend sf = null;
	SearchFriendsListPanel sflp = null;
	Vector<FriendsPageVO> fpvo = null;
	public JCheckBox jcb_friendID = null;
	Vector<String> v_selected = new Vector<String>();
	ImageIcon icon_profile  = null;

	public SearchFriendsListPanel() {
		fpvo = new Vector<FriendsPageVO>();
	}

	public SearchFriendsListPanel(Vector<FriendsPageVO> fpvo) {
		this.fpvo = fpvo;
	}
	
	
	public void initDisplay(FriendsPage fp,FriendSearch fs) {
		this.fp = fp;
		this.fs = fs;
		
		Vector<FriendsPageVO> vv = null;

		if (fpvo.size() != 0) {
			vv = fpvo;
		} else {
			vv = new Vector<FriendsPageVO>();
		}

		// fp.friends_list.removeAllElements();
		FriendsPage.searchFriends_map.clear();
		fs.panel.removeAll();

		for (int i = 0; i < fpvo.size(); i++) {

			JPanel jp_friendsList = new JPanel();
			jp_friendsList.setLayout(new FlowLayout());
			jp_friendsList.setBackground(Color.pink);
			Border bd = new LineBorder(Color.BLACK);
			jp_friendsList.setBorder(bd);
			
			JCheckBox jcb_friendID = new JCheckBox();
			
			jp_friendsList.add(jcb_friendID);

			jcb_friendID.setText(String.valueOf(vv.get(i).getSearch_user_id()));
			jcb_friendID.setOpaque(true);
			jcb_friendID.setBackground(Color.pink);
			//jcb_friendID.addActionListener(this);
			jcb_friendID.addItemListener(new ItemListener() {
			      public void itemStateChanged(ItemEvent e) {
			    	  if(jcb_friendID.isSelected()==true) {
			    		 v_selected.add(jcb_friendID.getText());
			    		 sf = new SearchFriend(fp, v_selected);
			    	  }
			      }
			    });
			FriendsPage.searchFriends_map.put(vv.get(i).getSearch_user_id(), jp_friendsList);
			fp.friends_list.add(vv.get(i).getSearch_user_id());
			//fp.jp_myfriends.add(FriendsPage.searchFriends_map.get(vv.get(i).getSearch_user_id()));
			fs.panel.add(FriendsPage.searchFriends_map.get(vv.get(i).getSearch_user_id()));
			if (fpvo.size() > 6) {
				rows = fpvo.size();
			}
		}
		
		fs.panel.setLayout(new GridLayout(rows, 1));
	}

	
	

	

}

package chat.client.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import chat.client.method.SearchAddFriend;
import chat.client.method.SearchFriendsListPanel;
import chat.client.view.FriendSearch;
import chat.client.view.FriendsPage;
import chat.util.Protocol;

public class SearchFriend implements ActionListener {

	FriendsPage fp = null;

	SearchFriendsListPanel sflp = null;
	SearchAddFriend sdf = null;
	FriendSearch fs = null;

	public SearchFriend(FriendsPage fp) {
		this.fp = fp;
	}

	public SearchFriend(FriendsPage fp, Vector<String> v_selected) {
		this.fp = fp;
		fp.v_selected = v_selected;

	}
	public SearchFriend(FriendsPage fp, FriendSearch fs) {
		this.fp = fp;
		this.fs = fs;
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (fp != null) {
			if(src == fs.tf_search) {
				try {
					fs.fp.umf.mnr.send(
							Protocol.msg("SearchFriends", Protocol.search_friend, fs.fp.user_id, fs.tf_search.getText()));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			else if (src == fs.jb_add) {
				for (int i = 0; i < fp.v_selected.size(); i++) {
					fs.fp.umf.mnr.send(Protocol.msg("FriendAdd", Protocol.addFriend, fp.user_id,fp.v_selected.get(i)));
				}
				fs.dispose();
			}

		}
	}
}

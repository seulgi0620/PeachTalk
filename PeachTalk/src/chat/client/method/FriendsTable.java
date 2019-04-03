package chat.client.method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import chat.client.view.FriendsPage;
import chat.util.DBConnectionMgr;
import chat.util.FriendsPageVO;

public class FriendsTable {
	DBConnectionMgr dbMgr = new DBConnectionMgr();
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

	FriendsPage fp = null;

	public Vector<FriendsPageVO> search (String m_ID) {

		Vector<FriendsPageVO> v_mflvo = new Vector<FriendsPageVO>();
		try {
			
			conn = dbMgr.getConnection("chat_ver2");
			
			String sql = "select user_name, user_profile, user_id from chat_user where user_id in (select friend_id from CHAT_FRIEND where user_id = ? ) order by user_id ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, m_ID);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				FriendsPageVO mflvo = new FriendsPageVO();
				String friend_name = rs.getString("user_name");
				String user_profile = rs.getString("user_profile");
				String f_user_id = rs.getString("user_id");
				mflvo.setFriendName(friend_name);
				mflvo.setUser_profile(user_profile);
				mflvo.setFriendID(f_user_id);

				v_mflvo.add(mflvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return v_mflvo;
	}

	
}

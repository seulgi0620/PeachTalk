package chat.client.method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import chat.util.DBConnectionMgr;
import chat.util.FriendsPageVO;

public class SearchAddFriend {
	
	DBConnectionMgr dbMgr = new DBConnectionMgr();
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	public Vector<FriendsPageVO> friendAdd(String user_id,String friend_id) {

		Vector<FriendsPageVO> v_fpvo = new Vector<FriendsPageVO>();
		try {
			
			conn = dbMgr.getConnection("chat_ver2");
			String sql = "select user_id, user_name from chat_user  " + "  where user_id = ?  ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, friend_id);
			rs = pstm.executeQuery();

			String f_user_id = null;
			String user_name = null;
			while (rs.next()) {
				FriendsPageVO fpvo = new FriendsPageVO();
				f_user_id = rs.getString("user_id");
				user_name = rs.getString("user_name");
				fpvo.setSelected_user_id(f_user_id);
				fpvo.setSelected_user_name(user_name);
				v_fpvo.add(fpvo);
			}

			String sql1 = "insert into chat_friend (friend_id, friend_name, user_id) " + "values (?,?,?)";
			pstm = conn.prepareStatement(sql1);
			pstm.setString(1, f_user_id);
			pstm.setString(2, user_name);
			pstm.setString(3, user_id);
			pstm.executeUpdate();

			//new FriendsTable(fp,fp.user_id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return v_fpvo;
	}
	
}

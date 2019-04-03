package chat.client.method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import chat.util.DBConnectionMgr;
import chat.util.FriendsPageVO;

public class SearchTable {

	DBConnectionMgr dbMgr = new DBConnectionMgr();
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	
	public Vector<FriendsPageVO> search (String user_id, String s_ID) {
		Vector<FriendsPageVO> v_flvo = new Vector<FriendsPageVO>();
		try {

			conn = dbMgr.getConnection("chat_ver2");
			String sql = "  select user_id, user_name from chat_user  " + "  where user_id like '%' || ? || '%'  "
					+ "  and user_id != ?  " + "  minus  " + "  select friend_id , friend_name from chat_friend "
					+ "  where user_id= ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, s_ID);
			pstm.setString(2, user_id);
			pstm.setString(3, user_id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FriendsPageVO mflvo = new FriendsPageVO();
				String s_user_id = rs.getString("user_id");
				mflvo.setSearch_user_id(s_user_id);
				v_flvo.add(mflvo);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return v_flvo;
	}
	
	
}

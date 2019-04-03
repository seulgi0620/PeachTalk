package chat.util;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class FriendsPageVO implements Serializable{
	String status = "";
	//접속시 친구불러올때
	String friendName = "";
	String friendID = "";
	String user_profile ="";
	Vector<String> v_myFriend = null;
	//검색시 사람불러올때
	String search_text = "";
	Vector<String> v_search = null;
	String search_user_id = "";
	String search_user_name = "";
	//검색된 친구 선택하여 추가할때
	Vector<String> v_selected = null;
	String selected_user_id = "";
	String selected_user_name = "";
	//내친구 리스트에서 검색할때
	String search_myFriendName = "";
	String search_user_profile = "";
	Vector<String> v_searchMy = null;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendID() {
		return friendID;
	}
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	public Vector<String> getV_myFriend() {
		return v_myFriend;
	}
	public void setV_myFriend(Vector<String> v_myFriend) {
		this.v_myFriend = v_myFriend;
	}
	public String getSearch_text() {
		return search_text;
	}
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	public Vector<String> getV_search() {
		return v_search;
	}
	public void setV_search(Vector<String> v_search) {
		this.v_search = v_search;
	}
	public String getSearch_user_id() {
		return search_user_id;
	}
	public void setSearch_user_id(String search_user_id) {
		this.search_user_id = search_user_id;
	}
	public String getSearch_user_name() {
		return search_user_name;
	}
	public void setSearch_user_name(String search_user_name) {
		this.search_user_name = search_user_name;
	}
	public Vector<String> getV_selected() {
		return v_selected;
	}
	public void setV_selected(Vector<String> v_selected) {
		this.v_selected = v_selected;
	}
	public String getSelected_user_id() {
		return selected_user_id;
	}
	public void setSelected_user_id(String selected_user_id) {
		this.selected_user_id = selected_user_id;
	}
	public String getSelected_user_name() {
		return selected_user_name;
	}
	public void setSelected_user_name(String selected_user_name) {
		this.selected_user_name = selected_user_name;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public String getSearch_myFriendName() {
		return search_myFriendName;
	}
	public void setSearch_myFriendName(String search_myFriendName) {
		this.search_myFriendName = search_myFriendName;
	}
	public String getSearch_user_profile() {
		return search_user_profile;
	}
	public void setSearch_user_profile(String search_user_profile) {
		this.search_user_profile = search_user_profile;
	}
	public Vector<String> getV_searchMy() {
		return v_searchMy;
	}
	public void setV_searchMy(Vector<String> v_searchMy) {
		this.v_searchMy = v_searchMy;
	}
}

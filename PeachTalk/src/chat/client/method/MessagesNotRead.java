package chat.client.method;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chat.client.view.LoginView2;
import chat.client.view.MyChatroomList2;
import chat.client.view.TalkClient2;
import chat.client.view.UserMainFrame;
import chat.util.ChatLogVO;
import chat.util.ChatroomListVO;
import chat.util.FriendsPageVO;
import chat.util.InviteListVO;
import chat.util.ParticipentVO;
import chat.util.Protocol;

public class MessagesNotRead extends Thread{

	LastRead lr = new LastRead();
	LoginView2 lv2 = null;
	
	public boolean isStop = false;
	
	public UserMainFrame umf = null;
	
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;
	
	int message_not_read = 0;
	
	String user_id = "";
	int cont = 0;
	
	public MessagesNotRead(UserMainFrame umf) {
		
		this.umf = umf;
		this.oos = umf.oos;
		this.ois = umf.ois;
		
		this.user_id = umf.user_id;
		
	}
	
	public void send(Vector<?> vc) {
		try {
			oos.writeObject(vc);			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public void send(String msg) {
		try {
			oos.writeObject(msg);			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		while(!isStop) {			
			try {
				
				Object obj = null;
				
				String msg = null;
				String chatroom_code = null;
				String protocol = null;
				String user_id = null;
				String message = null;
				String log_time = null;
				
				Vector<ChatroomListVO> clv = null;
				Vector<ChatLogVO> cl = null;
				Vector<ParticipentVO> ptv = null;
				Vector<InviteListVO> ilv = null;
				Vector<FriendsPageVO> mflv = null;
				
				try {
					
					obj = ois.readObject();
					
					if(obj instanceof String) {
						
						msg = (String) obj;
						
					} else if(obj instanceof Vector<?>) {
						
						Vector<?> raw = (Vector<?>) obj;
						
						if(raw.firstElement() instanceof ChatroomListVO) {
							clv = (Vector<ChatroomListVO>) raw;
							umf.chatroompage.tp = new CreatePanel(clv);
							umf.chatroompage.tp.initDisplay(umf.chatroompage);
							umf.chatroompage.sp.setViewportView(umf.chatroompage.jp);
							umf.repaint();
							umf.revalidate();
							
						} else if(raw.firstElement() instanceof ChatLogVO) {
							
							cl = (Vector<ChatLogVO>) raw;
							
							for(int k=0;k<cl.size();k++) {
								if(umf.chatroompage.tc_map.get(cl.get(k).getChatroom_code()) != null) {
									/*if(cl.get(k).getMsg().substring(0, 10).equals("sendemo#do#")){
										
									}*/
									//umf.chatroompage.tc_map.get(cl.get(k).getChatroom_code()).jta_display.append(cl.get(k).getUser_id()+cl.get(k).getMsg()+cl.get(k).getTt());
									umf.chatroompage.tc_map.get(cl.get(k).getChatroom_code()).addp(cl.get(k).getUser_id(), cl.get(k).getMsg(), cl.get(k).getTt());
								}
							}
									
						} else if(raw.firstElement() instanceof ParticipentVO) {
							
							ptv = (Vector<ParticipentVO>) raw;
							
							for(int k=0;k<ptv.size();k++) {
								if(umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()) != null) {
									umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jl_list.setListData(ptv.get(0).getVc());
									cont = ptv.get(0).getVc().size();
									String tit = ptv.get(0).getVc().toString();
									System.out.println(tit);
									String title = tit.substring(1, tit.length()-1);
									if(title.length()>30) {
										String ftitle = title.substring(0, 30)+"...";
										umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_title.setText(ftitle);
									}
									else if(title.length()<=30)
									umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_title.setText(title);
									String countt = Integer.toString(cont);
									umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_count.setText(countt);;
										if(countt.equals("2")) {
											umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_photo.setIcon(new ImageIcon("src/웨딩피치img/test2.png"));
										}
										else if(countt.equals("3")) {
											umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_photo.setIcon(new ImageIcon("src/웨딩피치img/3명.png"));
										}
										else if(countt.equals("4")) {
											umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_photo.setIcon(new ImageIcon("src/웨딩피치img/4명.png"));
										}
										else if(cont>=5) {
											umf.chatroompage.tc_map.get(ptv.get(k).getChatroom_code()).jlb_photo.setIcon(new ImageIcon("src/웨딩피치img/5명.png"));
										}
											
									
								}
							}
						} else if(raw.firstElement() instanceof InviteListVO) {
							
							ilv = (Vector<InviteListVO>) raw;
							
							for(int k=0;k<ilv.size();k++) {
								if(umf.chatroompage.tc_map.get(ilv.get(k).getChatroom_code()) != null) {
									umf.chatroompage.tc_map.get(ilv.get(k).getChatroom_code()).ci.jl_invite.setListData(ilv.get(0).getVc());
								}
							}
							
						}
						
						else if (raw.firstElement() instanceof FriendsPageVO) {

							mflv = (Vector<FriendsPageVO>) raw;
							if (mflv.size() > 0) {
								String status = mflv.get(0).getStatus();
								if (status.equals("myfriend") || status.equals("addfriend")) {
									umf.friendspage.mflp = new MyfriendsListPanel(mflv);
									umf.friendspage.mflp.initDisplay(umf.friendspage);
									umf.friendspage.jsp_myfriends.setViewportView(umf.friendspage.jp_myfriends);
									umf.repaint();
									umf.revalidate();
								} else if (status.equals("search")) {
									umf.friendspage.sflp = new SearchFriendsListPanel(mflv);
									umf.friendspage.sflp.initDisplay(umf.friendspage, umf.friendspage.fs);
									umf.friendspage.fs.jsp_myfriends.setViewportView(umf.friendspage.fs.panel);
									umf.repaint();
									umf.revalidate();
								}

							}
						}
						

					}						

					StringTokenizer st = null;
					if(msg != null) {
						st = new StringTokenizer(msg, Protocol.seperator);
						
						chatroom_code = st.nextToken();
						protocol = st.nextToken();
						user_id = st.nextToken();
						message = st.nextToken();
						try {
							log_time = st.nextToken();
						} catch(NoSuchElementException e) {
							log_time = "0";
						}
						
						if(Protocol.exit_room.equals(protocol)) {
							
							lr.updateLastRead(umf.user_id, chatroom_code);
							Protocol.onair_chatroom.remove(chatroom_code);

							umf.mnr.send(Protocol.msg(chatroom_code, Protocol.exit_room, user_id, "participent"));
							
						} else if(Protocol.create_room.equals(protocol)) {
							
							if(message.equals("me")) {
								//TalkClient tc = new TalkClient(umf, chatroom_code, user_id, "ㅋㅋ;");
								TalkClient2 tc = new TalkClient2(umf, chatroom_code, user_id, "ㅋㅋ;");
								umf.chatroompage.tc_map.put(chatroom_code, tc);
								
								Protocol.onair_chatroom.add(chatroom_code);
								
								umf.mnr.send(Protocol.msg(chatroom_code, Protocol.enter_room, user_id, "log"));
								umf.mnr.send(Protocol.msg(chatroom_code, Protocol.enter_room, user_id, "participent"));
							}

						} else if(Protocol.invite_user.equals(protocol)) {
							
							umf.mnr.send(Protocol.msg(chatroom_code, Protocol.enter_room, user_id, "participent"));
							
						}
						else if (Protocol.overlap.equals(protocol)) {
							try {
								isStop = true;
								ois.close();
								oos.close();
								//umf.mySocket.close();
								// tct.stop();
								umf.dispose();
								lv2 = new LoginView2();
								JOptionPane.showMessageDialog(umf, "이미 로그인 되어있는 아이디", "에러", JOptionPane.ERROR_MESSAGE);
							} 
							catch (IOException e) {
								e.printStackTrace();
							}
							break;
						}
						
						
						Vector<String> chatroom_list = umf.chatroompage.chatroom_list;

						for(int i=0;i<chatroom_list.size();i++) {
							if(chatroom_list.get(i).equals(chatroom_code)) {
								if(Protocol.chatroom_on.isEmpty()) {
									JPanel jp_outer = MyChatroomList2.chatroom_map.get(chatroom_code);
									JPanel jp_inner1_cnt = (JPanel) jp_outer.getComponent(2);
									JPanel jp_inner2_cnt = (JPanel) jp_inner1_cnt.getComponent(1);
									JLabel jl_inner3_cnt = (JLabel) jp_inner2_cnt.getComponent(0);
									int cnt = Integer.parseInt(jl_inner3_cnt.getText());
									jl_inner3_cnt.setText(String.valueOf(cnt+1));
									
									JPanel jp_inner1_msg = (JPanel) jp_outer.getComponent(1);
									JPanel jp_inner2_msg = (JPanel) jp_inner1_msg.getComponent(0);
									JLabel jl_inner3_msg = (JLabel) jp_inner2_msg.getComponent(0);
									jl_inner3_msg.setText(user_id+" : "+message);
									
									JPanel jp_inner1_time = (JPanel) jp_outer.getComponent(2);
									JPanel jp_inner2_time = (JPanel) jp_inner1_time.getComponent(0);
									JLabel jl_inner3_time = (JLabel) jp_inner2_time.getComponent(0);
									
									Date dateFormat = null;
									String stringFormat = null;
									try {
										dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(log_time);
										stringFormat = new SimpleDateFormat("HH:mm").format(dateFormat);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									jl_inner3_time.setText(stringFormat);
									
									jp_outer.getParent().add(jp_outer, 0);
									umf.repaint();
									umf.revalidate();
									
								} else if(!Protocol.chatroom_on.isEmpty()) {
									if(Protocol.chatroom_on.get(0).equals(chatroom_code)) {
										JPanel jp_outer = MyChatroomList2.chatroom_map.get(chatroom_code);
										JPanel jp_inner1 = (JPanel) jp_outer.getComponent(2);
										JPanel jp_inner2 = (JPanel) jp_inner1.getComponent(1);
										JLabel jl_inner3 = (JLabel) jp_inner2.getComponent(0);
										jl_inner3.setText("0");
										
										JPanel jp_inner1_msg = (JPanel) jp_outer.getComponent(1);
										JPanel jp_inner2_msg = (JPanel) jp_inner1_msg.getComponent(0);
										JLabel jl_inner3_msg = (JLabel) jp_inner2_msg.getComponent(0);
										jl_inner3_msg.setText(user_id+" : "+message);
										
										JPanel jp_inner1_time = (JPanel) jp_outer.getComponent(2);
										JPanel jp_inner2_time = (JPanel) jp_inner1_time.getComponent(0);
										JLabel jl_inner3_time = (JLabel) jp_inner2_time.getComponent(0);
										
										Date dateFormat = null;
										String stringFormat = null;
										try {
											dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(log_time);
											stringFormat = new SimpleDateFormat("HH:mm").format(dateFormat);
										} catch (ParseException e) {
											e.printStackTrace();
										}
										jl_inner3_time.setText(stringFormat);
																				
									}
								}
								
								if(umf.chatroompage.tc_map.get(chatroom_code) != null) {
									
									//TalkClient tc = umf.chatroompage.tc_map.get(chatroom_code);
									TalkClient2 tc = umf.chatroompage.tc_map.get(chatroom_code);
									
									if(protocol.equals(Protocol.enter_room) || protocol.equals(Protocol.create_room)) {

										Vector<String> vc = new Vector<String>();
										for(int l=0;l<tc.jl_list.getComponents().length;l++) {
											vc.add(String.valueOf(tc.jl_list.getComponent(l)));
										}
										vc.add(user_id);
										tc.jl_list.setListData(vc);
									
										
										
										
										//tc.jta_display.append(user_id + message + "\n");
										//tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
										tc.addp(user_id, message, log_time);
										tc.sp.getVerticalScrollBar().setValue(tc.sp.getVerticalScrollBar().getMaximum());
										
									} else if(protocol.equals(Protocol.plain_text)) {

										//tc.jta_display.append(user_id +" : "+ message + " (" + log_time + ") \n");
										//tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
										tc.addp(user_id, message, log_time);
										tc.sp.getVerticalScrollBar().setValue(tc.sp.getVerticalScrollBar().getMaximum());
										
									} else if(protocol.equals(Protocol.exit_room)) {

										Vector<String> vc = new Vector<String>();
										for(int l=0;l<tc.jl_list.getComponents().length;l++) {
											vc.add(String.valueOf(tc.jl_list.getComponent(l)));
										}
										vc.remove(user_id);
										
										tc.jl_list.removeAll();
										
										tc.jl_list.setListData(vc);
										
										tc.jl_list.revalidate();

										//tc.jta_display.append(user_id + message+"\n");
										//tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
										tc.addp(user_id, message,log_time);
										
									}
									
								}
								
							}
							
						}

					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
	}
}

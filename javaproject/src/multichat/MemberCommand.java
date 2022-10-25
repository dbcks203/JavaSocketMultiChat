package multichat;


import java.util.List;

import org.json.JSONObject;

public class MemberCommand{
	
	ChatServer chatServer;
	SocketClient sc;
	
	public MemberCommand(SocketClient sc, JSONObject jsonObject) {
		this.chatServer = sc.chatServer;
		this.sc = sc;
		String command = jsonObject.getString("memberCommand");
		switch (command) {
		case "login":
			login(jsonObject);
			break;
		case "registerMember":
			registerMember(jsonObject);
			break;
		case "passwdSearch":
			passwdSearch(jsonObject);
			break;
		case "updateMember":
			updateMember(jsonObject);
			break;
		case "memberDelete":
			memberDelete(jsonObject);
			break;
		case "memberInfo":
			memberInfo();
			break;
		}
		
	}
	
	private void memberInfo() {
		JSONObject jsonResult = new JSONObject();

		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {
			List<Member> memberList = chatServer.memberRepository.memberList;
			
			
			jsonResult.put("statusCode", "0");
			jsonResult.put("memberlist", memberList);
			jsonResult.put("message", "회원가입이 완료되었습니다.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.send(jsonResult.toString());

		sc.close();

	}

	private void memberDelete(JSONObject jsonObject) {
		Member member = new Member(jsonObject);

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {
			chatServer.findByUid(member.getUid());
			chatServer.deleteMemberInfo(member);
			jsonResult.put("statusCode", "0");
			jsonResult.put("message", "회원탈퇴가 정상적으로 이루어졌습니다.");

			sc.send(jsonResult.toString());

			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void registerMember(JSONObject jsonObject) {
		Member member = new Member(jsonObject);

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {

			chatServer.registerMember(member);
			jsonResult.put("statusCode", "0");
			jsonResult.put("message", "회원가입이 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.send(jsonResult.toString());

		sc.close();

	}

	private void updateMember(JSONObject jsonObject) {
		Member member = new Member(jsonObject);

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {
			chatServer.memberRepository.updateMember(member);
			jsonResult.put("statusCode", "0");
			jsonResult.put("message", "회원정보수정이 정상으로 처리되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.send(jsonResult.toString());

		sc.close();

	}

	private void login(JSONObject jsonObject) {
		String uid = jsonObject.getString("uid");
		String pwd = jsonObject.getString("pwd");
		JSONObject jsonResult = new JSONObject();

		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {
			Member member = chatServer.findByUid(uid);
			if (null != member && pwd.equals(member.getPwd())) {
				jsonResult.put("statusCode", "0");
				jsonResult.put("message", "로그인 성공");
				jsonResult.put("uid", uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.send(jsonResult.toString());

		sc.close();
	}

	private void passwdSearch(JSONObject jsonObject) {
		String uid = jsonObject.getString("uid");
		JSONObject jsonResult = new JSONObject();

		jsonResult.put("statusCode", "-1");
		jsonResult.put("message", "로그인 아이디가 존재하지 않습니다");

		try {
			Member member = chatServer.findByUid(uid);
			if (null != member) {
				jsonResult.put("statusCode", "0");
				jsonResult.put("message", "비밀번호 찾기 성공");
				jsonResult.put("pwd", member.getPwd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.send(jsonResult.toString());

		sc.close();
	}
}

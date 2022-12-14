package multichat;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;


public class ClientControlMember extends ChatClient {

	private static final String MEMBER_FILE_NAME = "C:\\temp\\mamber.db";
	

	public boolean login(Scanner scanner) {
		try {
			String uid;
			String pwd;
			boolean result = false;
			System.out.println("\n1. 로그인 작업");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			System.out.print("비밀번호 : ");
			pwd = scanner.nextLine();

			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "login");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);

			
			send(jsonObject.toString());

			result = loginResponse();
			
			disconnect();
			return result;


		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean loginResponse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");
		

		if (statusCode.equals("0")) {
			String uid = root.getString("uid");
			System.out.println("로그인 성공");	
			System.out.println(uid+"님이 로그인 하셨습니다.");
			return true;
		} else {
			System.out.println(message);
			return false;
		}
	}

	// 회원가입
	public void registerMember(Scanner scanner) {
		String uid;
		String pwd;
		String name;

		try {
			System.out.println("[2]회원가입");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			System.out.print("비번 : ");
			pwd = scanner.nextLine();
			System.out.print("이름 : ");
			name = scanner.nextLine();
			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "registerMember");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);
			jsonObject.put("name", name);
			String json = jsonObject.toString();

			write(json);
			send(json);

			registerMemberResonse();
			System.out.println(name+"님 환영합니다.");

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


		public void registerMemberResonse() throws Exception {
			String json = dis.readUTF();
			JSONObject root = new JSONObject(json);
			String statusCode = root.getString("statusCode");
			String message = root.getString("message");

			if (statusCode.equals("0")) {
				System.out.println("정상적으로 실행되었습니다");
			} else {
				System.out.println(message);
			}
		}

		public static void write(String str) throws Exception {

			FileOutputStream fos = new FileOutputStream(MEMBER_FILE_NAME,true);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(str);
			bw.newLine();
			bw.flush();
			bw.close();
		}
		public static String read() throws Exception {
			InputStream is = new FileInputStream(MEMBER_FILE_NAME);
			Reader reader = new InputStreamReader(is);
			char[] data = new char[10000];
			int num = reader.read(data);
			reader.close();
			String str = new String(data, 0, num);
			return str;
		}
	public void passwdSearch(Scanner scanner) {
		try {
			String uid;

			System.out.println("\n3. 비밀번호 찾기");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();

			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "passwdSearch");
			jsonObject.put("uid", uid);
			String json = jsonObject.toString();
			send(json);

			passwdSearchResponse();

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void passwdSearchResponse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");

		if (statusCode.equals("0")) {
			System.out.println("비밀번호 : " + root.getString("pwd"));
			System.out.println("정상적으로 실행되었습니다");
		} else {
			System.out.println(message);
		}
	}
	
	public void updateMember(Scanner scanner) {
		String uid;
		String pwd;
		String name;
		try {
			System.out.println("[4]회원정보수정");
			System.out.println("변경할 내용을 입력하세요.");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			System.out.print("비번 : ");
			pwd = scanner.nextLine();
			System.out.print("이름 : ");
			name = scanner.nextLine();

			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "updateMember");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);
			jsonObject.put("name", name);
			String json = jsonObject.toString();
			send(json);

			updateMemberResponse();

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMemberResponse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");

		if (statusCode.equals("0")) {
			System.out.println("회원탈퇴가 완료되었습니다");
		} else {
			System.out.println(message);
		}
	}

	public void memberDelete(Scanner scanner) {
		String uid;
		String pwd;
		String name;

		try {
			System.out.println("[5]회원탈퇴");
			System.out.print("아이디 : ");
			uid = scanner.nextLine();
			System.out.print("비번 : ");
			pwd = scanner.nextLine();
			System.out.println("이름: ");
			name = scanner.nextLine();

			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "memberDelete");
			jsonObject.put("uid", uid);
			jsonObject.put("pwd", pwd);
			jsonObject.put("name", name);
			String json = jsonObject.toString();
			send(json);

			updateMemberResponse();

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void memberDeleteResponse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");

		if (statusCode.equals("0")) {
			System.out.println("정상적으로 삭제되었습니다");
		} else {
			System.out.println(message);
		}
	}

	public void memberInfo(Scanner scanner) {


		try {
			System.out.println("[회원목록]");
			connect();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberCommand", "memberInfo");
			String json = jsonObject.toString();

			send(json);

			memberInfoResonse();
			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void memberInfoResonse() throws Exception {
		String json = dis.readUTF();
		JSONObject root = new JSONObject(json);
		String statusCode = root.getString("statusCode");
		String message = root.getString("message");
	
		
		if (statusCode.equals("0")) {
			System.out.println("정상적으로 실행되었습니다");
			System.out.println(root.get("memberlist"));
			
			System.out.println();
		} else {
			System.out.println(message);
		}
	}
	
	
}

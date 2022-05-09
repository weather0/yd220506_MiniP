package com.jyk.starbucks.service;

import java.util.Scanner;

import com.jyk.MainMenu;
import com.jyk.starbucks.vo.MembershipInfo;

public class MembershipManager {
	MembershipService dao = new MembershipServiceImple();
	MembershipInfo member = new MembershipInfo();
	Scanner scn = new Scanner(System.in);

	public void memberView() {
		System.out.println("\n─────[개인정보 조회 및 수정]─────");
		System.out.print("ID: " + member.getId());
		System.out.print("\n비밀번호입력 >> ");
		while (true) {
			String password = scn.nextLine();
			if (password.equals(dao.memberView(member).getPassword())) {
				dao.memberView(member).toString();
				while (true) {
					System.out.println("\n──────────────────────────────");
					System.out.print("1.개인정보 수정   2.메인메뉴복귀   >> ");
					int num = Integer.parseInt(scn.nextLine());
					switch (num) {
					case 1:
						memberUpdate();
						break;

					case 2:
						MainMenu.subMenu();
						break;
					}

				}
			} else {
				System.out.println("비밀번호가 불일치합니다. 다시 정확히 입력해주세요 >>");
			}
		}

	}

	public void signUp() {
		System.out.println("\n─────[멤버십 가입]─────");
		System.out.print("ID >> ");
		while (true) {
			String id = scn.nextLine();
			member.setId(id);
			if (!id.equals(dao.memberView(member).getId())) {
				break;
			} else {
				System.out.println("이미 가입된 ID입니다. 다시 입력해주세요 >>");
			}
		}
		System.out.print("비밀번호 입력  >> ");
		String password = scn.nextLine();
		System.out.print("비밀번호 재입력 >> ");
		while (true) {
			String password2 = scn.nextLine();
			if (password.equals(password2)) {
				member.setPassword(password);
				break;
			} else {
				System.out.println("비밀번호가 불일치합니다. 다시 정확히 입력해주세요 >>");
			}
		}
		System.out.print("연락처 >> ");
		member.setContact(scn.nextLine());

		int n = dao.signUp(member);
		if (n != 0) {
			System.out.println("입력 완료");
		} else {
			System.out.println("입력 실패");
		}
		MainMenu.mainMenu();

	}

	public void memberUpdate() {
		while (true) {
			System.out.println("\n─────[개인정보 수정]─────");
			System.out.print("1.연락처 수정   2.비밀번호 수정   3.조회   4.메인메뉴복귀   >> ");
			int num = Integer.parseInt(scn.nextLine());
			switch (num) {
			case 1:
				System.out.print("새 연락처 입력 >> ");
				member.setContact(scn.nextLine());
				member.setPassword(dao.memberView(member).getPassword());
				int n = dao.memberUpdate(member);
				if (n != 0) {
					System.out.println("수정 완료");
				} else {
					System.out.println("수정 실패");
				}
				break;

			case 2:
				System.out.print("새 비밀번호 입력 >> ");
				String password = scn.nextLine();
				System.out.print("비밀번호 재입력 >> ");
				while (true) {
					String password2 = scn.nextLine();
					if (password.equals(password2)) {
						member.setPassword(password);
						member.setContact(dao.memberView(member).getContact());
						break;
					} else {
						System.out.println("비밀번호가 불일치합니다. 다시 정확히 입력해주세요 >>");
					}
				}

				n = dao.memberUpdate(member);
				if (n != 0) {
					System.out.println("수정 완료");
				} else {
					System.out.println("수정 실패");
				}
				break;

			case 3:
				dao.memberView(member).toString();
				break;

			case 4:
				MainMenu.subMenu();
				break;
			}
		}

	}

	public void memberSignIn() {
		System.out.println("\n─────[로그인]─────");
		System.out.print("ID >> ");
		while (true) {
			String id = scn.nextLine();
			member.setId(id); // 가입할 때 빼고 setId메서드는 딱 여기서만 한 번 호출하여 이후 로그인 세션 유지
			if (!id.equals(dao.memberView(member).getId())) {
				System.out.println("올바른 ID를 입력해주세요 >>");
			} else
				break;
		}
		System.out.print("비밀번호입력 >> ");
		while (true) {
			String password = scn.nextLine();
			if (password.equals(dao.memberView(member).getPassword())) {
				System.out.println("로그인 성공");
				break;
			} else {
				System.out.println("비밀번호가 불일치합니다. 다시 정확히 입력해주세요 >>");
			}
		}
	}

	// 로그인 시 member에 setID해놓은 값을 다른 클래스에서도 그대로 get할 수 있도록 메서드화: 전역 로그인 세션 유지 가능
	public String accSession() {
		return member.getId();
	}

}

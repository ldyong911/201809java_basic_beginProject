package controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import service.UserService;
import service.UserServiceImpl;

//메뉴
public class Controller {
	final static int MAIN_MENU = 1; // 메인메뉴
	final static int LOGIN = 2; // 로그인
	final static int JOIN = 3; // 회원가입
	final static int FIND_ID = 4; // 아이디 찾기
	final static int FIND_PW = 5; // 비밀번호 찾기
	final static int EXIT = 0; // 종료하기

	final static int ADMIN_MENU = 6; // 관리자메뉴
	final static int NOTICE_REG = 7; // 공지사항등록
	final static int NOTICE_CHANGE = 8; // 공지사항수정
	final static int MOVIE_REG = 9; // 영화등록
	final static int THEATER_REG = 10; // 상영관등록
	final static int SEAT_REG = 11; // 좌석등록
	final static int DATE_REG = 12; // 날짜등록
	final static int USER_LIST = 13; // 회원리스트
	final static int LOGOUT = 14; // 로그아웃

	final static int USER_MENU = 15; // 유저메뉴
	final static int BUY_TICKET = 16; // 예매하기
	final static int CANCEL_TICKET = 17; // 예매취소(예매내역 보여주기)
	final static int NOTICE_VIEW = 18; // 공지사항 보기
	final static int INFO_CHANGE = 19; // 정보수정
	final static int USER_DROP = 20; // 회원탈퇴

	final static int PW_CHANGE = 21; // 비밀번호수정
	final static int PHONE_CHANGE = 22; // 핸드폰번호수정

	final static int MOVIE_SELECT = 23; // 영화선택
	final static int DATE_SELECT = 24; // 날짜시간선택
	final static int PERSON_SELECT = 25; // 인원선택
	final static int SEAT_SELECT = 26; // 좌석선택
	final static int PAY = 27; // 결제
	final static int BUY_LIST = 28; // 구매내역

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		/*
		 * 1.nextInt()인 숫자를 입력받은후 -> next()나 nextLine()인 문자를 입력받는 경우 콘솔입력을 받는게
		 * 아니라 버퍼에 있는 데이터 \n문자를 가져오게 되어 바로 문자입력을 받지않고 출력해버리기 때문에 숫자를 입력받은 직후 문자를
		 * 입력받아야 한다면 버퍼를 비워준후 입력을 받아야 함(스캐너변수.nextLine()로 한번더 입력받거나,
		 * 스캐너변수.skip("[\\r\\n]+") 두가지 방법으로 비움)
		 * 
		 * 2. try-catch시 scanner에 관한 예외처리를 할때 다시 입력받고 싶으면 scanner를 초기화해줘야 함
		 */

		boolean isContinue = true;
		UserService userService = new UserServiceImpl();

		int menu = 1;

		while (isContinue) {
			switch (menu) {
			case MAIN_MENU:
				menu = mainMenu();
				break;
			case LOGIN:
				menu = userService.login();
				break;
			case JOIN:
				menu = userService.join();
				break;
			case FIND_ID:
				menu = userService.findId();
				break;
			case FIND_PW:
				menu = userService.findPw();
				break;
			case EXIT:
				isContinue = false;
				break;
			case ADMIN_MENU:
				menu = adminMenu();
				break;
			case NOTICE_REG:
				menu = userService.noticeRegister();
				break;
			case NOTICE_CHANGE:
				menu = userService.noticeChange();
				break;
			case MOVIE_REG:
				menu = userService.movieRegister();
				break;
			case THEATER_REG:
				menu = userService.theaterRegister();
				break;
			case SEAT_REG:
				menu = userService.seatRegister();
				break;
			case DATE_REG:
				menu = userService.dateRegister();
				break;
			case USER_LIST:
				menu = userService.userList();
				break;
			case LOGOUT:
				menu = userService.logout();
				break;
			case USER_MENU:
				menu = userMenu();
				break;
			case BUY_TICKET:
				menu = userService.movieRank();
				break;
			case CANCEL_TICKET:
				menu = userService.totalBuyListPrint();
				break;
			case NOTICE_VIEW:
				menu = userService.noticeView();
				break;
			case INFO_CHANGE:
				menu = userInfoMenu();
				break;
			case USER_DROP:
				menu = userService.drop();
				break;
			case PW_CHANGE:
				menu = userService.pwChange();
				break;
			case PHONE_CHANGE:
				menu = userService.phoneChange();
				break;
			case MOVIE_SELECT:
				menu = userService.movieSelect();
				break;
			case DATE_SELECT:
				menu = userService.dateSelect();
				break;
			case PERSON_SELECT:
				menu = userService.peopleNumCheck();
				break;
			case SEAT_SELECT:
				menu = userService.seatSelect();
				break;
			case PAY:
				menu = userService.pay();
				break;
			case BUY_LIST:
				menu = userService.nowBuyListPrint();
				break;
			default:
				menu = userMenu();
				break;
			}
		}
	}// main

	static int mainMenu() {
		System.out.println("*******영화관에 오신 걸 환영합니다*******");
		System.out.println("\t1.로그인");
		System.out.println("\t2.회원가입");
		System.out.println("\t3.아이디찾기");
		System.out.println("\t4.비밀번호찾기");
		System.out.println("\t5.종료하기");
		System.out.println("********************************");

		switch (tryCatch()) {
		case 1:
			return LOGIN;
		case 2:
			return JOIN;
		case 3:
			return FIND_ID;
		case 4:
			return FIND_PW;
		case 5:
			return EXIT;
		default:
			return MAIN_MENU;
		}
	}// mainMenu

	static int adminMenu() {
		System.out.println("**************관리자*************");
		System.out.println("\t1.공지사항 등록");
		System.out.println("\t2.공지사항 수정");
		System.out.println("\t3.영화 등록");
		System.out.println("\t4.상영관 등록");
		System.out.println("\t5.날짜 등록");
		System.out.println("\t6.회원리스트");
		System.out.println("\t7.로그아웃");
		System.out.println("********************************");

		switch (tryCatch()) {
		case 1:
			return NOTICE_REG;
		case 2:
			return NOTICE_CHANGE;
		case 3:
			return MOVIE_REG;
		case 4:
			return THEATER_REG;
		case 5:
			return DATE_REG;
		case 6:
			return USER_LIST;
		case 7:
			return LOGOUT;
		default:
			return ADMIN_MENU;
		}
	}// adminMenu

	static int userMenu() {
		System.out.println("*************사용자**************");
		System.out.println("\t1.예매하기");
		System.out.println("\t2.예매확인");
		System.out.println("\t3.공지사항");
		System.out.println("\t4.정보수정");
		System.out.println("\t5.로그아웃");
		System.out.println("\t6.회원탈퇴");
		System.out.println("********************************");
		switch (tryCatch()) {
		case 1:
			return BUY_TICKET;
		case 2:
			return CANCEL_TICKET;
		case 3:
			return NOTICE_VIEW;
		case 4:
			return INFO_CHANGE;
		case 5:
			return LOGOUT;
		case 6:
			return USER_DROP;
		default:
			return USER_MENU;
		}
	}// userMenu

	static int userInfoMenu() {
		System.out.println("*************정보수정*************");
		System.out.println("\t1.비밀번호 수정");
		System.out.println("\t2.핸드폰번호 수정");
		System.out.println("********************************");
		switch (tryCatch()) {
		case 1:
			return PW_CHANGE;
		case 2:
			return PHONE_CHANGE;
		default:
			return USER_MENU;
		}
	}

	static int tryCatch() {
		int menu = 0;

		while (true) {
			try {
				menu = s.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("**숫자만 입력해주세요!**");
				s = new Scanner(System.in);
			}
		}
		return menu;
	}

}
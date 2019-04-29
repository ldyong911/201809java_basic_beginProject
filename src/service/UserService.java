package service;

//메뉴를 실행
public interface UserService {
	
	//메인메뉴
	int join(); //회원가입하기
	int login(); //로그인하기
	int findId(); //아이디찾기
	int findPw(); //비밀번호찾기
	
	//관리자메뉴
	int noticeRegister(); //공지사항 등록
	int noticeChange(); //공지사항수정
	int movieRegister(); //영화등록
	int theaterRegister(); //상영관등록
	int seatRegister(); //좌석등록
	int dateRegister(); //날짜,시간 등록
	int userList(); //유저 리스트
	
	int logout(); //로그아웃하기	
	
	//유저 메뉴
	int pwChange(); //비밀번호 수정
	int phoneChange(); //핸드폰번호 수정
	int noticeView();//공지사항
	int drop(); //회원탈퇴
	int movieRank(); //영화순위
	int movieSelect(); //영화선택
	int dateSelect(); //날짜, 시간선택
	int peopleNumCheck(); //인원선택
	int seatSelect(); //좌석선택
	int pay(); //결제
	int nowBuyListPrint(); //현재 구매내역
	int totalBuyListPrint(); //전체 구매내역

}

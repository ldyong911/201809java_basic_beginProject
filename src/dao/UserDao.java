package dao;

import java.util.ArrayList;

import vo.BuyListVO;
import vo.DateVO;
import vo.MovieVO;
import vo.NoticeVO;
import vo.SeatVO;
import vo.TheaterVO;
import vo.UserVO;

//데이터베이스 접근
public interface UserDao {
	// 유저를 불러오기
	UserVO selectUser(String key);

	// 유저를 추가하기
	void insertUser(UserVO user);

	// 유저리스트
	ArrayList<UserVO> selectUserList();

	// 공지사항 등록(db에서 공지사항 불러오기)
	void insertNotice(NoticeVO notice);

	// 공지사항수정
	ArrayList<NoticeVO> noticeChange();

	// 영화등록(db에서 영화정보 불러오기)
	void insertMovie(MovieVO movie);
	
	// 영화리스트
	ArrayList<MovieVO> movieList();

	// 상영관등록
	void insertTheater(TheaterVO theater);

	// 상영관리스트
	ArrayList<TheaterVO> theaterList();

	// 좌석등록
	void insertSeat(SeatVO seat);
	
	// 좌석리스트
	ArrayList<SeatVO> seatList();

	//날짜등록
	void dateRegister(DateVO date);
	
	//날짜선택
	ArrayList<DateVO> dateList();

	// 회원탈퇴(db에서 회원정보 불러오기)
	void deleteUser(String key);

	// 공지사항보기(db에서 정보 불러오기
	ArrayList<NoticeVO> noticeView();

	// 비밀번호 수정(db에서 정보 불러오기)
	void pwChange(String key, String pw);

	// 핸드폰번호 수정(db에서 정보 불러오기)
	void phoneChange(String key, String phone);

	// 영화순위(db에서 영화정보 불러오기)
	void movieSort(Integer key, Integer rank);

	// 영화예매(db에서 정보 불러오기)

	// 예매취소
	void movieDrop(String key, int cal);

	// 결제(db에서 영화가격을 불러와서 영화가격*인원수)
	void insertPay(BuyListVO buy);

	// 구매내역 출력(db에서 구매내역정보 불러오기)
	ArrayList<BuyListVO> buyList();
	

}
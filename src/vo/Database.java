package vo;

import java.util.ArrayList;

//데이터베이스
public class Database {
	//회원테이블
	public static ArrayList<UserVO> tb_user = new ArrayList<UserVO>();
	
	//공지사항
	public static ArrayList<NoticeVO> tb_notice = new ArrayList<NoticeVO>();
	
	//영화테이블
	public static ArrayList<MovieVO> tb_movie = new ArrayList<MovieVO>();
	
	//상영관테이블
	public static ArrayList<TheaterVO> tb_theater = new ArrayList<TheaterVO>();

	//날짜,시간 테이블
	public static ArrayList<DateVO> tb_date = new ArrayList<DateVO>();
	
	//좌석 테이블
	public static ArrayList<SeatVO> tb_seat = new ArrayList<SeatVO>();
	
	//구매내역 테이블
	public static ArrayList<BuyListVO> tb_buyList = new ArrayList<BuyListVO>();
	
}

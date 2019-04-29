package dao;

import java.util.ArrayList;

import vo.BuyListVO;
import vo.Database;
import vo.DateVO;
import vo.MovieVO;
import vo.NoticeVO;
import vo.SeatVO;
import vo.TheaterVO;
import vo.UserVO;

//데이터베이스 접근
public class UserDaoImpl implements UserDao {
	// 관리자 계정 생성
	{
		UserVO user = new UserVO();
		Database.tb_user.add(user);
		user.setId("root");
		user.setPw("root");
		user.setName("관리자");
		user.setPhone("777");
	}

	// 유저불러오기
	@Override
	public UserVO selectUser(String key) {
		for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(i);

			if (user.getId().equals(key)) {
				return user;
			}
		}
		return null;
	}

	// 유저추가하기
	@Override
	public void insertUser(UserVO user) {
		Database.tb_user.add(user);
	}

	// 유저리스트
	@Override
	public ArrayList<UserVO> selectUserList() {
		return Database.tb_user;
	}

	// 유저삭제하기
	@Override
	public void deleteUser(String key) {
		for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(i);

			if (key == user.getId()) {
				Database.tb_user.remove(i);
			}
		}
	}

	// 비밀번호 수정
	@Override
	public void pwChange(String key, String pw) {
		for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(i);

			if (key == user.getId()) {
				Database.tb_user.get(i).setPw(pw);
			}
		}
	}

	// 핸드폰번호 수정
	@Override
	public void phoneChange(String key, String phone) {
		for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(i);

			if (user.getId().equals(key)) {
				Database.tb_user.get(i).setPhone(phone);
			}
		}
	}

	// 공지사항 등록
	@Override
	public void insertNotice(NoticeVO notice) {
		Database.tb_notice.add(notice);
	}

	// 영화 등록
	@Override
	public void insertMovie(MovieVO movie) {
		Database.tb_movie.add(movie);

	}
	
	// 영화리스트
	@Override
	public ArrayList<MovieVO> movieList() {
		return Database.tb_movie;
	}
	
	// 상영관등록
	@Override
	public void insertTheater(TheaterVO theater) {
		Database.tb_theater.add(theater);
	}

	//상영관 리스트
	@Override
	public ArrayList<TheaterVO> theaterList() {
		return Database.tb_theater;
	}
	
	// 좌석등록
	@Override
	public void insertSeat(SeatVO seat) {
		Database.tb_seat.add(seat);
	}
	
	// 좌석리스트
	@Override
	public ArrayList<SeatVO> seatList() {
		return Database.tb_seat;
	}
	
	
	//날짜등록
	@Override
	public void dateRegister(DateVO date) {
		Database.tb_date.add(date);
	}

	//날짜 리스트
	@Override
	public ArrayList<DateVO> dateList() {
		return Database.tb_date;
	}

	// 영화순위
	@Override
	public void movieSort(Integer key, Integer rank) {
		
//		for (int i = 0; i < Database.tb_buyList.size(); i++) {
//			BuyListVO buy = Database.tb_buyList.get(i);
//
//			// 확인 System.out.println(key+"  "+buy.getMovieNum());
//
//			int count = 0;
//			if (key == buy.getMovieNum()) {
//				count += +buy.getSeatNum().size(); // 티켓수더하기
//				Database.tb_movie.get(i).setRank(count);
//			} else {
//				break;
//			}
//		}
		 

		for (int i = 0; i < Database.tb_movie.size(); i++) {
			MovieVO mo = Database.tb_movie.get(i);

			if (key == mo.getMovieNum()) {
				Database.tb_movie.get(i).setRank(rank);
			}
		}

		for (int i = 0; i < Database.tb_movie.size() - 1; i++) {
			for (int j = i + 1; j < Database.tb_movie.size(); j++) {
				if (Database.tb_movie.get(i).getRank() < Database.tb_movie.get(
						j).getRank()) {
					MovieVO temp = Database.tb_movie.get(i); // 자리를 바꿈
					Database.tb_movie.set(i, Database.tb_movie.get(j));
					Database.tb_movie.set(j, temp);
				}
			}
		}
	}

	// 공지사항
	@Override
	public ArrayList<NoticeVO> noticeView() {
		return Database.tb_notice;
	}

	// 공지사항 수정
	@Override
	public ArrayList<NoticeVO> noticeChange() {
		return Database.tb_notice;
	}

	// 결제내역 저장
	@Override
	public void insertPay(BuyListVO buy) {
		Database.tb_buyList.add(buy);
	}

	// 구매내역
	@Override
	public ArrayList<BuyListVO> buyList() {
		return Database.tb_buyList;
	}

	//예매취소
	@Override
	public void movieDrop(String key, int cal) {
		/*for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(cal);

			if (key == user.getId()) {
				Database.tb_user.remove(i);
			}
		}
	}*/}

}
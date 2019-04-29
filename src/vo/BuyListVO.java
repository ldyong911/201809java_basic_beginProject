package vo;

import java.util.ArrayList;

//구매내역
public class BuyListVO {
	private int movieNum; //영화번호
	private String movieName; //영화이름
	private int theaterNum; //상영관번호
	private String theaterName; //상영관이름
	private int date; //날짜
	private ArrayList<String> seatNum; //좌석번호
	private int buyRank; //랭크
	private String id; //사용자아이디
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBuyRank() {
		return buyRank;
	}
	public void setBuyRank(int buyRank) {
		this.buyRank = buyRank;
	}
	public int getMovieNum() {
		return movieNum;
	}
	public void setMovieNum(int movieNum) {
		this.movieNum = movieNum;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getTheaterNum() {
		return theaterNum;
	}
	public void setTheaterNum(int theaterNum) {
		this.theaterNum = theaterNum;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public ArrayList<String> getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(ArrayList<String> seatNum) {
		this.seatNum = seatNum;
	}
}

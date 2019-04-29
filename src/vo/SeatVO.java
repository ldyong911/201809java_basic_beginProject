package vo;

import java.util.ArrayList;

//좌석
public class SeatVO {
	private ArrayList<ArrayList<String>> seatNum; //좌석번호(주요키)
	private int theaterNum; //상영관번호(외래키)
	
	public ArrayList<ArrayList<String>> getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(ArrayList<ArrayList<String>> seatNum) {
		this.seatNum = seatNum;
	}
	public int getTheaterNum() {
		return theaterNum;
	}
	public void setTheaterNum(int theaterNum) {
		this.theaterNum = theaterNum;
	}
//	Aㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
//	Bㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
//	Cㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
	
}
package vo;

//상영관
public class TheaterVO {
	private int theaterNum; //상영관번호(주요키)
	private String theaterName; //상영관이름
	

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
}
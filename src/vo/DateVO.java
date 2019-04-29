package vo;

//날짜시간
public class DateVO {
	private int date; //날짜시간
	private int theaterNum; //상영관번호(외래키)
	private int movieNum; //영화번호(외래키)
	

	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	
	public int getTheaterNum() {
		return theaterNum;
	}
	public void setTheaterNum(int theaterNum) {
		this.theaterNum = theaterNum;
	}
	
	public int getMovieNum() {
		return movieNum;
	}
	public void setMovieNum(int movieNum) {
		this.movieNum = movieNum;
	}
}
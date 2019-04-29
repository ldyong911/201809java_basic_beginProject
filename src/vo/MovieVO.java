package vo;

//영화제목
public class MovieVO {
	private int movieNum; //영화번호(주요키)
	private String movieName; //영화이름
	private String movieIntro; //영화소개
	private int moviePrice; //영화가격
	private int rank; //랭크
	
	public void setMovieNum(int movieNum) {
		this.movieNum = movieNum;
	}
	public int getMovieNum() {
		return movieNum;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieIntro() {
		return movieIntro;
	}
	public void setMovieIntro(String movieIntro) {
		this.movieIntro = movieIntro;
	}
	public int getMoviePrice() {
		return moviePrice;
	}
	public void setMoviePrice(int moviePrice) {
		this.moviePrice = moviePrice;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
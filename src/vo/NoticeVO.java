package vo;

//공지사항
public class NoticeVO {
	private int noticeNum; //공지사항번호(주요키)
	private String noticeTitle; //공지사항제목
	private String noticeContent; //공지사항내용
	private String noticeWrite; //공지사항쓴이
	private String noticeDate; //공지사항 게시일
	
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getNoticeWrite() {
		return noticeWrite;
	}
	public void setNoticeWrite(String noticeWrite) {
		this.noticeWrite = noticeWrite;
	}
	public String getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}


}
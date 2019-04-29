package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vo.BuyListVO;
import vo.DateVO;
import vo.MovieVO;
import vo.NoticeVO;
import vo.SeatVO;
import vo.TheaterVO;
import vo.UserVO;
import dao.UserDao;
import dao.UserDaoImpl;

//메뉴를 실행
public class UserServiceImpl implements UserService {

	Scanner s = new Scanner(System.in);
	UserDao userDao = new UserDaoImpl();
	private String loginId; // 로그인아이디(주요키)를 임시적으로 저장
	private int noticeNum; //공지사항번호 임시저장(자동증가)
	private int movieNum; //영화번호 임시저장(자동증가)
	private int theaterNum; //상영관번호 임시저장(자동증가)
	private int selmNum; //영화번호 임시저장
	private int seltNum; //상영관번호 임시저장
	private int selectNum; //선택한 영화의 인덱스 번호
	private int selectMovieNum; //선택한 영화의 영화번호
	private String selectMovieName; //선택한 영화의 영화이름
	private int selectTheaterNum; //선택한 영화의 상영관번호
	private String selectTheaterName; //선택한 영화의 상영관이름
	private int selectDate; //선택한 날짜
	private int adult; // 성인 인원수 임시저장
	private int youth; // 청소년 인원수 임시저장
	private int money; // 사용자가 사용가능한 돈
	private int rowSize; // 선택한 영화의 상영관 자리 행사이즈 임시저장
	private int colSize; // 선택한 영화의 상영관 자리 열사이즈 임시저장
	private int count; //선택한 영화의 상영관 자리에서 ■수 체크
	private int selCount; //선택한 좌석의 수 체크
	Set<String> hs = new HashSet<String>(); //구매한 좌석 임시저장
	private int selcol; //선택한 예매취소 인뎃스 저장
		
	// 아이디는 공백이나 줄바꿈이 안되며 대소문자 구분없이 알파벳이나 숫자 4자리에서 10자리
	Pattern pId = Pattern.compile("(?i)\\w{4,10}");
	// 비밀번호는 공백이나 줄바꿈이 안되며 대소문자 구분하며 특수문자!$^&*만 포함 4자리에서 12자리
	Pattern pPw = Pattern.compile("[a-zA-Z0-9!$^&*]{4,12}");
	// 이름은 문자나 공백이나 줄바꿈이 아니며 한글 2자리에서 3자리
	Pattern pName = Pattern.compile("[가-힣]{2,3}");
	// 핸드폰번호는 010,011,016,017,018,019로 시작해서 가운데는 3이나 4자리 마지막은 4자리
	Pattern pPhone = Pattern.compile("01[016789]\\d{3,4}\\d{4}");
	
	// 숫자입력예외처리
	public int tryCatch() {
		Scanner s = new Scanner(System.in);
		int menu = 0;
		while (true) {
			try {
				menu = s.nextInt();
				return menu;
			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력해주세요!");
			}
		}
	}

	//정규표현식 검사
	public String joinRegular(Pattern p, String s){
		Scanner sc = new Scanner(System.in);
		while(true){
			try {
				System.out.print(s);
				String str = sc.next();
				Matcher m = p.matcher(str);
				if(m.matches() == false){
					throw new Exception(); //어떤 예외인지 모르기때문에 고의적으로 예외 발생
				}else{
					return str;
				}
			} catch (Exception e) {
				System.out.println("형식에 맞지 않습니다.");
			}
		}
	}

	// 회원가입
	@Override
	public int join() {
		UserVO user = new UserVO();
		
		System.out.println("--------------회원가입-----------------");
		String s1 = "아이디(4~10자리): ";
		user.setId(joinRegular(pId, s1));
		
		String s2 = "비밀번호( 4~12자리(특수문자사용시 !$%&* 만사용가능)): ";
		user.setPw(joinRegular(pPw, s2));
		
		String s3 = "이름(한글 2~3자리): ";
		user.setName(joinRegular(pName, s3));
		
		String s4 = "핸드폰번호(-없이 입력): ";
		user.setPhone(joinRegular(pPhone, s4));
		
	
//		System.out.print("아이디(4~10자리): ");
//		String id = s.next();
//		Matcher mId = pId.matcher(id);
//		
//		if(mId.matches() == false){
//			System.out.println("형식에 맞지 않습니다.");
//			return 3;
//		}else{
//			user.setId(id);
//		}
//		
//		System.out.println("-------------회원가입-------------");
//		System.out.print("아이디: ");
//		user.setId(s.next());
//		System.out.print("비밀번호: ");
//		user.setPw(s.next());
//		System.out.print("이름: ");
//		user.setName(s.next());
//		System.out.print("핸드폰번호: ");
//		s.nextLine();
//		user.setPhone(s.nextLine());
//		
		
		
		// 아이디중복체크
		UserVO userCheck = userDao.selectUser(user.getId());
		
		if (userCheck == null) {
			// 회원가입 진행
			userDao.insertUser(user);
			System.out.println("회원가입 성공~");
			System.out.println("------------------------------------");
			return 1; //메인메뉴로
		} else {
			// 아이디중복
			System.out.println("**이미 있는 아이디입니다!!**");
			System.out.println();
			return 3; //회원가입으로
		}
		
	}

	// 로그인
	@Override
	public int login() {
		// 로그인에서 존재하는 아이디인지 체크
		ArrayList<UserVO> userList = userDao.selectUserList();
		System.out.println("--------------로그인--------------");
		System.out.print("아이디: ");
		String id = s.next();
		System.out.print("비밀번호: ");
		String pw = s.next();

		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().equals(id)
					&& userList.get(i).getPw().equals(pw)) {
				loginId = userList.get(i).getId();

				if (userList.get(i).getId().equals("root")) {
					System.out.println("\t**관리자로 로그인했습니다**");
					System.out.println("--------------------------------\n");
					return 6; // 관리자 메뉴로 실행하기 위해
				} else {
					System.out.println("\t**로그인했습니다.**");
					System.out.println("--------------------------------\n");
					return 15; // 게스트 메뉴로 실행하기 위해
				}

			}
		}
		System.out.println("!!아이디나 비밀번호를 다시 확인해주세요.!!");
		System.out.println("--------------------------------\n");
		return 1; //메인메뉴로
	}

	// 유저리스트
	@Override
	public int userList() {
		ArrayList<UserVO> userList = userDao.selectUserList();

		System.out.println("--------------회원리스트------------\n");
		for (UserVO user : userList) {
			System.out.println("Id: " + user.getId() + "  Name: "
					+ user.getName() + "  Pw: " + user.getPw() + "  Phone: "
					+ user.getPhone());
		}
		System.out.println("--------------------------------\n");
		return 6; //관리자메뉴로

	}

	// 로그아웃
	@Override
	public int logout() {
		System.out.println("-------*로그아웃이 되었습니다*-------"); // 로그인아이디를 테스트삼아 출력
		return 1; //메인메뉴로
	}

	// 아이디찾기
	@Override
	public int findId() {
		ArrayList<UserVO> findId = userDao.selectUserList();
		System.out.println("-------------아이디찾기-------------");
		System.out.print("핸드폰번호를 입력해주세요: ");
		for (int i = 0; i < findId.size(); i++) {
			String line = s.nextLine();
			if (findId.get(i).getPhone().equals(line)) {
				System.out.println("회원님의 현재 아이디는 " + findId.get(i).getId()
						+ " 입니다.");
				System.out.println("--------------------------------\n");
				return 1; //메인메뉴로
			}
		}
		System.out.println("!!존재하지 않는 핸드폰번호입니다!!");
		System.out.println("--------------------------------\n");
		return 4; //아이디찾기로
	}

	// 비밀번호찾기
	@Override
	public int findPw() {
		ArrayList<UserVO> findPw = userDao.selectUserList();
		System.out.println("------------비밀번호찾기-----------\n");
		System.out.print("핸드폰번호를 입력해주세요: ");
		String line = s.next(); // 문자열 입력
		for (int i = 0; i < findPw.size(); i++) {
			if (findPw.get(i).getPhone().equals(line)) {
				System.out.print("아이디를 입력하세요 :");
				String line2 = s.next(); // 문자열 입력
				if (findPw.get(i).getId().equals(line2)) {
					System.out.println("회원님의 현재 비밀번호는: "
							+ findPw.get(i).getPw() + " 입니다.");
					System.out.println("--------------------------------\n");
					return 1; //메인메뉴로
				}
			}
		}
		System.out.println("!!존재하지 않는 번호입니다.!!");
		System.out.println("--------------------------------\n");
		return 5; //비밀번호찾기로
	}

	// 공지사항 등록
	@Override
	public int noticeRegister() {
		NoticeVO notice = new NoticeVO();

		notice.setNoticeNum(++noticeNum); // 공지사항번호 자동증가
		System.out.println("------------공지사항 등록-----------");
		System.out.println("제목: ");
		notice.setNoticeTitle(s.nextLine());
		notice.setNoticeTitle(s.nextLine());
		System.out.println("내용: ");
		notice.setNoticeContent(s.nextLine());
		System.out.println("작성자: ");
		notice.setNoticeWrite(s.next());
		// 날짜 자동으로 넣기
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		notice.setNoticeDate(sdf.format(date));
		userDao.insertNotice(notice);
		// System.out.println("영화 후기");
		System.out.println("--------------------------------\n");

		return 6; //관리자메뉴로
	}

	// 공지사항 수정
	@Override
	public int noticeChange() {
		ArrayList<NoticeVO> noticechange = userDao.noticeView();
		System.out.println("------------공지사항 수정-----------\n");
		noticeView();
		System.out.print("수정할 글의 번호를 입력해주세요: ");
		for (int i = 0; i < noticechange.size(); i++) {
			if (noticechange.get(i).getNoticeNum() == tryCatch()) {
				System.out.print("수정할 제목을 입력하세요: ");
				s.nextLine();
				noticechange.get(i).setNoticeTitle(s.nextLine());

				System.out.print("수정할 내용을 입력하세요: ");
				noticechange.get(i).setNoticeContent(s.nextLine());

				// 날짜 자동으로 넣기
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				noticechange.get(i).setNoticeDate(sdf.format(date));

				System.out.println("**수정되었습니다.**\n");
				noticeView();
			}

		}
		return 6; //관리자메뉴로
	}

	// 영화등록
	@Override
	public int movieRegister() {
		MovieVO movie = new MovieVO();
		System.out.println("-------------영화등록-------------\n");
		movie.setMovieNum(++movieNum);
		System.out.print("영화 이름: ");
		movie.setMovieName(s.next());
		System.out.print("영화 소개: ");
		movie.setMovieIntro(s.next());
		System.out.print("영화 가격: ");
		movie.setMoviePrice(tryCatch());
		System.out.println("\t**영화 등록 성공**");
		userDao.insertMovie(movie);

		return 6; //관리자메뉴로
	}

	// 상영관등록
	@Override
	public int theaterRegister() {		
		TheaterVO theater = new TheaterVO();
		System.out.println("-------------상영관등록-------------\n");
		theater.setTheaterNum(++theaterNum); //상영관 번호는 자동으로 중복안되게
		System.out.print("상영관 이름을 입력해주세요: ");
		theater.setTheaterName(s.next());
		System.out.println("\t**상영관등록 성공**");
		userDao.insertTheater(theater);
		
		return 11; //좌석등록으로
	}

	// 좌석등록
	@Override
	public int seatRegister() {
		// []안에 []가 들어있는 형태
		ArrayList<ArrayList<String>> seat = new ArrayList<ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		SeatVO seatvo = new SeatVO();

		System.out.println("행과 열 좌석의 갯수를 입력해주세요(행/열 순으로 입력해주세요)");
		System.out.println("**행최대:26, 열최대:9");
		System.out.print("행: ");
		int row = tryCatch();
		System.out.print("열: ");
		int col = tryCatch();

		if (row > 26 || col > 9) {
			System.out.println("!! 생성할 수 있는 좌석을 초과하였습니다.!!");
			return 11;
		}

		for (int i = 0; i < row; i++) {
			seat.add(list);
			list = new ArrayList<String>(); // 행 생성
			System.out.print((char)('A'+i));
			for (int j = 0; j < col; j++) {
				seat.get(i).add(((char) ('A' + i)) + "" + (j + 1)); // A1 형식으로 저장하기위해서
				System.out.print(" "+ seat.get(i).get(j));
			}
			System.out.println();
		}
		seatvo.setSeatNum(seat);
		seatvo.setTheaterNum(theaterNum); // 상영관번호 등록
		userDao.insertSeat(seatvo);
		System.out.println("--------------------------------\n");
		return 6; //관리자메뉴로
	}

	// 날짜,시간 등록
	@Override
	public int dateRegister() {
		ArrayList<MovieVO> movieList = userDao.movieList();
		ArrayList<TheaterVO> theaterList = userDao.theaterList();
		
		
		
		if(movieList.size() <= 0){
			System.out.println("영화관이 등록되지않았습니다.");
			return 9; //영화관등록으로
		}else if(theaterList.size() <= 0){
			System.out.println("상영관이 등록되지않았습니다.");
			return 10; //상영관등록으로
		}
		
		//영화선택
		System.out.println("-------------영화목록-------------");
		System.out.println("번호\t영화명");
		int num = 0;
		for(MovieVO mv : movieList){
			System.out.println(++num + "\t" + mv.getMovieName());
		}
		System.out.print("날짜를 등록할 영화를 고르세요: ");
		int select = tryCatch();
		
		if(select > movieList.size() || select > theaterList.size()){
	         System.out.println("유효하지 않습니다.");
	         return 12; //날짜등록으로
	    }
		
		selmNum = movieList.get(select-1).getMovieNum(); //영화번호 임시저장
		
		
		
		//상영관선택
		System.out.println("-------------상영관목록-------------");
		System.out.println("번호\t상영관번호\t상영관명");
		int num2 = 0;
		for(TheaterVO th : theaterList){
			System.out.println(++num2 + "\t" + th.getTheaterNum() + "\t" + th.getTheaterName());
		}
		System.out.print("날짜를 등록할 상영관을 고르세요: ");
		int select2 = tryCatch();	
		
		if(select2 > movieList.size() || select2 > theaterList.size()){
	         System.out.println("유효하지 않습니다.");
	         return 12; //날짜등록으로
	    }
		
		seltNum = theaterList.get(select2-1).getTheaterNum(); //상영관번호 임시저장
		
		//날짜등록
		DateVO date = new DateVO();
		System.out.println("-------------날짜등록-------------");
		System.out.print("날짜를 입력하세요(예)102910 - 월일시간): ");
		date.setDate(tryCatch()); //날짜입력
		date.setMovieNum(selmNum); //영화번호 저장
		date.setTheaterNum(seltNum); //상영관번호 저장
		System.out.println("\t**날짜 등록 성공**");
		userDao.dateRegister(date);

		return 6; //관리자메뉴로
	}

	// 비밀번호 수정
	@Override
	public int pwChange() {
		System.out.println("------------비밀번호 수정----------");
		String s2 = "비밀번호(특수문자!$%&*만 가능하며 4~12자리): ";
		userDao.pwChange(loginId, joinRegular(pPw, s2));
		System.out.println("**비밀번호가 정상적으로 변경되었습니다.**");
		System.out.println("--------------------------------\n");
		return 15; //유저메뉴로
	}

	// 핸드폰번호 수정
	@Override
	public int phoneChange() {
		System.out.println("------------핸드폰번호 수정-----------");
		String s4 = "핸드폰번호(-없이 입력): ";
		userDao.pwChange(loginId, joinRegular(pPhone, s4));
		System.out.println("**핸드폰번호가 정상적으로 변경되었습니다.**");
		System.out.println("--------------------------------\n");
		return 15; //유저메뉴로
	}

	// 회원탈퇴
	@Override
	public int drop() {
		System.out.println("-------------회원탈퇴-------------\n");
		System.out.println("?? 정말로 탈퇴하시겠습니까 ??");
		System.out.println("(yes ==y || no ==n)");
		char c = s.next().charAt(0);
		if (c == 'y' || c == 'Y') {
			userDao.deleteUser(loginId);
			System.out.println("**정상적으로 탈퇴되었습니다.**");
			System.out.println("--------------------------------\n");
			return 1; //메인메뉴로

		} else if (c == 'n' || c == 'N') {
			return 15; //유저메뉴로
		}
		return 20; //회원탈퇴로
	}

	// 공지사항 리스트
	@Override
	public int noticeView() {
		ArrayList<NoticeVO> noticeList = userDao.noticeView();

		System.out.println("=======공 지 사 항=======");
		for (NoticeVO notice : noticeList) {
			System.out.println("번호: " + notice.getNoticeNum() + "  작성자: "
					+ notice.getNoticeWrite() + "\n제목: "
					+ notice.getNoticeTitle() + "\n내용: "
					+ notice.getNoticeContent() + "\n작성일: "
					+ notice.getNoticeDate());
			System.out.println("=====================\n");
		}
		return 15; //유저메뉴로
	}
	
	//영화순위 보여주기
	@Override
	public int movieRank() {
		ArrayList<MovieVO> mvList = userDao.movieList();
		int num = 0;
		System.out.println("-----------영 화 순 위----------\n");
		for (MovieVO movie : mvList) {
			System.out.println((++num) + ".  " + "영화이름: " + movie.getMovieName());
		}
		System.out.println("--------------------------------\n");
		
		if(mvList.size() <= 0){
			System.out.println("아직 영화가 등록되지 않았습니다.");
			return 15; //유저메뉴로
		}
		return 23; //영화선택으로
	}

	// 영화선택
	@Override
	public int movieSelect() {
		System.out.print("자세히 볼 영화번호를 선택해주세요: ");
		int select = tryCatch();
		
		ArrayList<MovieVO> mvList = userDao.movieList();
		System.out.println("---------영화 상세 정보---------");
		System.out.println("영화이름 : " + mvList.get(select - 1).getMovieName()
				+ "\t영화줄거리 : " + mvList.get(select - 1).getMovieIntro()+"\t영화가격 :"+mvList.get(select-1).getMoviePrice()+" (성인:영화가격+3000)");
		System.out.println("--------------------------------\n");
		System.out.print("영화이름: " + mvList.get(select - 1).getMovieName()
				+ "  를 예매하시겠습니까? ");
		System.out.println("YES : Y || NO : N");

		char c = s.next().charAt(0);
		if (c == 'y' || c == 'Y') {
			selectNum = select-1; //선택한 영화의 인덱스 번호 임시저장
			selectMovieNum = mvList.get(selectNum).getMovieNum(); //선택한 영화의 번호 임시저장
			selectMovieName = mvList.get(selectNum).getMovieName(); //선택한 영화의 이름 임시저장
			System.out.println("**영화가 선택되었습니다.");
			return 24; //날짜시간선택으로
		} else if (c == 'n' || c == 'N') {
			return 15; //유저메뉴로
		}

		System.out.println("잘못 입력하셨습니다.다시입력해주세요");
		return 16; //예매하기로
	}

	// 날짜, 시간선택
	@Override
	public int dateSelect() {
		ArrayList<DateVO> dateList = userDao.dateList();
		ArrayList<TheaterVO> thList = userDao.theaterList();
		
		
		System.out.println("---------날짜 시간 선택---------\n");
		System.out.println("번호\t날짜\t영화이름\t상영관번호");
		int num=0;
		for(int i=0; i<dateList.size(); i++){
			if(selectMovieNum == dateList.get(i).getMovieNum()){
				System.out.println((++num) + ".\t" + dateList.get(i).getDate() + "\t"
						+ selectMovieName + "\t\t"
						+ dateList.get(i).getTheaterNum());
				
			}
		}
		System.out.print("해당영화의 날짜를 선택해주세요: ");
		int select = tryCatch();
		System.out.println("--------------------------------\n");
		selectDate = dateList.get(select-1).getDate();
		selectTheaterNum = dateList.get(select-1).getTheaterNum();
		for(int i=0; i<thList.size(); i++){
			if(selectTheaterNum == thList.get(i).getTheaterNum()){
				selectTheaterName = thList.get(i).getTheaterName();
			}
		}

		return 25; //인원선택으로
	}
	
	//행, 열 사이즈 구하기
	public void rowcolSize(){
		ArrayList<SeatVO> seatList = userDao.seatList();
		for (int i = 0; i < seatList.size(); i++) {
			for (int j = 0; j < seatList.get(i).getSeatNum().size(); j++) {
				for (int k = 0; k < seatList.get(i).getSeatNum().get(j).size(); k++) {
					if (selectTheaterNum == seatList.get(i).getTheaterNum()) { //선택한 상영관번호
						rowSize = seatList.get(i).getSeatNum().size(); // 행사이즈 저장
						colSize = seatList.get(i).getSeatNum().get(j).size(); // 열사이즈 저장
					}
				}
			}
		}
	}

	// 인원선택
	@Override
	public int peopleNumCheck() {
		rowcolSize();
		System.out.println("------------인원선택------------");
		if (rowSize * colSize - count == 0) {
			System.out.println("!! 영화관의 자리가 없습니다. !!");
			return 24; //날짜선택으로
		}
		System.out.println(" ");
		System.out.println("인원수를 입력하세요(최대 " + (rowSize * colSize - count)
				+ "명 입력할 수 있습니다. *해당 사항 없을시 '0'입력)");
		System.out.print("성인: ");
		adult = tryCatch();
		System.out.print("청소년: ");
		youth = tryCatch();

		if ((rowSize * colSize - count) < (adult + youth)) {
			System.out.println("!! 입력한 인원수만큼의 자리가 없습니다. !!");
			return 25; //인원선택으로
		}
		
		if(0>=adult+youth){
			System.out.println("유효하지 않습니다.");
			return 25; //인원선택으로
		}

		System.out.println("--------------------------------");
		System.out.println("성인: " + adult + "명 \t 청소년: " + youth + "명");
		System.out.println("인원수가 맞으면  YES : Y 틀리면 NO : N 입력해주세요");
		System.out.println("--------------------------------");

		char c = s.next().charAt(0);
		if (c == 'y' || c == 'Y') {
			return 26; // 좌석선택으로
		} else if (c == 'n' || c == 'N') {
			return 25; //인원선택
		}
		
		System.out.println("잘못입력하셨습니다.다시입력해주세요");
		return 25; //인원선택
	}

	public void seatPrint() {
		// 좌석 프린트
		ArrayList<SeatVO> seatList = userDao.seatList();
		
		System.out.println("--------------------------------");
		for(int i=0; i<seatList.size(); i++){
			if(selectTheaterNum == seatList.get(i).getTheaterNum()){ //선택한 영화의 상영관번호인지 체크해서 좌석출력
				for(int j=0; j<rowSize; j++){
					System.out.print((char)('A'+j) + " "); //행프린트
					for(int k=0; k<colSize; k++){
						if(hs.contains(seatList.get(i).getSeatNum().get(j).get(k))){
							System.out.print(seatList.get(i).getSeatNum().get(j).get(k) + "■");
//							++selCount;
						}else{
							System.out.print(seatList.get(i).getSeatNum().get(j).get(k) + "□");
						}
					}
					System.out.println();
				}
			}
		}
		System.out.println("--------------------------------");

	}

   // 좌석 선택
   @Override
   public int seatSelect() {
      int person = adult + youth - selCount; //인원수체크(이미선택한자리를 선택하거나, 유효성없는 자리를 선택하면 내가 선택한부분부터 시작)
      
      seatPrint();
      
      //좌석선택
      for(int n=0; n<person; n++){
    	  System.out.println("좌석을 선택하세요(형식:A1)");
    	  String str = s.next().toUpperCase(); //모든 문자를 대문자로 저장(대소문자 구별없이 하려고)
    	  if(str.length() == 2){ //2글자만 입력받게
    		  char one = str.charAt(0); //첫글자
    		  char two = str.charAt(1); //두번째글자
    		  if(('A' <= one && one <= 'Z') && (one - 'A' <= rowSize - 1) //행이 범위안에 A부터Z안에 속해야하고 상영관자리의 사이즈보다 작아야함
                  && (0 < two - '0' && two - '0' <= colSize)) { //열이 0보다 커야하며 상영관자리의 사이즈보다 작아야함)
    			  for(int i=0; i<rowSize; i++){ //행사이즈
    				  if(one - 'A' == i){ //유효한 행인지
    					  for(int j=0; j<colSize; j++){ //열사이즈
    						  if(two - '0' - 1 == j){ //유효한 열인지
    							  if(hs.contains(str)){
    								  System.out.println("이미 선택된 자리입니다.");
    								  return 26; //좌석선택으로
    							  }else{
    								 hs.add(str);
    								 ++selCount; //선택했으면 count해줘서 잘못입력했을때 원래입력했던부분부터 시작하기위해
        							 seatPrint();
    							  }
    						  }
    					  }
    					  System.out.println();
    				  }
    			  }
    		  }else{
    			  System.out.println("선택된 자리는 없습니다!");
    			  return 26; //좌석선택으로
    		  }
    	  }else{
    		  System.out.println("형식에 맞게 입력하세요!");
    		  return 26; //좌석선택으로
    	  }
      }

      selCount = 0; // 선택한 개수 초기화
      return 27; //결제로
   }

	// 결제
	@Override
	public int pay() {
		System.out.println("-------------결제화면-------------");
		BuyListVO buy = new BuyListVO();
		ArrayList<UserVO> userList = userDao.selectUserList();
		ArrayList<MovieVO> mvList = userDao.movieList();
		ArrayList<TheaterVO> thList = userDao.theaterList();

		money = 50000;
		int price = 0;
		int mileage = 0;
		price += adult * (mvList.get(selectNum).getMoviePrice() + 3000);
		price += youth * mvList.get(selectNum).getMoviePrice();

		// 시간되면 ,찍어주기
		System.out.println("성인   : " + adult + "명  -> 가격: " + adult
				* (mvList.get(selectNum).getMoviePrice() + 3000));
		System.out.println("청소년 : " + youth + "명 -> 가격" + youth
				* mvList.get(selectNum).getMoviePrice());
		System.out.println("총 구매가격은 " + price + "입니다");
		System.out.println("결제를 원하면  YES : Y 원하지않으면 NO : N 입력해주세요");

		char c = s.next().charAt(0);
		if (c == 'y' || c == 'Y') {
			if (price > money) {
				System.out.println("보유한 돈이 부족합니다.");
				return 26; // 인원선택 다시
			}
			money -= price;
			System.out.println("남은돈 :" + money + "\n결제가 완료되었습니다");
			mileage += price * 0.1;
			System.out.println("총 마일리지 적립: " + mileage);
			
			for (int i = 0; i < userList.size(); i++) {
				if (loginId == userList.get(i).getId()) {
					userList.get(i).setMileage(userList.get(i).getMileage() + mileage);
					System.out.println(userList.get(i).getMileage());
				}
			}
			
			ArrayList<String> temp = new ArrayList<String>();
			Iterator it = hs.iterator();
			while(it.hasNext()){
				temp.add((String)it.next());
			}
			for(int i=0; i<temp.size(); i++){
				System.out.print(temp.get(i));
			}

			buy.setMovieNum(selectMovieNum);
			buy.setMovieName(selectMovieName);
			buy.setTheaterNum(selectTheaterNum);
			buy.setTheaterName(selectTheaterName);
			buy.setDate(selectDate);
			buy.setSeatNum(temp);
			buy.setBuyRank(adult+youth);
			buy.setId(loginId);
			System.out.println(buy.getMovieNum());
			System.out.println(buy.getMovieName());
			System.out.println(buy.getTheaterNum());
			System.out.println(buy.getTheaterName());
			System.out.println(buy.getDate());
			System.out.println(buy.getBuyRank());
			System.out.println(buy.getId());
			userDao.insertPay(buy);
			count += (adult + youth); // 선택된 자리수 계속 카운트
			System.out.println("---------결제가완료되었습니다---------\n");
			return 28; //구매내역으로
		} else if (c == 'n' || c == 'N') {
			if (money < price) {
				System.out.println(" !! 돈이 부족합니다  !! ");
				return 26; //인원선택으로
			}
		}
		return 28; //구매내역으로
	}

	// 현재구매내역 출력
	@Override
	public int nowBuyListPrint() {
		ArrayList<BuyListVO> buyList = userDao.buyList();
		
		System.out.println("-------------결제내역-------------");
		System.out.print("영화번호: " + selectMovieNum + " "
				+ "영화이름: " + selectMovieName + " "
				+ "\n상영관번호: " + selectTheaterNum + " "
				+ "상영관이름: " + selectTheaterName + " "+"좌석 : ");
		for(int i=0; i<buyList.size(); i++){
			if(selectTheaterNum == buyList.get(i).getTheaterNum() &&
					selectMovieNum == buyList.get(i).getMovieNum() &&
					selectDate == buyList.get(i).getMovieNum() &&
					loginId == buyList.get(i).getId()){
				for(int j=0; j<buyList.get(i).getSeatNum().size(); j++){
					System.out.print(buyList.get(i).getSeatNum().get(j) + ", ");
				}
			}
			System.out.println();
		}
		
		System.out.println("--------------------------------");

		return 15; //유저메뉴로
	}

	// 전체 구매내역
	@Override
	public int totalBuyListPrint() {
		ArrayList<BuyListVO> buylist = userDao.buyList();
		ArrayList<UserVO> user = userDao.selectUserList();
		int num=0;
		for (int i = 0; i < buylist.size(); i++) {
			if (loginId == buylist.get(i).getId()) {
				System.out.println((++num) +". "+"사용자 아이디: " + buylist.get(i).getId()
						+ "  구매한 영화이름: " + buylist.get(i).getMovieName()
						+ "  구매한 영화번호 : " + buylist.get(i).getMovieNum()+ "\t구매한 좌석"+buylist.get(i).getSeatNum());
				System.out.println("--------------------------------");
				
			}
			
			
		}
		
		for (int i = 0; i < user.size(); i++) {
			if (loginId == user.get(i).getId()) {
				System.out.println("사용자 총 적립 금액" + user.get(i).getMileage());
			}

		}
		
		return 15; //유저메뉴로
	}
	
}
package com.back;
import java.util.*;

// OOP를 쓰는 것에 있어서 -객체지향은
// 최대한 기능을 분리하는것에 이점을 가진다.
// 캡슐화 , 다형성 , 상속 , 추상화
// 캡슐화 => private 데이터 보호 , getter/setter로 접근
// 상속 => 부모 클래스를 자식이 물려받음
// 다형성 => 같은 메서드가 상황에 따라 다르게 동작
// 추상화 => 필요한 것만 드러내고 나머지는 숨기기 (추상 클래스, 인터페이스)

// Class -> field / Generator / Getter / Setter

class Quotes {
    private int id; // 명언 인덱스
    private String author; // 작가
    private String content; // 명언

    public Quotes(int id, String author, String content) { // 생성자
        this.id = id;
        this.author = author;
        this.content = content;
    } // 생성자를 통해서 객체 생성을 함
    // 근데 생성을 하면 필드값이 private라 main 클래스나 다른 클래스에서 접근 못함
    // 따라서 Getter 메서드를 만듬
    public int GetId() {
        return id;
    }
    public String GetAuthor() {
        return author;
    }
    public String GetContent() {
        return content;
    }

    // Setter 메소드 => 수정 기능 필요해서 넣음
    public void SetAuthor(String author) {
        this.author = author;
    }
    public void SetContent(String content) {
        this.content = content;
    }

}

class QuoteService {
    private List<Quotes> q_list = new ArrayList<>(); // 명언 객체를 담을 리스트
    private int idx = 0; // 증가 인덱스

    public void register(String author, String content) { // 등록
        idx++; // 등록시 인덱스 증가
        Quotes q1 = new Quotes(idx,author,content);
        q_list.add(0, q1); // 최신순 유지
        System.out.println(idx + "번 명언이 등록되었습니다");
    }

    public void list() { // 목록
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for(Quotes q : q_list) {
            System.out.println(q.GetId() + " / " + q.GetAuthor() + " / " + q.GetContent());
        }
    }

    public void delete(int id) { // 삭제
        q_list.remove(id);
    }

    public void revise(Quotes q, String content, String author) {
        q.SetContent(content); // 명언 수정
        q.SetAuthor(author); // 작가 수정
    }

    public Quotes finditem(int id) { //찾고자하는 객체가 있을 때
        for(int i = 0;i<q_list.size();i++) {
            if(q_list.get(i).GetId() == id) {
                return q_list.get(i); // 있다면 객체 반환
            }
        }
        return null; // 없다면 null
    }

}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuoteService service = new QuoteService(); // 기능 클래스 따로 구현

        System.out.println("== 명언 앱 ==");

        while(true) {

            System.out.println("명령) ");

            String cmd = sc.nextLine(); // 명령 입력 받음

            if(cmd.equals("종료")) {
                break;
            }
            else if(cmd.equals("등록")) {
                System.out.println("명언 : ");
                String content = sc.nextLine();
                System.out.println("작가 : ");
                String author = sc.nextLine();

                service.register(author, content); // 객체 바로 등록
            }
            else if(cmd.equals("목록")) {
                service.list(); // 목록 보여주는 메소드
            }
            else if(cmd.startsWith("삭제?id=")) { // 정규표현식 말고 startsWith사용
                int id = Integer.parseInt(cmd.split("=")[1]); // = 기준으로 split하고 뒤 id 값만 가져옴
                Quotes q = service.finditem(id);

                if(q == null) { // 삭제하고자 하는 명언이 있다면
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                    continue;
                }

                service.delete(id);
                System.out.println(id+ "번 명언이 삭제되었습니다.");

            }
            else if(cmd.startsWith("수정?id=")) {
                int id = Integer.parseInt(cmd.split("=")[1]);

                Quotes q = service.finditem(id); // 해당 객체가 있는지 먼저 확인.

                if(q == null) { // 값이 없다면
                    System.out.println(id+ "번 명언은 존재하지 않습니다.");
                    continue;
                }

                System.out.println("명언(기존) : " + q.GetContent());
                System.out.print("명언 : ");
                String newContent = sc.nextLine(); // 수정
                System.out.println("작가(기존) : " + q.GetAuthor());
                System.out.print("작가 : ");
                String newAuthor = sc.nextLine();

                service.revise(q,newContent,newAuthor);


                // 수정, 근데 Scanner 한번 더 선언하는것보다 메인에서 쓰자
                // revise 기능을 따로 떨어트리고 객체를 가져와서 판단하는 구문을 쓸거면 삭제 기능에도 추가?

            }
        }
    }
}

// 기존 하드코딩
//package com.back;
//import java.util.*;
//

//public class Main {
//    public static void main(String[] args) {
//        ArrayList<String> quotes = new ArrayList<>(); // 번호 + 작가 + 명언 담을 리스트
//        Scanner sc = new Scanner(System.in);
//
//        String cmd = ""; // 명령 받을 변수
//        String quote = ""; //입력 변수
//        String author = ""; //입력 변수
//        int quote_num = 0; //번호
//
//        System.out.println("== Application Service ==");
//
//        while(true) { // 메인 반복문
//            System.out.print("command) ");
//
//            cmd = sc.nextLine(); // 명령 입력받음
//
//            if(cmd.equals("exit")) { // 종료
//                break;
//            }
//            else if(cmd.equals("register")) { // 등록
//                System.out.print("Quotes : ");
//                quote = sc.nextLine(); // 명언 입력
//                System.out.print("Author : ");
//                author = sc.nextLine(); // 작가 입력
//
//                quote_num++; // 번호 증가
//                System.out.println(quote_num + " (number) quote is saved.");
//                quotes.add(0,quote_num +" / "+ author +" / "+ quote); // 항상 내림차순 유지
//                // 항상 0번째에 저장하면 최신순으로 배열 유지
//                // System.out.println(quote_num + quote + author);
//            }
//            else if(cmd.equals("list")) { // 목록
//                System.out.println("Number / Author / Quotes");
//                System.out.println("----------------------");
//                for(int i= 0;i<quotes.size();i++){ // 최신순 출력 이미 위에서 입력시 내림차순 유지하게 해놓음
//                    System.out.println(quotes.get(i));
//                }
//            }
//            else if(cmd.matches("delete\\?id=\\d+")) { // 삭제 + 정규표현식
//                char del_idx = cmd.charAt(cmd.length()-1); // 삭제할 리스트 인덱스 (입력받은 값 id=숫자) // 두자리 숫자 생각
//                int quote_len = quotes.size(); // 삭제된거 다시 삭제하는거 방지하기 위해 배열 크기 미리 저장
//                for(int i = 0;i<quotes.size();i++) {
//                    if((quotes.get(i)).charAt(0) == del_idx) { // 리스트 다 돌면서 삭제할 인덱스의 값이 있는지 확인
//                        quotes.remove(i); // 있다면 삭제
//                        System.out.println(del_idx + "\'s Quote has been deleted.");
//                    }
//                }
//                if(quote_len == quotes.size()) { // 검색했는데도 배열 크기가 변화된게 없다면 이미 삭제된 것
//                    System.out.println(del_idx + "\'s Quote is not valid.");
//                }
//                //cmd.charAt(cmd.length()-1); // 검색한 index값
//            }
//            else if(cmd.matches("revise\\?id=\\d+")) {
//                char rev_idx = cmd.charAt(cmd.length()-1);  // 수정할 idx
//                int rev_flag = 0; // 수정할 값이 없다면 판단용 인덱스
//                for(int i = 0;i<quotes.size();i++) {
//                    if((quotes.get(i)).charAt(0) == rev_idx) { // 수정할 값을 찾으면
//                        String text = quotes.get(i);
//                        String[] text_quote = text.split("/"); // / 기준으로 나눠서 마지막 명언만 가지고옴
//                        System.out.println("Quotes(default) : " + text_quote[2]);
//                        System.out.print("Quotes : ");
//                        String revise = sc.nextLine(); // 수정할거 입력받음
//                        quotes.set(i , text_quote[0] + "/" + text_quote[1] + "/ " + revise); // 수정한 명언 추가해서 다시 업데이트
//                    }
//                    else { // 명언 없는거 체크용
//                        rev_flag++; // 조건에 안걸렸다면 플래그 값이 전체 반복문 횟수와 같음(배열크기랑 같음)
//                    }
//                }
//                if(rev_flag == quotes.size()) { // 해당 명언이 없다면
//                    System.out.println(rev_idx + "\'s Quote is not valid.");
//                }
//            }
//        }
//
//
//    }// 시작점
//
}
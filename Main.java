package com.back;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<String> quotes = new ArrayList<>(); // 번호 + 작가 + 명언 담을 리스트
        Scanner sc = new Scanner(System.in);

        String cmd = ""; // 명령 받을 변수
        String quote = ""; //입력 변수
        String author = ""; //입력 변수
        int quote_num = 0; //번호

        System.out.println("== Application Service ==");

        while(true) { // 메인 반복문
            System.out.print("command) ");

            cmd = sc.nextLine(); // 명령 입력받음

            if(cmd.equals("exit")) { // 종료
                break;
            }
            else if(cmd.equals("register")) { // 등록
                System.out.print("Quotes : ");
                quote = sc.nextLine(); // 명언 입력
                System.out.print("Author : ");
                author = sc.nextLine(); // 작가 입력

                quote_num++; // 번호 증가
                System.out.println(quote_num + " (number) quote is saved.");
                quotes.add(0,quote_num +" / "+ author +" / "+ quote); // 항상 내림차순 유지
                // 항상 0번째에 저장하면 최신순으로 배열 유지
                // System.out.println(quote_num + quote + author);
            }
            else if(cmd.equals("list")) { // 목록
                System.out.println("Number / Author / Quotes");
                System.out.println("----------------------");
                for(int i= 0;i<quotes.size();i++){ // 최신순 출력 이미 위에서 입력시 내림차순 유지하게 해놓음
                    System.out.println(quotes.get(i));
                }
            }
            else if(cmd.matches("delete\\?id=\\d+")) { // 삭제 + 정규표현식
                char del_idx = cmd.charAt(cmd.length()-1); // 삭제할 리스트 인덱스 (입력받은 값 id=숫자) // 두자리 숫자 생각
                int quote_len = quotes.size(); // 삭제된거 다시 삭제하는거 방지하기 위해 배열 크기 미리 저장
                for(int i = 0;i<quotes.size();i++) {
                    if((quotes.get(i)).charAt(0) == del_idx) { // 리스트 다 돌면서 삭제할 인덱스의 값이 있는지 확인
                        quotes.remove(i); // 있다면 삭제
                        System.out.println(del_idx + "\'s Quote has been deleted.");
                    }
                }
                if(quote_len == quotes.size()) { // 검색했는데도 배열 크기가 변화된게 없다면 이미 삭제된 것
                    System.out.println(del_idx + "\'s Quote is not valid.");
                }
                //cmd.charAt(cmd.length()-1); // 검색한 index값
            }
            else if(cmd.matches("revise\\?id=\\d+")) {
                char rev_idx = cmd.charAt(cmd.length()-1);  // 수정할 idx
                int rev_flag = 0; // 수정할 값이 없다면 판단용 인덱스
                for(int i = 0;i<quotes.size();i++) {
                    if((quotes.get(i)).charAt(0) == rev_idx) { // 수정할 값을 찾으면
                        String text = quotes.get(i);
                        String[] text_quote = text.split("/"); // / 기준으로 나눠서 마지막 명언만 가지고옴
                        System.out.println("Quotes(default) : " + text_quote[2]);
                        System.out.print("Quotes : ");
                        String revise = sc.nextLine(); // 수정할거 입력받음
                        quotes.set(i , text_quote[0] + "/" + text_quote[1] + "/ " + revise); // 수정한 명언 추가해서 다시 업데이트
                    }
                    else { // 명언 없는거 체크용
                        rev_flag++; // 조건에 안걸렸다면 플래그 값이 전체 반복문 횟수와 같음(배열크기랑 같음)
                    }
                }
                if(rev_flag == quotes.size()) { // 해당 명언이 없다면
                    System.out.println(rev_idx + "\'s Quote is not valid.");
                }
            }
        }


    }// 시작점

}
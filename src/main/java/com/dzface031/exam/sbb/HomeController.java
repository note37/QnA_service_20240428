package com.dzface031.exam.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


// @Controller : 스프링부트한테 해당 클래스느 컨트롤러 역할이라고 알려준다.
@Controller
public class HomeController {
    private int increaseNo;
    public HomeController(){
        increaseNo = -1;
    }
    //@RequestMapping 은 get과 post 방식이 모두 포함되어 있는 방식
    @RequestMapping ("/sbb")
    // @ResponseBody :
    // 아래 함수 리턴 값을 body에 띄워줌.
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 바디에 담는다.
    @ResponseBody
    public String showHome(){
        return "안녕하세요.";
    }
    @RequestMapping("/test")
    @ResponseBody
    public String showTest(){
        return """
                <h1>안녕하세요.</h1>
                <input type="text" placeholder="입력해주세요."/>
                """;
    }
    @GetMapping("/page1")
    @ResponseBody
    public String showGet(){
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이 입력"/>
                    <input type="submit" value="page2로 POST 방식으로 이동"/>
                </form>
                """;
    }

    // get 방식은 http://localhost:8080/page2?age=20 이렇게 주소창에 데이터를 입력 가능
    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue ="0" ) int age){
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요 Get 방식으로 오신걸 환영합니다.</h1>
                """.formatted(age);
    }

    //post 방식은 http://localhost:8080/page2 페이지 위치만 나타남
    @PostMapping("/page2")
    @ResponseBody
    //@RequestParam: page1에서 나이 입력을 안했을 경우 오류가 발생하는데, 기본값을 적용해줄 수 있음 다만 문자열로 기본값 설정 됨
    public String showPage2Post(@RequestParam(defaultValue ="0" ) int age){
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요 Post 방식으로 오신걸 환영합니다.</h1>
                """.formatted((age));
    }
    @GetMapping("/plus")
    @ResponseBody
    // 주소창에 http://localhost:8080/plus?a=2&b=5 입력시 적용
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b){
        return a + b ;
    }

    //increaseNo 라는 전역변수를 만들어 홈페이지 접속시마다 숫자가 1씩 늘어나게 하는 기능구현
    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease(){
        increaseNo++;
        return increaseNo;

    }
}

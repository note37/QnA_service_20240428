package com.dzface031.exam.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    @GetMapping("/gugudan")
    @ResponseBody
    // Integer 는 null값 받기 가능
    public String showGugudan(Integer dan, Integer limit){
        if(dan==null){
            dan = 9;
        }
        if(limit ==null){
            limit = 9;
        }
        String gugudanFormat = "";
        for (int i = 1; i <=limit ; i++) {
            gugudanFormat +="%d * %d = %d<br>".formatted(dan, i, dan * i);
        }
        return gugudanFormat;
    }
    // 구구단 스트림방식으로 구현
    @GetMapping("/gugudan2")
    @ResponseBody
    // Integer 는 null값 받기 가능
    public String showGugudan2(Integer dan, Integer limit){
        if(dan==null){
            dan = 9;
        }
        if(limit ==null){
            limit = 9;
        }
        //rangeClosed 반복문
        final Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i ->"%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));
    }
    @GetMapping("/home/returnBoolean")
    @ResponseBody
    public boolean showReturnBoolean() {
        return false;
    }
    @GetMapping("/home/returnDouble")
    @ResponseBody
    public double showReturnDouble(){
        return Math.random();
    }

    @GetMapping("/home/returnIntArr")
    @ResponseBody
    public int[] showReturnIntArr(){
        int[] arr = new int[]{10, 20, 30};
        return arr;
    }

    @GetMapping("/home/returnStringList")
    @ResponseBody
    public List<String> showReturnStringList(){
        List<String> list = new ArrayList<>(){{
            add("안녕");
            add("반가워");
            add("어서와");
        }};
        return list;
//        위 리스트 다른 입력 방법
//        List<String> list2 = new ArrayList<>();
//        list2.add("안녕");
//        list2.add("반가워");
//        list2.add("어서와");
    }

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap(){
        Map<String, Object> map = new LinkedHashMap<>(){{
            put("id",1);
            put("name","복순이");
            put("food",new ArrayList<>() {{
                add("사과");
                add("당근");
                add("닭고기");


            }});

        }};
        return map;
    }

}

class Animal {
    private final int id;
    private final String name;

    private final List<String> food;

    public Animal(int id, String name, List<String> food) {
        this.id = id;
        this.name = name;
        this.food = food;
    }
}

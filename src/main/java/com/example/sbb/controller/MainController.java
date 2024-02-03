package com.example.sbb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private int increaseNo = -1;

    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수ㅢ 리턴값을 문자열화 해서 브라우저 응답을 바디에 담는다.
    @ResponseBody
    public String index(){
        return "안녕하세요.";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet(){
        return """
               <form method="POST" action="/page2"/>
                    <input type="number" name="age" placeholder="나이 입력"/>
                    <input type="submit" value="page2로 POST 방식으로 이동"/>
               </form>
               """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age){
        return """
               <h1>입력된 나이 : %d</h1>
               <h1>안녕하세요. POST 방식으로 오신걸 환영합니다.</h1>
               """.formatted(age);
    }

    // JSP 서블릿 방식으로 했을 경웋
    @GetMapping("/plus2")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append(a + b + "");
    }


    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, int b){
        return a + b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease(){
        increaseNo++;
        return increaseNo;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(int dan, int limit){
        String rs = "";

        for(int i = 1; i <= limit; i++){
            rs += "%d * %d = %d<br>\n".formatted(dan, i, dan * i);
        }
        return rs;
    }
    //http://localhost:8080/gugudan?dan=2&limit=9


    @GetMapping("/gugudan2")
    @ResponseBody
    public String showGugudan2(Integer dan, Integer limit){
        if(null == dan){
            dan = 9;
        }

        if(limit == null){
            limit = 9;
        }


        final Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name){
        String rs = switch (name){
            case "홍길순" -> {
                char j = 'J';
                yield "INF" + j;
            }
            case "임꺽정" ->  "ESFj";
            case "홍길동", "박상원" -> "INFP";
            default -> "모름";
        };

        return rs;
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req){
        HttpSession session = req.getSession();
        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name,  HttpSession session){
        // req => 쿠키 => JSESSIONID => 세션을 얻을 수 있다.
        String value = (String)session.getAttribute(name);

        return "세션변수 %s의 값은 %s입니다.".formatted(name, value);
    }

    private List<Article> articles = new ArrayList<>(
            Arrays.asList(
                    new Article("제목", "내용"),
                    new Article("제목", "내용")
            )
    );

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body){

        Article article = new Article(title, body);
        articles.add(article);

        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }
    //http://localhost:8080/addArticle?title=%EC%A0%9C%EB%AA%A9&body=%EB%82%B4%EC%9A%A9

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id){

        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        return article;
    }


    //http://localhost:8080/modifyArticle/1?titl=chan&body=gkgk
    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body){

        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .get();

        if(article == null){
            return "%d번 게시물은 존재하지 않습니다.".formatted(id);
        }

        article.setTitle(title);
        article.setTitle(body);

        return "%d번 게시물을 수정하였습니다.".formatted(id);
    }

    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id){

        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if(article == null){
            return "%d번 게시물은 존재하지 않습니다.".formatted(id);
        }

        articles.remove(article);

        return "%d번 게시물을 삭제하였습니다..".formatted(id);
    }


    @GetMapping("/addPersonOnlyWay")
    @ResponseBody
    public Person addPersonOnlyWay(int id, int age, String name){
        return new Person(id, age, name);
    }

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    public Person addPerson(Person p){
        return p;
    }


    @AllArgsConstructor
    @Getter
    @Setter
    class Article{

        private static int lastId = 0;

        private int id;
        private String title;
        private String body;

        public Article(String title, String body){
            this(++lastId, title, body);
        }
    }

    @AllArgsConstructor
    @Getter
    class Person{
        private int id;
        private int age;
        private String name;
    }

}

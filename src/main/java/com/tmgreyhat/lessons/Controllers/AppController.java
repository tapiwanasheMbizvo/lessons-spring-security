package com.tmgreyhat.lessons.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {



    @GetMapping("/")
    String main(){

        return "index";


    }

    @GetMapping("/user")
    String userRoute(){


        return  "user";
    }

    @GetMapping("/admin")
    String adminRoute(){

        return  "admin";
    }


}

package net.hqdo.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Qing on 2016/12/1.
 */
@Controller()
public class IndexController {

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:countries";
    }
}

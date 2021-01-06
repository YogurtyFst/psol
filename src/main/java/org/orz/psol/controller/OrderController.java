package org.orz.psol.controller;

import org.orz.psol.model.RespBean;
import org.orz.psol.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/checkout1")
    @ResponseBody
    RespBean checkout1(@RequestParam int num, @RequestParam String choiceId, @RequestParam String userId, @RequestParam int addressId) {
        return orderService.checkout1(num, choiceId, userId, addressId);
    }


}

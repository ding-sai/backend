package com.dingsai.dingsaibackend.controller;

import com.dingsai.dingsaibackend.model.entity.Order;
import com.dingsai.dingsaibackend.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PaymentController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/createOrder/{money}")
//    @RequestBody Order order
    public String createOrder(@PathVariable Double money) {
        Order order = new Order("20240216001","PD001",money,"Example Product","This is an example product for demonstration purposes");
        // 调用支付宝SDK创建订单并返回支付链接
        String payUrl = alipayService.createOrder(order);
        return payUrl;
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        // 处理支付宝异步通知
        String result = alipayService.handleNotify(request);
        return result;
    }
}

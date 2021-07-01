package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;
    @PostMapping(value = "/payment/creat")
    public CommonResult creat(@RequestBody Payment payment){
        Integer result = paymentService.create(payment);
        if(result > 0){
            return new CommonResult(200,"插入数据成功 \t服务端口: "+serverPort,result);
        }else{
            return new CommonResult(500,"插入数据失败 \t服务端口: "+serverPort,null);
        }
    }
    @GetMapping(value = "/payment/queryPaymentById/{id}")
    public CommonResult<Payment> queryPaymentById(@PathVariable("id")Long id){
        Payment payment = paymentService.queryPaymentById(id);
        if(payment != null){
            return new CommonResult(200,"查询成功,\t服务端口: "+serverPort,payment);
        }else{
            return new CommonResult(500,"没有对应记录,查询ID: "+id+"\t服务端口: "+serverPort,null);
        }
    }
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }
}

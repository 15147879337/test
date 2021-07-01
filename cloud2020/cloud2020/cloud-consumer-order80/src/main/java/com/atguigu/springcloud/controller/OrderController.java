package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController {
    /**
     *  public static final String PaymentSrv_URL = "http://localhost:8001";
     */
    public static final String PAYMENT_SRV = "http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  LoadBalancer loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/creat")
    public CommonResult creat(Payment payment){
        return restTemplate.postForObject(PAYMENT_SRV+"/payment/creat",payment,CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/queryPaymentById/{id}")
    public CommonResult<Payment> queryPaymentById(@PathVariable("id")Long id){
    return restTemplate.getForObject(PAYMENT_SRV+"/payment/queryPaymentById/"+id,CommonResult.class,id);
    }
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB()
    {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(instances == null || instances.size() == 0){
            return  null;
        }
        ServiceInstance serviceInstance = loadBalancer.initInstance(instances);
        URI uri = serviceInstance.getUri();

       return restTemplate.getForObject(uri +"/payment/lb",String.class);
    }
}

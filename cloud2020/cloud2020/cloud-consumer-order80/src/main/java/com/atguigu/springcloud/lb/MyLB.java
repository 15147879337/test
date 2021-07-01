package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {
    /**
     *
     */
    private AtomicInteger atomicInteger= new AtomicInteger(0);

    /**
     *
     * @return
     */
    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;

        }while (!atomicInteger.compareAndSet(current,next));
        System.out.println("*************第"+next+"次访问");
        return next;
    }
    @Override
    public ServiceInstance initInstance(List<ServiceInstance> instanceList) {
        int index = getAndIncrement() % instanceList.size();
        return instanceList.get(index);
    }
}

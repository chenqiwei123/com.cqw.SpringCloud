package com.cqw.springcloud.Lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class MyLB implements LoadBalancer{
    private AtomicInteger atomicInteger=new AtomicInteger(0);

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index=incrementAndGetModulo(serviceInstances.size());
        return serviceInstances.get(index);
    }
    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = atomicInteger.get();
            int next = (current + 1) % modulo;
            if (atomicInteger.compareAndSet(current, next))
                return next;
        }
    }
}

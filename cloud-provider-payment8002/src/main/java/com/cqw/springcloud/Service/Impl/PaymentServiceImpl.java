package com.cqw.springcloud.Service.Impl;

import com.cqw.springcloud.Commons.Entity.Payment;
import com.cqw.springcloud.Dao.PaymentDao;

import com.cqw.springcloud.Service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Service
public class PaymentServiceImpl implements PaymentService
{
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment)
    {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id)
    {
        return paymentDao.getPaymentById(id);
    }
}


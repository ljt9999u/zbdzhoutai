package com.example.demo.service.impl;

import com.example.demo.mapper.CustomizedDemandMapper;
import com.example.demo.pojo.CustomizedDemand;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//定制需求
public class CustomizedDemandServiceImpl implements CustomizedDemandService {
    @Autowired
   private CustomizedDemandMapper customizedDemandMapper;
    @Override
    public void addDemand(CustomizedDemand customizedDemand) {
        customizedDemandMapper.addDemand(customizedDemand);
    }
}

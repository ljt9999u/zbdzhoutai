package com.example.demo.service.impl;

import com.example.demo.mapper.CustomizedDemandMapper;
import com.example.demo.pojo.CustomizedDemand;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//定制需求
public class CustomizedDemandServiceImpl implements CustomizedDemandService {
    @Autowired
    private CustomizedDemandMapper customizedDemandMapper;
    
    @Override
    public CustomizedDemand addDemand(CustomizedDemand customizedDemand) {
        customizedDemandMapper.addDemand(customizedDemand);
        // addDemand执行后，id会被自动填充到customizedDemand对象中（通过@Options注解）
        return customizedDemand;
    }
    
    @Override
    public List<CustomizedDemand> getAllDemands() {
        return customizedDemandMapper.getAllDemands();
    }
    
    @Override
    public List<CustomizedDemand> getDemandsByStatus(Integer status) {
        return customizedDemandMapper.getDemandsByStatus(status);
    }
    
    @Override
    public CustomizedDemand getDemandById(Integer id) {
        return customizedDemandMapper.getDemandById(id);
    }
    
    @Override
    public void updateDemandStatus(Integer id, Integer status) {
        customizedDemandMapper.updateDemandStatus(id, status);
    }
}

package com.example.demo.service;

import com.example.demo.pojo.CustomizedDemand;
import java.util.List;

public interface CustomizedDemandService {
    //添加需求
    void addDemand(CustomizedDemand customizedDemand);
    
    //查询所有需求
    List<CustomizedDemand> getAllDemands();
    
    //根据状态查询需求
    List<CustomizedDemand> getDemandsByStatus(Integer status);
    
    //根据ID查询单个需求
    CustomizedDemand getDemandById(Integer id);
    
    //更新需求状态
    void updateDemandStatus(Integer id, Integer status);
}

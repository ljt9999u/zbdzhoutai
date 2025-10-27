package com.example.demo.service.impl;

import com.example.demo.mapper.IdentificationApplicationMapper;
import com.example.demo.pojo.IdentificationApplication;
import com.example.demo.service.IdentificationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationApplicationServiceImpl implements IdentificationApplicationService {

    @Autowired
    private IdentificationApplicationMapper identificationApplicationMapper;

    @Override
    public void submit(IdentificationApplication application) {
        identificationApplicationMapper.insert(application);
    }

    @Override
    public List<IdentificationApplication> listAll() {
        return identificationApplicationMapper.selectAll();
    }

    @Override
    public void updateStatus(IdentificationApplication application) {
        identificationApplicationMapper.updateStatus(application);
    }
}

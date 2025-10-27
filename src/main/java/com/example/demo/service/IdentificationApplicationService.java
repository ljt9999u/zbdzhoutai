package com.example.demo.service;

import com.example.demo.pojo.IdentificationApplication;

import java.util.List;

public interface IdentificationApplicationService {
    void submit(IdentificationApplication application);

    List<IdentificationApplication> listAll();

    void updateStatus(IdentificationApplication application);
}

package com.zs.o2o.service.impl;

import com.zs.o2o.dao.HeadLineDao;
import com.zs.o2o.entity.HeadLine;
import com.zs.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return null;
    }
}

package com.zs.o2o.service;

import com.zs.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}

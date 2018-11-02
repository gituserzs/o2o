package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testHeadLineDao(){
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> headLineList = headLineDao.queryHeadLine(headLine);
        System.out.println(headLineList.size());
    }
}

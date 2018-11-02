package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    AreaDao areaDao;

    @Test
    public void selectAll(){
        List<Area> areas = areaDao.selectAll();
        areas.stream().map(Area::getAreaName).forEach(System.out::println);
    }
}

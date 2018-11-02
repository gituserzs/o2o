package com.zs.o2o.service;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestAreaService extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        // List<Area> areaList = areaService.getAreaList();
        // areaList.stream().map(s->s.getAreaName()).forEach(System.out::println);
    }

}

package com.zs.o2o.dao;

import com.zs.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    List<HeadLine> queryHeadLine( HeadLine headLineCondition);
}

package com.dal.mapper;

import com.dal.domain.Criteria;
import com.dal.domain.ReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {
    int insert(ReplyVO vo);

    ReplyVO read(Long rno);

    int delete(Long rno);

    int update(ReplyVO reply);

    List<ReplyVO> getListWithPaging(@Param("cri")Criteria cri, @Param("bno") Long bno);
}

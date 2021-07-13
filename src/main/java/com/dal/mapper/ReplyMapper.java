package com.dal.mapper;

import com.dal.domain.ReplyVO;

public interface ReplyMapper {
    int insert(ReplyVO vo);

    ReplyVO read(Long rno);

    int delete(Long rno);

    int update(ReplyVO reply);
}

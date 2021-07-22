package com.dal.service;

import com.dal.domain.Criteria;
import com.dal.domain.ReplyPageDTO;
import com.dal.domain.ReplyVO;

import java.util.List;

public interface ReplyService {

    int register(ReplyVO vo);

    ReplyVO get(Long rno);

    int modify(ReplyVO vo);

    int remove(Long rno);

    List<ReplyVO> getList(Criteria cri, Long bno);

    ReplyPageDTO getListPage(Criteria cri, Long bno);
}

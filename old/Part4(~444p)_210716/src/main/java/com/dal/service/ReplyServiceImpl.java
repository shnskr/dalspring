package com.dal.service;

import com.dal.domain.Criteria;
import com.dal.domain.ReplyPageDTO;
import com.dal.domain.ReplyVO;
import com.dal.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        log.info("register........." + vo);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get........." + rno);
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        log.info("modify.........." + vo);
        return mapper.update(vo);
    }

    @Override
    public int remove(Long rno) {
        log.info("remove........." + rno);
        return mapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {
        log.info("get Reply List of a Board " + bno);
        return mapper.getListWithPaging(cri, bno);
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long bno) {
        return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
    }
}

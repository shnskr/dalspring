package com.dal.mapper;

import com.dal.domain.BoardVO;
import com.dal.domain.Criteria;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {
//    @Select("select * from tbl_board where bno > 0")
    List<BoardVO> getList();

    List<BoardVO> getListWithPaging(Criteria cri);

    void insert(BoardVO board);

    void insertSelectKey(BoardVO board);

    BoardVO read(Long bno);

    int delete(Long bno);

    int update(BoardVO board);

    int getTotalCount(Criteria cri);
}

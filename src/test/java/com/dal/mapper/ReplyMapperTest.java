package com.dal.mapper;

import com.dal.config.RootConfig;
import com.dal.domain.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

// SpringRunner.class와 SpringJUnit4ClassRunner.class는 똑같다
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class ReplyMapperTest {

    private Long[] bnoArr = { 4194320L, 4194319L, 4194318L, 4194317L, 4194316L };

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Test
    public void testMapper() {
        log.info(mapper);
    }

    @Test
    public void testCreate() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ReplyVO vo = new ReplyVO();

            //게시물의 번호
            vo.setBno(bnoArr[i % 5]);
            vo.setReply("댓글 테스트 " + i);
            vo.setReplyer("replyer" + i);

            mapper.insert(vo);
        });
    }

    @Test
    public void testRead() {
        Long targetRno = 5L;

        ReplyVO vo = mapper.read(targetRno);

        log.info(vo);
    }

    @Test
    public void testDelete() {
        Long targetRno = 1L;

        mapper.delete(targetRno);
    }

    @Test
    public void testUpdate() {
        Long targetRno = 10L;

        ReplyVO vo = mapper.read(targetRno);

        vo.setReply("Update Reply");

        int count = mapper.update(vo);

        log.info("UPDATE COUNT : " + count);
    }
}

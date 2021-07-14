package com.dal.controller;

import com.dal.domain.Criteria;
import com.dal.domain.ReplyVO;
import com.dal.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@RequestMapping("/replies")
@RestController
public class ReplyController {

    private final ReplyService service;

    // Consumes : Request 데이터 포맷을 정의한다.
    // Produces : Response 데이터 포맷을 정의한다.
    @PostMapping(value = "/new",
            consumes = "application/json",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
        log.info("ReplyVO : " + vo);

        int insertCount = service.register(vo);

        log.info("Reply INSERT COUNT : " + insertCount);

        return insertCount == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/pages/{bno}/{page}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") Long bno,
                                                 @PathVariable("page") int page) {
        log.info("getList................");
        Criteria cri = new Criteria(page, 10);
        log.info(cri);
        return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
    }

    @GetMapping(value = "/{rno}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
        log.info("get : " + rno);
        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{rno}",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("remove : " + rno);
        return service.remove(rno) == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
            value = "/{rno}",
            consumes = "application/json",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> modify(@RequestBody ReplyVO vo,
                                         @PathVariable("rno") Long rno) {
        vo.setRno(rno);

        log.info("rno : " + rno);
        log.info("modify : " + vo);
        return service.modify(vo) == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.dal.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {

    @GetMapping("/all")
    public String doAll() {
        log.info("do all can access everybody");

        return "sample/all";
    }

    @GetMapping("/member")
    public String doMember() {
        log.info("logined member");

        return "sample/member";
    }

    @GetMapping("/admin")
    public String  doAdmin() {
        log.info("admin only");

        return "sample/admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    @GetMapping("/annoMember")
    public void doMember2() {
        log.info("logined annotation member");
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/annoAdmin")
    public void doAdmin2() {
        log.info("admin annotation only");
    }
}

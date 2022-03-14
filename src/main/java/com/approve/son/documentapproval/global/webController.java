package com.approve.son.documentapproval.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class webController {

    @PostMapping(value = "/ping")
    @GetMapping(value = "/ping")
    public String pingCheck() {
        return "arrive";
    }
}

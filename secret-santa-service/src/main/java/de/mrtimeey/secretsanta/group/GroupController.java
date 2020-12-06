package de.mrtimeey.secretsanta.group;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/group")
@AllArgsConstructor
public class GroupController {

    @GetMapping
    public String hello() {
        return "hello";
    }

}

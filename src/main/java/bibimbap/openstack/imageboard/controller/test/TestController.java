package bibimbap.openstack.imageboard.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    String testAPI() {
        return "Conn Success";
    }

    @GetMapping("/{name}")
    String testParamAPI(@PathVariable String name) {
        return new StringBuilder()
                .append("Hello ")
                .append(name)
                .toString();
    }

}

package bibimbap.openstack.imageboard.controller.test;

import bibimbap.openstack.imageboard.util.log.CustomLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    String testAPI(HttpServletRequest request) {
        CustomLogger.log(request,"test");
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

package bibimbap.openstack.imageboard.controller.test;

import bibimbap.openstack.imageboard.util.log.CustomLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    String testGetAPI(HttpServletRequest request) {
        CustomLogger.log(request, "test");
        return "Conn Success";
    }

    @PostMapping("/")
    String testPostAPI(@RequestBody Long id) {
        return "Hello " + id;
    }

    @GetMapping("/{name}")
    String testParamAPI(@PathVariable String name) {
        return new StringBuilder()
                .append("Hello ")
                .append(name)
                .toString();
    }


}

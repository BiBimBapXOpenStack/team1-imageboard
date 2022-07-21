package bibimbap.openstack.imageboard.util.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class CustomLogger {
    private static String format = "[ {} ] : {}";
    public static void log(HttpServletRequest request,String msg) {
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null)
            clientIp = request.getRemoteAddr();
        log.info(format,clientIp, msg);
    }
}

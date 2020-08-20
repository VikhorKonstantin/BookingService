package by.vikhor.bookingservice.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = {"/login", "/signup", "/bookings", "/rooms"})
    public String forwardRequests() {
        return "forward:/";
    }
}


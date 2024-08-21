package likelion.eight.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController implements ErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));

        response.setStatus(status.value());

//        if (status == HttpStatus.UNAUTHORIZED || status == HttpStatus.FORBIDDEN) {
//            return new ModelAndView("redirect:/login");
//        }

        if (status.is4xxClientError()) {
            return new ModelAndView("error/404", model);
        }
        if (status.is5xxServerError()) {
            return new ModelAndView("error/500", model);
        }

        return new ModelAndView("error/error", model);
    }

    protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        if (!mediaType.equals(MediaType.TEXT_HTML)) {
            options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
        }
        return options;
    }
}
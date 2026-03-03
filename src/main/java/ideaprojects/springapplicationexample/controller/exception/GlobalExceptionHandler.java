package ideaprojects.springapplicationexample.controller.exception;

         import jakarta.servlet.http.HttpServletRequest;
         import jakarta.servlet.http.HttpServletResponse;
         import org.springframework.boot.webmvc.error.ErrorController;
         import org.springframework.http.HttpStatus;
         import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;


        @Controller
        @ControllerAdvice
         public class GlobalExceptionHandler implements ErrorController {

            @RequestMapping("/error")
            public String error(HttpServletResponse response) {
                switch (HttpStatus.valueOf(response.getStatus())) {
                    case HttpStatus.NOT_FOUND:
                        return "redirect:error/404";
                    case HttpStatus.FORBIDDEN:
                        return "redirect:error/403";
                    default:
                        return "redirect:/error/500";
                }
            }

            @GetMapping("/error/403")
            public String forbidden() {
                return "public/error/forbidden-error-page";
            }

            @GetMapping("/error/404")
            public String notFound() {
                return "public/error/not-found-error-page";
            }

            @GetMapping("/error/500")
            public String serverError() {
                return "public/error/error-page";
            }

            @ExceptionHandler
            public String handlerException(Exception ex){
                return "public/error/error-page";
            }
    }
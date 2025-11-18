package backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseStatusExceptions {

    public static Exception BAD_REQUEST = new BAD_REQUEST();
    public static Exception NOT_FOUND = new NOT_FOUND();
    public static Exception FORBIDDEN = new FORBIDDEN();
    public static Exception UNAUTHORIZED = new UNAUTHORIZED();
    public static Exception INTERNAL_SERVER_ERROR = new INTERNAL_SERVER_ERROR();

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class BAD_REQUEST extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class NOT_FOUND extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public static class FORBIDDEN extends RuntimeException {
    }


    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public static class UNAUTHORIZED extends RuntimeException {
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class INTERNAL_SERVER_ERROR extends RuntimeException {
    }

}

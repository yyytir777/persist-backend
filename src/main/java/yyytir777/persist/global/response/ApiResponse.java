package yyytir777.persist.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import yyytir777.persist.global.response.error.ErrorCode;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "code", "count", "result"})
public class ApiResponse<T> {

    @JsonProperty("success")
    private boolean isSuccess;

    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        if (result instanceof List<?>) {
            Integer size = ((List<?>) result).size();
            return new ApiResponse<>(true, HttpStatus.OK.toString(), size, result);
        }
        return new ApiResponse<>(true, HttpStatus.OK.toString(), null, result);
    }

    public static ApiResponse<?> onFailure(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getHttpStatus().toString(), null, errorCode.getCode() + " : " + errorCode.getMessage());
    }

    public static <T> ApiResponse<T> onFailure(HttpStatus httpStatus, T result) {
        return new ApiResponse<>(false, httpStatus.toString(), null, result);
    }
}

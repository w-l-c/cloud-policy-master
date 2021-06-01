package cn.rebornauto.platform.common.handler.exception;

import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.exception.BizException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RestController
public class WebExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

	/**
	 * shiro权限 异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public Response processUnauthorizedException(HttpServletRequest request, UnauthorizedException e) {
		logger.error("UnauthorizedException", e);
		return Response.factory().code(403).message("UnauthorizedException");
	}

	/**
	 * 业务异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ BizException.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response processBizException(HttpServletRequest request, BizException e) {
		return Response.bad().body(e.getMessage());
	}

	/**
	 * 参数验证
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<Map> processValidatedError(MethodArgumentNotValidException exception){
		BindingResult result = exception.getBindingResult();
		Map<String,Object> attrMap=new HashMap<String, Object>();
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError e:errors){
				attrMap.put(e.getField(),e.getDefaultMessage());
			}
		}
       return Response.<Map>bad().body(attrMap);
	}

	/**
	 * 参数验证
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ BindException.class})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response<Map> processValidatedError2(BindException exception){
		BindingResult result = exception.getBindingResult();
		Map<String,Object> attrMap=new HashMap<String, Object>();
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError e:errors){
				attrMap.put(e.getField(),e.getDefaultMessage());
			}
		}
		return Response.<Map>bad().body(attrMap);
	}

	/**
	 * 总异常
	 */
	@ExceptionHandler({Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Response processException(Exception e, HttpServletRequest request) {
		logger.error("Exception", e);
		return Response.error().body(e);
	}

}
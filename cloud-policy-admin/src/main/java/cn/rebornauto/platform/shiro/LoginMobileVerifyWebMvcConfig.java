package cn.rebornauto.platform.shiro;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Jul 22, 2019 2:59:03 PM
 */
@Configuration
public class LoginMobileVerifyWebMvcConfig implements WebMvcConfigurer {
 
	/**
	 * 短信验证码验证拦截器拦截所有，排除以下请求
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginMobileVerifyInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/sys/vcode")
		.excludePathPatterns("/sys/doLogin")
		.excludePathPatterns("/sys/doLogout")
		.excludePathPatterns("/401")
		.excludePathPatterns("/403")
		.excludePathPatterns("/sys/getCaptcha")
		.excludePathPatterns("/sys/doLoginMobile")
//		.excludePathPatterns("/stat/**")
		;
	}
}

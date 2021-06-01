package cn.rebornauto.platform.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ShiroProperties shiroProperties() {
        return new ShiroProperties();
    }


    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 验证密码服务
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public SysShiroRealm shiroRealm() {
        SysShiroRealm realm = new SysShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean
    public MemoryConstrainedCacheManager memoryConstrainedCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(memoryConstrainedCacheManager());
        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroProperties shiroProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //新增或者覆盖DefaultFilter
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        filters.put("vcode", validateCodeFilter);

        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/sys/doLogout");
        filters.put("logout", logoutFilter);
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        //覆盖DefaultFilter.logout
        filterChainDefinitionManager.put("/sys/logout", "logout");
        //自己写的登录，不用FormAuthenticationFilter
        filterChainDefinitionManager.put("/sys/doLogin", "anon,vcode");

        //退出成功的页面
        filterChainDefinitionManager.put("/sys/doLogout", "anon");

        //验证码
        filterChainDefinitionManager.put("/sys/vcode", "anon");
        //短信查询
        filterChainDefinitionManager.put("/sms/list/*", "anon");
        
        //测试查询
//        filterChainDefinitionManager.put("/stat/*", "anon");

        filterChainDefinitionManager.put("/", "anon");
        filterChainDefinitionManager.put("/**.html", "anon");
        filterChainDefinitionManager.put("/**.css", "anon");
        filterChainDefinitionManager.put("/**.js", "anon");
        filterChainDefinitionManager.put("/**.png", "anon");

        // 固定写死的值，也可以从数据库或其他配置中读取,也可以单独在每个controller添加注解
        //filterChainDefinitionManager.put("/user/list", "authc,perms[user:list]");
        filterChainDefinitionManager.put("/sys/file/upload", "authc");

        List<String> authcs = shiroProperties.getAuthc();
        authcs.forEach(a->{
            filterChainDefinitionManager.put(a, "authc");
        });

        List<String> anons = shiroProperties.getAnon();
        anons.forEach(a->{
            filterChainDefinitionManager.put(a, "anon");
        });
        filterChainDefinitionManager.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        shiroFilterFactoryBean.setLoginUrl("/401");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        return shiroFilterFactoryBean;
    }


    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }
}

package cn.rebornauto.platform.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "sys")
public class ShiroProperties {

    private List<String> authc = new ArrayList<>();

    private List<String> anon = new ArrayList<>();

    public List<String> getAuthc() {
        return authc;
    }

    public void setAuthc(List<String> authc) {
        this.authc = authc;
    }

    public List<String> getAnon() {
        return anon;
    }

    public void setAnon(List<String> anon) {
        this.anon = anon;
    }
}

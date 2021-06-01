package cn.rebornauto.platform.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.upload")
public class UploadProperties {

    private String local;
    private String url;
    private String modelUrl;
    private String domain;
    private String key;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUrl() {
        return url;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

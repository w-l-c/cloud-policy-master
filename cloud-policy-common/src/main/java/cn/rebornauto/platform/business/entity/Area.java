package cn.rebornauto.platform.business.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "`tb_area`")
public class Area implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`name`")
    private String name;

    /**
     * 城市唯一代码
     */
    @Column(name = "`adcode`")
    private String adcode;

    /**
     * 父级代码
     */
    @Column(name = "`parent_adcode`")
    private String parentAdcode;

    /**
     * 城市编码
     */
    @Column(name = "`citycode`")
    private String citycode;

    /**
     * country/province/city/district
     */
    @Column(name = "`level`")
    private String level;

    /**
     * 经纬度
     */
    @Column(name = "`center`")
    private String center;

    /**
     * 拼音全称
     */
    @Column(name = "`pinyin`")
    private String pinyin;

    /**
     * 拼音简称
     */
    @Column(name = "`short_pinyin`")
    private String shortPinyin;

    /**
     * 是否删除
     */
    @Column(name = "`is_deleted`")
    private Byte isDeleted;

    /**
     * 是否开启
     */
    @Column(name = "`enabled`")
    private Byte enabled;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取城市唯一代码
     *
     * @return adcode - 城市唯一代码
     */
    public String getAdcode() {
        return adcode;
    }

    /**
     * 设置城市唯一代码
     *
     * @param adcode 城市唯一代码
     */
    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
    }

    /**
     * 获取父级代码
     *
     * @return parent_adcode - 父级代码
     */
    public String getParentAdcode() {
        return parentAdcode;
    }

    /**
     * 设置父级代码
     *
     * @param parentAdcode 父级代码
     */
    public void setParentAdcode(String parentAdcode) {
        this.parentAdcode = parentAdcode == null ? null : parentAdcode.trim();
    }

    /**
     * 获取城市编码
     *
     * @return citycode - 城市编码
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 设置城市编码
     *
     * @param citycode 城市编码
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    /**
     * 获取country/province/city/district
     *
     * @return level - country/province/city/district
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置country/province/city/district
     *
     * @param level country/province/city/district
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * 获取经纬度
     *
     * @return center - 经纬度
     */
    public String getCenter() {
        return center;
    }

    /**
     * 设置经纬度
     *
     * @param center 经纬度
     */
    public void setCenter(String center) {
        this.center = center == null ? null : center.trim();
    }

    /**
     * 获取拼音全称
     *
     * @return pinyin - 拼音全称
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音全称
     *
     * @param pinyin 拼音全称
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    /**
     * 获取拼音简称
     *
     * @return short_pinyin - 拼音简称
     */
    public String getShortPinyin() {
        return shortPinyin;
    }

    /**
     * 设置拼音简称
     *
     * @param shortPinyin 拼音简称
     */
    public void setShortPinyin(String shortPinyin) {
        this.shortPinyin = shortPinyin == null ? null : shortPinyin.trim();
    }

    /**
     * 获取是否删除
     *
     * @return is_deleted - 是否删除
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否删除
     *
     * @param isDeleted 是否删除
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取是否开启
     *
     * @return enabled - 是否开启
     */
    public Byte getEnabled() {
        return enabled;
    }

    /**
     * 设置是否开启
     *
     * @param enabled 是否开启
     */
    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", adcode=").append(adcode);
        sb.append(", parentAdcode=").append(parentAdcode);
        sb.append(", citycode=").append(citycode);
        sb.append(", level=").append(level);
        sb.append(", center=").append(center);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", shortPinyin=").append(shortPinyin);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Area other = (Area) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAdcode() == null ? other.getAdcode() == null : this.getAdcode().equals(other.getAdcode()))
            && (this.getParentAdcode() == null ? other.getParentAdcode() == null : this.getParentAdcode().equals(other.getParentAdcode()))
            && (this.getCitycode() == null ? other.getCitycode() == null : this.getCitycode().equals(other.getCitycode()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getCenter() == null ? other.getCenter() == null : this.getCenter().equals(other.getCenter()))
            && (this.getPinyin() == null ? other.getPinyin() == null : this.getPinyin().equals(other.getPinyin()))
            && (this.getShortPinyin() == null ? other.getShortPinyin() == null : this.getShortPinyin().equals(other.getShortPinyin()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAdcode() == null) ? 0 : getAdcode().hashCode());
        result = prime * result + ((getParentAdcode() == null) ? 0 : getParentAdcode().hashCode());
        result = prime * result + ((getCitycode() == null) ? 0 : getCitycode().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getCenter() == null) ? 0 : getCenter().hashCode());
        result = prime * result + ((getPinyin() == null) ? 0 : getPinyin().hashCode());
        result = prime * result + ((getShortPinyin() == null) ? 0 : getShortPinyin().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        return result;
    }
}
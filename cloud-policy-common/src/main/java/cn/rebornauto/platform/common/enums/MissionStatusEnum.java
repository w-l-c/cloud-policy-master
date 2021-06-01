package cn.rebornauto.platform.common.enums;

/**
 * @author ligewei
 * @create 2020/06/10 10:02
 */
public enum MissionStatusEnum {
    /**
     * 已申请1
     */
    MISSION_STATUS_1("已申请", 1),
    /**
     * 2已上传
     */
    MISSION_STATUS_2("未审核", 2),
    /**
     * 3待审核
     */
    MISSION_STATUS_3("已发布", 3),
    /**
     * 已审核4
     */
    MISSION_STATUS_4("已驳回", 4),
    /**
     * 已驳回5
     */
    MISSION_STATUS_5("已认领", 5)
    ;
    private String name;
    private int index;
    private MissionStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public static String getName(int index) {
        for (MissionStatusEnum c : MissionStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}

package cn.rebornauto.platform.common.enums;

/**
 * @author ligewei
 * @create 2020/06/23 10:03
 */
public enum ContractTypeEnum
{
    CONTRACT_TYPE_1("共享经济合作协议",1),
    CONTRACT_TYPE_2("厦门思明区税务局委托书",2);
    private String name;
    private int index;

    private ContractTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ContractTypeEnum c : ContractTypeEnum.values()) {
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

    public static boolean contains(int index){
        for(ContractTypeEnum c : ContractTypeEnum.values()){
            if(c.getIndex()==index) {
                return true;
            }
        }
        return false;
    }
}

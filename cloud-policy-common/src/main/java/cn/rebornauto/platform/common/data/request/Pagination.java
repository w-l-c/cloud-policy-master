package cn.rebornauto.platform.common.data.request;


import lombok.Data;

@Data
public class Pagination {

    private long total; //总条数
    private int current;// 当前页
    private int pageSize;//每页数据条数
    private int offset;//起始位置

    public int getOffset() {
        int pageCount = (int) ((total + pageSize - 1) / pageSize);
        if (current > pageCount) {
            this.current = pageCount;
        }
        current = current<1?1:current;
        int index = (current - 1) * pageSize;
        return index<0?0:index;
    }

}

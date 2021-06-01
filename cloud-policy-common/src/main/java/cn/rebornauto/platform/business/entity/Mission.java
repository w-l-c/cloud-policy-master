package cn.rebornauto.platform.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "`t_mission`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mission implements Serializable {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 任务编号（YXW—TASK-时间戳加随机码）
     */
    @Column(name = "`mission_no`")
    private String missionNo;

    /**
     * 客户编号
     */
    @Column(name = "`customer_id`")
    private String customerId;

    /**
     * 发布时间
     */
    @Column(name = "`releasetime`")
    private LocalDateTime releasetime;

    /**
     * 任务名称
     */
    @Column(name = "`mission_name`")
    private String missionName;

    /**
     * 任务描述
     */
    @Column(name = "`mission_remark`")
    private String missionRemark;

    /**
     * 任务金额
     */
    @Column(name = "`amount`")
    private BigDecimal amount;

    /**
     * 清单
     */
    @Column(name = "`excel`")
    private String excel;

    /**
     * 文件
     */
    @Column(name = "`file`")
    private String file;

    /**
     * 图片
     */
    @Column(name = "`image`")
    private String image;

    /**
     * 任务状态 （1：已申请 2：待审核 3：已发布 4：已驳回 5：已认领）
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 数据状态   1有效   -1无效
     */
    @Column(name = "`data_status`")
    private Integer dataStatus;

    /**
     * 创建人
     */
    @Column(name = "`createoper`")
    private String createoper;

    /**
     * 创建时间
     */
    @Column(name = "`createtime`")
    private LocalDateTime createtime;

    /**
     * 修改人
     */
    @Column(name = "`modifyoper`")
    private String modifyoper;

    /**
     * 修改时间
     */
    @Column(name = "`modifytime`")
    private LocalDateTime modifytime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", missionNo=").append(missionNo);
        sb.append(", customerId=").append(customerId);
        sb.append(", releasetime=").append(releasetime);
        sb.append(", missionName=").append(missionName);
        sb.append(", missionRemark=").append(missionRemark);
        sb.append(", amount=").append(amount);
        sb.append(", excel=").append(excel);
        sb.append(", file=").append(file);
        sb.append(", image=").append(image);
        sb.append(", status=").append(status);
        sb.append(", dataStatus=").append(dataStatus);
        sb.append(", createoper=").append(createoper);
        sb.append(", createtime=").append(createtime);
        sb.append(", modifyoper=").append(modifyoper);
        sb.append(", modifytime=").append(modifytime);
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
        Mission other = (Mission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMissionNo() == null ? other.getMissionNo() == null : this.getMissionNo().equals(other.getMissionNo()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getReleasetime() == null ? other.getReleasetime() == null : this.getReleasetime().equals(other.getReleasetime()))
            && (this.getMissionName() == null ? other.getMissionName() == null : this.getMissionName().equals(other.getMissionName()))
            && (this.getMissionRemark() == null ? other.getMissionRemark() == null : this.getMissionRemark().equals(other.getMissionRemark()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getExcel() == null ? other.getExcel() == null : this.getExcel().equals(other.getExcel()))
            && (this.getFile() == null ? other.getFile() == null : this.getFile().equals(other.getFile()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDataStatus() == null ? other.getDataStatus() == null : this.getDataStatus().equals(other.getDataStatus()))
            && (this.getCreateoper() == null ? other.getCreateoper() == null : this.getCreateoper().equals(other.getCreateoper()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getModifyoper() == null ? other.getModifyoper() == null : this.getModifyoper().equals(other.getModifyoper()))
            && (this.getModifytime() == null ? other.getModifytime() == null : this.getModifytime().equals(other.getModifytime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMissionNo() == null) ? 0 : getMissionNo().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getReleasetime() == null) ? 0 : getReleasetime().hashCode());
        result = prime * result + ((getMissionName() == null) ? 0 : getMissionName().hashCode());
        result = prime * result + ((getMissionRemark() == null) ? 0 : getMissionRemark().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getExcel() == null) ? 0 : getExcel().hashCode());
        result = prime * result + ((getFile() == null) ? 0 : getFile().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDataStatus() == null) ? 0 : getDataStatus().hashCode());
        result = prime * result + ((getCreateoper() == null) ? 0 : getCreateoper().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getModifyoper() == null) ? 0 : getModifyoper().hashCode());
        result = prime * result + ((getModifytime() == null) ? 0 : getModifytime().hashCode());
        return result;
    }
}
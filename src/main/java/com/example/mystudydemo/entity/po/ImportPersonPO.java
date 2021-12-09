package com.example.mystudydemo.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 * @author zsp
 * @since 2021-10-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ho_person")
@ApiModel(value="ImportPersonPO对象", description="")
public class ImportPersonPO implements Serializable {

    @TableField("xm")
    private String xm;

    @TableField("sjh")
    private String sjh;

    @TableField("jybh")
    private String jybh;

    @TableField("sfzh")
    private String sfzh;

    public ImportPersonPO(String xm, String sjh, String jybh) {
        this.xm = xm;
        this.sjh = sjh;
        this.jybh = jybh;
    }

    @TableField("ssbm")
    private String ssbm;

    @TableField("password")
    private String password;
    /**
     * 插入person表后返回的id
     */
    @TableField("person")
    private Long person;
}

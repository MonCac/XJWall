package com.team.xjwall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 帖子类型表
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Type对象", description="帖子类型表")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子类型id")
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    @ApiModelProperty(value = "类型名")
    private String typeName;


}

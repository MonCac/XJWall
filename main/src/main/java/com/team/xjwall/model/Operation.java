package com.team.xjwall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作表
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Operation对象", description="操作表")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作id")
    @TableId(value = "operation_id", type = IdType.AUTO)
    private Integer operationId;

    @ApiModelProperty(value = "操作名")
    private String operationName;


}

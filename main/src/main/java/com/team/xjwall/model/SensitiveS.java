package com.team.xjwall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SensitiveS对象", description="敏感词表")
public class SensitiveS implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "敏感词id")
    @TableId(value = "word_id", type = IdType.AUTO)
    private Integer wordId;

    @ApiModelProperty(value = "敏感词")
    private String word;


}

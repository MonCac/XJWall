package com.team.xjwall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserPost对象", description="")
public class UserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "帖子id")
    private Integer postId;

    @ApiModelProperty(value = "是否点赞，是为1，默认0")
    private Integer islike;

    @ApiModelProperty(value = "是否收藏，是为1，默认0")
    private Integer iscollected;

    @ApiModelProperty(value = "是否浏览，是为1，默认0")
    private Integer isviewed;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除，删除为1，默认0")
    private Integer deleted;

    @ApiModelProperty(value = "是否举报，默认为0")
    private Integer isreported;


}

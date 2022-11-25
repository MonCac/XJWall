package com.team.xjwall.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
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
@ApiModel(value="Post对象", description="")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子id")
    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    @ApiModelProperty(value = "帖子类型")
    private Integer typeId;

    @ApiModelProperty(value = "帖子标题")
    private String title;

    @ApiModelProperty(value = "内容概述")
    private String content;

    @ApiModelProperty(value = "发帖人id")
    private Integer ownerId;

    @ApiModelProperty(value = "父帖id")
    private Integer fatherPost;

    @ApiModelProperty(value = "点赞数")
    private Integer likes;

    @ApiModelProperty(value = "收藏数")
    private Integer collections;

    @ApiModelProperty(value = "浏览次数")
    private Integer views;

    @ApiModelProperty(value = "是否匿名（匿名为1不匿名为0，默认0）")
    private Integer anonymous;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除，删除为1，默认0")
    private Integer deleted;


}

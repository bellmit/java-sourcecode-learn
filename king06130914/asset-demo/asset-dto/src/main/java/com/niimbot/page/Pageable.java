package com.niimbot.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * mp分页查询
 *
 */
public abstract class Pageable {

    /**
     * 默认分页位移和分页大小
     */
    protected static final int DEFAULT_PAGESIZE = 10;
    protected static final int DEFAULT_PAGENUM = 1;

    /**
     * 每页显示条数，默认 10
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "每页显示记录数")
    public long pageSize = 10;
    /**
     * 当前页
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "当前页码")
    public long pageNum = 1;


    public Pageable() {
    }

    public Pageable(long pageSize, long pageNum) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGESIZE;
        }
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGENUM;
        }
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    /**
     * 构造mybatis plus分页查询参数对象
     *
     * @param <T>
     * @return
     */
    public <T> Page<T> buildIPage() {
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 构造mybatis plus分页查询参数对象
     *
     * @param <T>
     * @return
     */
    public <T> Page<T> buildIPage(String tableAliasName) {
        return new Page<>(pageNum, pageSize);
    }


}

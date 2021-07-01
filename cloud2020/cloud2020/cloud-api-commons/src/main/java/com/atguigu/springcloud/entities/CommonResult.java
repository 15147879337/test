package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 10495
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResult<T> implements Serializable {
    public CommonResult(Integer state, String message){
        this(state,message,null);
    }
    private Integer state;
    private String message;
    private T date;
}

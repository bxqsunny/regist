package com.hbpu.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ResultDto <T> {
    /**
     * 提示是否有数据
     */
    @NonNull
    private boolean tip;
    /**
     * 数据信息
     */
    @NonNull
    private String msg;
    /**
     * 数据
     */
    private T data;
}

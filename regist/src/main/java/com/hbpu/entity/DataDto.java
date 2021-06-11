package com.hbpu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDto <T> {

    private Integer page = 1;

    private Integer limit = 5;

    private T data;
}

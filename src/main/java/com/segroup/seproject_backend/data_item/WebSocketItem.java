package com.segroup.seproject_backend.data_item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketItem<T> {
    private String type;
    private T object;
}

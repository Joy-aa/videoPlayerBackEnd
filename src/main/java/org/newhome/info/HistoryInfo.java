package org.newhome.info;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryInfo {
    private Integer historyId;

    private Integer userId;

    private Integer vedioId;

    private LocalDateTime watchTime;
}

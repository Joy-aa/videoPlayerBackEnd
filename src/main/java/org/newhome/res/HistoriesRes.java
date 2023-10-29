package org.newhome.res;

import lombok.Data;
import org.newhome.entity.History;

import java.util.ArrayList;
import java.util.List;

@Data
public class HistoriesRes {
    private List<History> histories = new ArrayList<>();
}

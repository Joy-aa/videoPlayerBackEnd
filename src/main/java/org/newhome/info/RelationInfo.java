package org.newhome.info;

import lombok.Data;

@Data
public class RelationInfo {
    private Integer relationId;

    private Integer user1Id;

    private Integer user2Id;

    private Integer kind;
}

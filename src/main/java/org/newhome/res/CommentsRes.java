package org.newhome.res;

import lombok.Data;
import org.newhome.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentsRes {
    private List<Comment> comments = new ArrayList<>();
}

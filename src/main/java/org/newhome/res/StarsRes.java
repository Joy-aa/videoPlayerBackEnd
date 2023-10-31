package org.newhome.res;

import lombok.Data;
import org.newhome.entity.Star;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.List;

@Data
public class StarsRes {
    private List<Star> stars  = new ArrayList<>();
}

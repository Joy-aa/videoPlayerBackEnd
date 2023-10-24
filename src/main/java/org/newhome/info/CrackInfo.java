package org.newhome.info;

import lombok.Data;
import org.newhome.entity.Crack;
import org.newhome.util.MyBeanUtils;

@Data
public class CrackInfo {

    private Integer pictureId;

    private Float crackLength;

    private Float crackMaxwidth;

    private Float crackAvgwidth;

    private Integer crackId;

    private Integer crackXmin;

    private Integer crackXmax;

    private Integer crackYmin;

    private Integer crackYmax;

    public CrackInfo(){}

    public CrackInfo(Crack crack){
        MyBeanUtils.copyProperties(crack, this);
    }

    public Crack change(){
        Crack crack = new Crack();
        MyBeanUtils.copyProperties(this, crack);
        return crack;
    }
}

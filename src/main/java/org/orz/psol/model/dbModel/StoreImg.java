package org.orz.psol.model.dbModel;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2020-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreImg对象", description="")
public class StoreImg implements Serializable {

    private static final long serialVersionUID=1L;

    private String storeId;

    private String url;

    private String link;

    private Integer order;

    private Boolean isSwipper;

    private Integer swipperOrder;


}

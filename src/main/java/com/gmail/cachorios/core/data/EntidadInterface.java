package com.gmail.cachorios.core.data;

import java.io.Serializable;

public interface EntidadInterface extends Serializable {

    Long getId();

    int getVersion();
    
    boolean isNew();
    
    
}

package com.niimbot.asset.framework.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FailureResult {

    String error;

    @JsonIgnore
    Integer bizCode;
}

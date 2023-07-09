package com.moormic.f1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Circuit {
    private String id;
    private String name;
    @JsonProperty("image")
    private String imageUrl;
}

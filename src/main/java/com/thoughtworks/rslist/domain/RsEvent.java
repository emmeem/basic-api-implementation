package com.thoughtworks.rslist.domain;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RsEvent implements Serializable {
    @NotNull private String eventName;
    @NotNull private String keyWord;
    @NotNull private String userId;
}

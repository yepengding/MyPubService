package org.veritasopher.mypubservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Encrypted {

    private String file;
    private String key;
    private String decryptor;

}

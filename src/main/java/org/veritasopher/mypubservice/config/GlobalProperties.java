package org.veritasopher.mypubservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("global")
@Getter
@Setter
public class GlobalProperties {

    /**
     * Folder location for workspace
     */
    private String location = "workspace";

    /**
     * Encryptor name
     */
    private String encryptor = "mypub-encryptor.exe";

}

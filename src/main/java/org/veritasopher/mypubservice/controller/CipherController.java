package org.veritasopher.mypubservice.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.veritasopher.mypubservice.common.CommonResult;
import org.veritasopher.mypubservice.config.GlobalProperties;
import org.veritasopher.mypubservice.model.Encrypted;
import org.veritasopher.mypubservice.service.WorkspaceService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/cipher")
public class CipherController {

    private final Path rootLocation;
    private final String encryptor_name;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    public CipherController(GlobalProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.encryptor_name = properties.getEncryptor();
    }

    @PostMapping("/encrypt")
    @ResponseBody
    public CommonResult<Encrypted> encrypt(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        // Generate id
        String id;
        try (InputStream is = file.getInputStream()) {
            id = DigestUtils.md5Hex(is) + UUID.randomUUID();
            id = "EN" + DigestUtils.md5Hex(id).substring(0, 8);
        }

        Assert.notNull(id, "Failed to generate ID.");

        // Generate secret key
        String key = DigestUtils.sha3_512Hex(id + UUID.randomUUID());

        // Persist file
        workspaceService.store(id, file);

        // Get paths
        String workspace = rootLocation.toAbsolutePath().toString();
        String encryptor = rootLocation.resolve(encryptor_name).toAbsolutePath().toString();
        String input_file = rootLocation.resolve(id).toAbsolutePath().toString();

        // Run encryptor
        Process process = new ProcessBuilder(encryptor, input_file, "-k=" + key, "-n=" + id, "-o=" + workspace + File.separator).start();
        process.waitFor();

        return CommonResult.success(new Encrypted(id + ".enc", id + ".key", id + ".dec.exe"));
    }

}

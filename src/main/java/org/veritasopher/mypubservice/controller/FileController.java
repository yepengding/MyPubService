package org.veritasopher.mypubservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.veritasopher.mypubservice.service.WorkspaceService;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private WorkspaceService workspaceService;


    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getFileByCID(@PathVariable String name) {

        Resource file = workspaceService.loadAsResource(name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}

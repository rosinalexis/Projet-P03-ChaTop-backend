package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.TokenDto;
import com.openclassrooms.chatpo.exceptions.FileNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
@Tag(name = "File Management")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Operation(
            description = "Get endpoint to serve a file from the server. The file is identified by its filename and is returned as a downloadable resource.",
            summary = "Serve a file as a downloadable resource",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File served successfully"),
                    @ApiResponse(responseCode = "404",
                            description = "File not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TokenDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("Served file: {}", resource.getFilename());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }
}


package com.pcfz.employee_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Employee Profile Photo mapping
 * maps URLs beginning with /uploads/employees/ to the uploads/employees directory relative to the application's working directory.
 */
@Configuration
public class UploadedPhotoConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadLocation = Paths.get("uploads", "employees")
                .toAbsolutePath()
                .normalize()
                .toUri()
                .toString();

        // Make sure the resource location ends with /
        if (!uploadLocation.endsWith("/")) {
            uploadLocation += "/";
        }

        registry.addResourceHandler("/uploads/employees/**")
                .addResourceLocations(uploadLocation);
    }
}

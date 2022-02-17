package eu.coatrack.admin.service;

import eu.coatrack.admin.YggAdminApplication;
import eu.coatrack.admin.controllers.ProxyDockerComposeTemplateFileNotFoundException;
import eu.coatrack.admin.controllers.ProxyDockerComposeTemplateInitializationFailedException;
import eu.coatrack.admin.model.repository.ProxyRepository;
import eu.coatrack.api.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GatewayDockerComposeFileProviderService {

    private static final Logger log = LoggerFactory.getLogger(GatewayDockerComposeFileProviderService.class);
    private static final String gatewayDockerComposeTemplateContent = loadContentFromGatewayDockerComposeTemplateFile();

    @Autowired
    private ProxyRepository proxyRepository;

    @Value("${ygg.proxy.generate-bootstrap-properties.spring.cloud.config.uri}")
    private String springCloudConfigUri;

    private static String loadContentFromGatewayDockerComposeTemplateFile() {
        try {
            URL resource = YggAdminApplication.class.getClassLoader().getResource("proxy-docker-compose-template.yml");
            if (resource == null)
                throw new ProxyDockerComposeTemplateFileNotFoundException();

            Path pathToTemplate = Paths.get(resource.toURI());
            byte[] encoded = Files.readAllBytes(pathToTemplate);
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ProxyDockerComposeTemplateInitializationFailedException();
        }
    }

    public String provideDockerComposeFileContentOfGateway(String gatewayId) {
        log.info("About to search for proxy: " + gatewayId);
        Proxy gateway = proxyRepository.findById(gatewayId);
        log.info("Found gateway: " + gateway.getId());
        return gatewayDockerComposeTemplateContent
                .replace("<gateway-id>", gatewayId)
                .replace("<config-server-uri>", springCloudConfigUri)
                .replace("<username>", gateway.getConfigServerUsername())
                .replace("<password>", gateway.getConfigServerPassword());
    }

}

package id.co.mii.mockup.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/")
public class SystemController {

    @Autowired
    Environment environment;

    @Autowired
    private BuildProperties buildProperties;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getRequest() {
        Map<String, String> appInfo = new LinkedHashMap<>();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port");
            appInfo.put("name", this.buildProperties.getName());
            appInfo.put("version", this.buildProperties.getVersion());
            appInfo.put("server", ip);
            appInfo.put("message", "SUCCESS");
            return new ResponseEntity<>(appInfo, HttpStatus.OK);
        }catch (Exception e){
            appInfo.put("message", e.getMessage());
            return new ResponseEntity<>(appInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

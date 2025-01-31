package id.co.mii.mockup.controller;

import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.mockup.dto.request.Item;
import id.co.mii.mockup.dto.response.BaseResponse;
import id.co.mii.mockup.dto.response.ItemResponse;
import id.co.mii.mockup.exception.ResourceNotFoundException;
import id.co.mii.mockup.service.MyService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }
    
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name) {
        return myService.getGreeting(name);
    }

    @PostMapping("/add")
    public String addItem(@RequestBody Item item) {
        if (item == null) {
            throw new ResourceNotFoundException("Item not found");
        }
        
        return "Item added: " + item.getName();
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam String keyword, String key2) {
        return "Searching for: " + keyword + " key2: " + key2;
    }

    @PostMapping("/headers")
    public Map<String, String> getHeader(HttpServletRequest request, @RequestHeader Map<String, String> headersData) {
        System.out.println(request);

        System.out.println("key - value");
        headersData.forEach((key, value) -> {
            if (key != null && value != null) {
                System.out.println(key+" - "+value);
            }
        });
        
        return headersData;
    }

    @PostMapping("/request")
    public Map<String, String> getRequest(HttpServletRequest request) {
                
        System.out.println("header:"); //@RequestHeader
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        if (requestHeaderNames != null) {
            while (requestHeaderNames.hasMoreElements()) {
                String headerName = requestHeaderNames.nextElement();
                
                System.out.println(headerName+" | "+request.getHeader(headerName));
            }
        }

        System.out.println("params:"); //@RequestParam
        Enumeration<String> requestParameterNames = request.getParameterNames();
        if (requestParameterNames != null) {
            while (requestParameterNames.hasMoreElements()) {
                String paramName = requestParameterNames.nextElement();
                
                System.out.println(paramName+" | "+request.getParameter(paramName));
            }
        }

        System.out.println(request.getRemoteAddr());
        System.out.println(request.getLocalAddr());
        
        return null;
    }

    @PostMapping(value = "/response", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> responseItem(@RequestBody Item item) {

        ItemResponse itemResponse = new ItemResponse();

        Item itemData = new Item();
        
        itemData.setId(UUID.randomUUID().toString());
        itemData.setName(item.getName());

        itemResponse.setItemData(itemData);

        itemResponse.setResponseCode("200");
        itemResponse.setResponseMessage("Success");
        itemResponse.setResponseStatus(HttpStatus.OK);
        return new ResponseEntity<>(itemResponse, itemResponse.getResponseStatus());
    }
}

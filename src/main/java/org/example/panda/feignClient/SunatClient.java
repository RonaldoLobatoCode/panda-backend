package org.example.panda.feignClient;

import org.example.panda.feignClient.response.ReniecResponse;
import org.example.panda.feignClient.response.SunatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sunat-client", url = "https://api.apis.net.pe/v2/sunat/")
public interface SunatClient {
    @GetMapping("/ruc")
    SunatResponse getInfo(@RequestParam("numero") String numero,
                          @RequestHeader("Authorization")String token);
    //dni?numero=73005607
}

package ec.domain.remote.feign;

import ec.domain.core.record.remote.ClienteTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${remote.microservice.cliente-url}")
public interface ClienteRemoteFeignClientService {

  @GetMapping("/api/query/clientes/{idCliente}")
  ClienteTo obtenerPorId(@PathVariable("idCliente") Integer idCliente);
}

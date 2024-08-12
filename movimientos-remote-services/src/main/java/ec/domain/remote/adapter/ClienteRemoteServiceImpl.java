package ec.domain.remote.adapter;

import ec.domain.application.ports.output.remote.ClienteRemoteService;
import ec.domain.core.record.remote.ClienteTo;
import ec.domain.remote.feign.ClienteRemoteFeignClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteRemoteServiceImpl implements ClienteRemoteService {

  private final ClienteRemoteFeignClientService remoteFeignClientService;

  @Override
  public ClienteTo obtenerClientePorId(Integer idCliente) {
    return remoteFeignClientService.obtenerPorId(idCliente);
  }
}

package ec.domain.application.ports.output.remote;

import ec.domain.core.record.remote.ClienteTo;

public interface ClienteRemoteService {

  ClienteTo obtenerClientePorId(Integer idCliente);
}

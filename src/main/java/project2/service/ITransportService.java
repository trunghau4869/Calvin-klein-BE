package project2.service;

import project2.model.Transport;

import java.util.List;
import java.util.Optional;

public interface ITransportService {

    List<Transport> getAllTransport();

    Optional<Transport> getTransportById(Long id);

}

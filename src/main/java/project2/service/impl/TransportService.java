package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.Transport;
import project2.repository.ITransportRepository;
import project2.service.ITransportService;
import java.util.List;
import java.util.Optional;


@Service
public class TransportService implements ITransportService {
    @Autowired
    private ITransportRepository transportRepository;

    @Override
    public List<Transport> getAllTransport() {
        return transportRepository.findAll();
    }

    @Override
    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }
}

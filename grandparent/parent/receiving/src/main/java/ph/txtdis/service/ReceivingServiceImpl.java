package ph.txtdis.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ph.txtdis.model.Receiving;
import ph.txtdis.model.ReceivingDetail;
import ph.txtdis.model.ReceivingSummary;
import ph.txtdis.repository.ReceivingRepository;
import ph.txtdis.repository.ReceivingSummaryRepository;

@Service
@Transactional()
public class ReceivingServiceImpl extends AbstractService<Receiving, Integer> implements ReceivingService {

    @Autowired
    private ReceivingRepository repository;

    @Autowired
    private ReceivingSummaryRepository summaryRepository;

    protected ReceivingServiceImpl() {
    }

    @Override
    public Integer getMinId() {
        return repository.getMinId();
    }

    @Override
    public Integer getMaxId() {
        return repository.getMaxId();
    }

    @Override
    public List<ReceivingDetail> getDetails(int id) {
        return repository.getDetails(id);
    }

    @Override
    public List<ReceivingSummary> getSummary(LocalDate startDate, LocalDate endDate) {
        return summaryRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public LocalDate getDateFromPurchaseOrder(int id) {
        return repository.getDateFromPurchaseOrder(id);
    }
}

package ph.txtdis.dto;

import org.springframework.stereotype.Component;

import ph.txtdis.model.Warehouse;
import ph.txtdis.service.WarehouseService;

@Component
public class WarehouseDTOImpl extends AbstractTypedDTO<Warehouse, WarehouseService> implements WarehouseDTO {
}
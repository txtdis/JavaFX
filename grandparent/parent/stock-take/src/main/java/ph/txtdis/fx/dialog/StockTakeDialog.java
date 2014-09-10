package ph.txtdis.fx.dialog;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ph.txtdis.App;
import ph.txtdis.dto.ItemDTO;
import ph.txtdis.dto.StockTakeDTO;
import ph.txtdis.exception.NotFoundException;
import ph.txtdis.fx.input.InputNode;
import ph.txtdis.fx.input.LabeledComboBox;
import ph.txtdis.fx.input.LabeledDecimalField;
import ph.txtdis.fx.input.LabeledIdNameField;
import ph.txtdis.model.Item;
import ph.txtdis.model.StockTake;
import ph.txtdis.model.StockTakeDetail;
import ph.txtdis.type.QualityType;
import ph.txtdis.type.UomType;
import ph.txtdis.util.Login;

public class StockTakeDialog extends AbstractFieldDialog<StockTakeDetail, StockTakeDTO> {

    private LabeledIdNameField itemField;
    private ItemDTO itemDTO;

    public StockTakeDialog(Stage stage, StockTakeDTO dto) {
        super("Stock Take", stage, dto);
        setListeners();
    }

    private void setListeners() {
        itemDTO = App.getContext().getBean(ItemDTO.class);
        itemField.getIdField().addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.TAB) {
                int id = itemField.getValue();
                if (itemDTO.exists(id)) {
                    actWhenFound(id);
                } else {
                    try {
                        throw new NotFoundException("Item ID No. " + id);
                    } catch (Exception e) {
                        actOnError(this, e);
                    }
                }
            }
        });
    }
    
    private void actWhenFound(int id) {
        itemDTO.setId(id);
        itemField.getNameField().setText(itemDTO.getName());
    }

    private void actOnError(Stage stage, Exception e) {
        new ErrorDialog(stage, e.getMessage());
        inputNodes.forEach(inputNode -> inputNode.reset());
    }

    @Override
    protected List<InputNode<?>> addNodes() {

        itemField = new LabeledIdNameField("Item ID", 18);        
        LabeledComboBox<UomType> uomCombo = new LabeledComboBox<>("UOM", UomType.values());
        LabeledDecimalField qtyField = new LabeledDecimalField("Quantity");
        LabeledComboBox<QualityType> qualityCombo = new LabeledComboBox<>("Quality", QualityType.values());
        
        return Arrays.asList(itemField, uomCombo, qtyField, qualityCombo);

    }

    @Override
    protected StockTakeDetail createEntity(StockTakeDTO dto, List<InputNode<?>> inputNodes) {

        ItemDTO itemDTO = App.getContext().getBean(ItemDTO.class);

        StockTake stockTake = dto.get();
        Item item = itemDTO.get();
        UomType uom = getInputAtRow(1);
        BigDecimal qty = getInputAtRow(2);
        QualityType quality = getInputAtRow(3);

        StockTakeDetail detail = new StockTakeDetail(stockTake, item, uom, qty, quality);
        detail.setCreatedBy(Login.user());
        return detail;
    }
}

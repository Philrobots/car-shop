package ca.ulaval.glo4003.mainResources;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.assemblers.CustomerDtoAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryCompletedResponseAssembler;
import ca.ulaval.glo4003.evulution.api.delivery.assemblers.DeliveryLocationDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.LoginDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.TokenResponseAssembler;
import ca.ulaval.glo4003.evulution.api.sale.assemblers.*;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.delivery.DeliveryCompleteAssembler;
import ca.ulaval.glo4003.evulution.service.manufacture.EstimatedRangeAssembler;
import ca.ulaval.glo4003.evulution.service.sale.SaleCreatedAssembler;

public class AssemblerResources {

    private SaleCreatedAssembler saleCreatedAssembler = new SaleCreatedAssembler();
    private DeliveryCompleteAssembler deliveryCompleteAssembler = new DeliveryCompleteAssembler();
    private TokenAssembler tokenAssembler = new TokenAssembler();
    private TokenDtoAssembler tokenDtoAssembler = new TokenDtoAssembler();
    private EstimatedRangeAssembler estimatedRangeAssembler = new EstimatedRangeAssembler();
    private CustomerDtoAssembler customerDtoAssembler = new CustomerDtoAssembler();
    private LoginDtoAssembler loginDtoAssembler = new LoginDtoAssembler();
    private TokenResponseAssembler tokenResponseAssembler = new TokenResponseAssembler();
    private ChooseCarDtoAssembler chooseCarDtoAssembler = new ChooseCarDtoAssembler();
    private ChooseBatteryDtoAssembler chooseBatteryDtoAssembler = new ChooseBatteryDtoAssembler();
    private EstimatedRangeResponseAssembler estimatedRangeResponseAssembler = new EstimatedRangeResponseAssembler();
    private SaleResponseAssembler saleResponseAssembler = new SaleResponseAssembler();
    private InvoiceDtoAssembler invoiceDtoAssembler = new InvoiceDtoAssembler();
    private DeliveryCompletedResponseAssembler deliveryCompletedResponseAssembler = new DeliveryCompletedResponseAssembler();
    private DeliveryLocationDtoAssembler deliveryLocationDtoAssembler = new DeliveryLocationDtoAssembler();

    public SaleCreatedAssembler getSaleCreatedAssembler() {
        return saleCreatedAssembler;
    }

    public DeliveryCompleteAssembler getDeliveryCompleteAssembler() {
        return deliveryCompleteAssembler;
    }

    public TokenAssembler getTokenAssembler() {
        return tokenAssembler;
    }

    public TokenDtoAssembler getTokenDtoAssembler() {
        return tokenDtoAssembler;
    }

    public EstimatedRangeAssembler getEstimatedRangeAssembler() {
        return estimatedRangeAssembler;
    }

    public CustomerDtoAssembler getCustomerDtoAssembler() {
        return customerDtoAssembler;
    }

    public LoginDtoAssembler getLoginDtoAssembler() {
        return loginDtoAssembler;
    }

    public TokenResponseAssembler getTokenResponseAssembler() {
        return tokenResponseAssembler;
    }

    public ChooseCarDtoAssembler getChooseCarDtoAssembler() {
        return chooseCarDtoAssembler;
    }

    public ChooseBatteryDtoAssembler getChooseBatteryDtoAssembler() {
        return chooseBatteryDtoAssembler;
    }

    public EstimatedRangeResponseAssembler getEstimatedRangeResponseAssembler() {
        return estimatedRangeResponseAssembler;
    }

    public SaleResponseAssembler getSaleResponseAssembler() {
        return saleResponseAssembler;
    }

    public InvoiceDtoAssembler getInvoiceDtoAssembler() {
        return invoiceDtoAssembler;
    }

    public DeliveryCompletedResponseAssembler getDeliveryCompletedResponseAssembler() {
        return deliveryCompletedResponseAssembler;
    }

    public DeliveryLocationDtoAssembler getDeliveryLocationDtoAssembler() {
        return deliveryLocationDtoAssembler;
    }
}

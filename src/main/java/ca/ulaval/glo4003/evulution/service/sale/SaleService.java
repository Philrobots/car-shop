package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.*;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceFactory;
import ca.ulaval.glo4003.evulution.domain.invoice.InvoiceRepository;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

public class SaleService {
    private SaleRepository saleRepository;
    private TokenRepository tokenRepository;
    private InvoiceRepository invoiceRepository;
    private TokenAssembler tokenAssembler;
    private TransactionIdAssembler transactionIdAssembler;
    private TransactionIdFactory transactionIdFactory;
    private SaleFactory saleFactory;
    private CarFactory carFactory;
    private BatteryFactory batteryFactory;
    private InvoiceFactory invoiceFactory;
    private EstimatedRangeAssembler estimatedRangeAssembler;
    private AssemblyLineService assemblyLineService;

    public SaleService(SaleRepository saleRepository, TokenRepository tokenRepository,
            InvoiceRepository invoiceRepository, TokenAssembler tokenAssembler,
            TransactionIdAssembler transactionIdAssembler, SaleFactory saleFactory,
            TransactionIdFactory transactionIdFactory, CarFactory carFactory, BatteryFactory batteryFactory,
            InvoiceFactory invoiceFactory, EstimatedRangeAssembler estimatedRangeAssembler,
            AssemblyLineService assemblyLineService) {
        this.saleRepository = saleRepository;
        this.tokenRepository = tokenRepository;
        this.invoiceRepository = invoiceRepository;
        this.tokenAssembler = tokenAssembler;
        this.transactionIdAssembler = transactionIdAssembler;
        this.saleFactory = saleFactory;
        this.transactionIdFactory = transactionIdFactory;
        this.carFactory = carFactory;
        this.batteryFactory = batteryFactory;
        this.invoiceFactory = invoiceFactory;
        this.estimatedRangeAssembler = estimatedRangeAssembler;
        this.assemblyLineService = assemblyLineService;
    }

    public SaleCreatedDto initSale(TokenDto tokenDto) {
        // TODO salefactory a le token repo et le token assembler, juste a caller create de salefactory
        Token token = tokenAssembler.dtoToToken(tokenDto);
        String email = tokenRepository.getEmail(token);
        Sale sale = saleFactory.create(email);
        saleRepository.registerSale(sale);
        return transactionIdAssembler.transactionIdToDto(sale.getTransactionId(), sale.getDeliveryId());
    }

    public void chooseVehicle(int transactionIdInt, ChooseVehicleDto chooseVehicleDto) {
        TransactionId transactionId = this.transactionIdFactory.createFromInt(transactionIdInt);
        Sale sale = this.saleRepository.getSale(transactionId);
        Car car = this.carFactory.create(chooseVehicleDto.name, chooseVehicleDto.color);
        sale.chooseCar(car);
    }

    public EstimatedRangeDto chooseBattery(int transactionIdInt, ChooseBatteryDto chooseBatteryDto) {
        TransactionId transactionId = this.transactionIdFactory.createFromInt(transactionIdInt);
        Sale sale = this.saleRepository.getSale(transactionId);
        Battery battery = this.batteryFactory.create(chooseBatteryDto.type);
        sale.chooseBattery(battery);
        // TODO stocker la batterie dans un repository laisser l'object battery faire les calculs ( autonomie )
        Integer estimatedRange = sale.getBatteryAutonomy();
        return estimatedRangeAssembler.EstimatedRangeToDto(estimatedRange);
    }

    public void completeSale(int transactionIdInt, InvoiceDto invoiceDto) {
        TransactionId transactionId = this.transactionIdFactory.createFromInt(transactionIdInt);
        Sale sale = this.saleRepository.getSale(transactionId);
        sale.completeSale();

        Invoice invoice = this.invoiceFactory.create(Integer.parseInt(invoiceDto.bank_no),
                Integer.parseInt(invoiceDto.account_no), invoiceDto.frequency, sale.getPrice());
        this.invoiceRepository.addInvoice(sale.getTransactionId(), invoice);
        this.assemblyLineService.addSaleToAssemblyLines(sale);
    }
}

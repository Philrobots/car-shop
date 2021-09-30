package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.SaleCreatedDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.EstimatedRangeDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.TransactionIdDto;
import ca.ulaval.glo4003.evulution.domain.car.Car;
import ca.ulaval.glo4003.evulution.domain.car.CarFactory;
import ca.ulaval.glo4003.evulution.domain.car.Battery;
import ca.ulaval.glo4003.evulution.domain.car.BatteryFactory;
import ca.ulaval.glo4003.evulution.domain.sale.*;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

public class SaleService {
    private SaleFactory saleFactory;
    private SaleRepository saleRepository;
    private TokenRepository tokenRepository;
    private TokenAssembler tokenAssembler;
    private TransactionIdAssembler transactionIdAssembler;
    private TransactionIdFactory transactionIdFactory;
    private CarFactory carFactory;
    private BatteryFactory batteryFactory;
    private EstimatedRangeAssembler estimatedRangeAssembler;

    public SaleService(SaleFactory saleFactory, SaleRepository saleRepository, TokenRepository tokenRepository,
            TokenAssembler tokenAssembler, TransactionIdAssembler transactionIdAssembler,
            TransactionIdFactory transactionIdFactory, CarFactory carFactory, BatteryFactory batteryFactory,
            EstimatedRangeAssembler estimatedRangeAssembler) {
        this.saleFactory = saleFactory;
        this.saleRepository = saleRepository;
        this.tokenRepository = tokenRepository;
        this.tokenAssembler = tokenAssembler;
        this.transactionIdAssembler = transactionIdAssembler;
        this.transactionIdFactory = transactionIdFactory;
        this.carFactory = carFactory;
        this.batteryFactory = batteryFactory;
        this.estimatedRangeAssembler = estimatedRangeAssembler;
    }

    public SaleCreatedDto initSale(TokenDto tokenDto) {
        Token token = tokenAssembler.dtoToToken(tokenDto);
        String email = tokenRepository.getEmail(token);
        Sale sale = saleFactory.create(email);

        saleRepository.registerSale(sale);

        return transactionIdAssembler.transactionIdToDto(sale.getTransactionId(), sale.getDelivery().getDeliveryId());
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
        Integer estimatedRange = sale.getBatteryAutonomy();
        return estimatedRangeAssembler.EstimatedRangeToDto(estimatedRange);
    }

}

package ca.ulaval.glo4003.evulution.service.manufacture;

import ca.ulaval.glo4003.evulution.domain.car.exceptions.BadCarSpecsException;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureDomainService;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleValidator;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.CarNotChosenBeforeBatteryException;
import ca.ulaval.glo4003.evulution.domain.sale.exceptions.SaleAlreadyCompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.EstimatedRangeDto;

public class ManufactureService {
    private final SaleValidator saleValidator;
    private final ManufactureDomainService manufactureDomainService;
    private final EstimatedRangeAssembler estimatedRangeAssembler;

    public ManufactureService(SaleValidator saleValidator, ManufactureDomainService manufactureDomainService,
            EstimatedRangeAssembler estimatedRangeAssembler) {
        this.saleValidator = saleValidator;
        this.manufactureDomainService = manufactureDomainService;
        this.estimatedRangeAssembler = estimatedRangeAssembler;
    }

    public void chooseCar(int saleIdRequest, ChooseCarDto chooseCarDto) {
        try {
            SaleId saleId = this.saleValidator.validateNotCompleteStatus(saleIdRequest);
            this.manufactureDomainService.addCar(saleId, chooseCarDto.name, chooseCarDto.color);
        } catch (SaleNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (SaleAlreadyCompleteException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (BadCarSpecsException e) {
            throw new ServiceBadInputParameterException();
        }
    }

    public EstimatedRangeDto chooseBattery(int saleIdRequest, ChooseBatteryDto chooseBatteryDto) {
        try {
            SaleId saleId = this.saleValidator.validateNotCompleteStatus(saleIdRequest);
            int estimatedRange = this.manufactureDomainService.addBattery(saleId, chooseBatteryDto.type);
            return this.estimatedRangeAssembler.estimatedRangeToDto(estimatedRange);
        } catch (SaleNotFoundException e) {
            throw new ServiceBadRequestException();
        } catch (SaleAlreadyCompleteException | CarNotChosenBeforeBatteryException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (BadCarSpecsException e) {
            throw new ServiceBadInputParameterException();
        }
    }
}

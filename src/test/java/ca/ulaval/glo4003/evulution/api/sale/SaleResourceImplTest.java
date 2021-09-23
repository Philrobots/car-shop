package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryDto;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SaleResourceImplTest {

    private int A_TRANSACTION_ID = 2;
    @Mock
    private SaleService saleService;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private ChooseVehicleDto chooseVehicleDto;

    @Mock
    private ChooseBatteryDto chooseBatteryDto;

    @Mock
    private ContainerRequestContext containerRequestContext;

    private SaleResourceImpl saleResourceImpl;

    @BeforeEach
    private void setUp() {
        saleResourceImpl = new SaleResourceImpl(saleService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    @Test
    public void whenInitSale_thenSaleServiceIsCalled() {
        // given
        String emptyRequestContextAuthorization = null;
        BDDMockito.given(tokenDtoAssembler.assembleFromString(emptyRequestContextAuthorization)).willReturn(tokenDto);

        // when
        saleResourceImpl.initSale(containerRequestContext);

        // then
        Mockito.verify(saleService).initSale(tokenDto);
    }

    @Test
    public void whenChooseVehicle_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);

        // then
        Mockito.verify(saleService).chooseVehicle(A_TRANSACTION_ID, chooseVehicleDto);
    }

    @Test
    public void whenChooseBattery_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);

        // then
        Mockito.verify(saleService).chooseBattery(A_TRANSACTION_ID, chooseBatteryDto);
    }

}

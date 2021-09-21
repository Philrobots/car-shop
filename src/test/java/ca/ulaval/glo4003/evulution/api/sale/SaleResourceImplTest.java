package ca.ulaval.glo4003.evulution.api.sale;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseVehicleDto;
import ca.ulaval.glo4003.evulution.service.sale.SaleService;
import com.google.common.collect.Lists;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class SaleResourceImplTest {

    private int AN_INT = 2;
    @Mock
    private SaleService saleService;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private ChooseVehicleDto chooseVehicleDto;

    @Mock
    private ContainerRequestContext containerRequestContext;

    private SaleResourceImpl saleResourceImpl;

    @BeforeEach
    private void setUp() {
        saleResourceImpl = new SaleResourceImpl(saleService, tokenDtoAssembler);
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
    public void when_thenSaleServiceIsCalled() {
        // when
        saleResourceImpl.chooseVehicle(AN_INT, chooseVehicleDto);

        // then
        Mockito.verify(saleService).chooseVehicle(AN_INT, chooseVehicleDto);
    }

}

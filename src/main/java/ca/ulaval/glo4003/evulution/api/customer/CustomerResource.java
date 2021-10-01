package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
public interface CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCustomer(CustomerDto customerDto);
}

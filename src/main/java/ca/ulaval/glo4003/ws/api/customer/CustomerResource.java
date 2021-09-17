package ca.ulaval.glo4003.ws.api.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.List;

@Path("/customers")
public interface CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CustomerDto> getAll();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCustomer(CustomerDto customerDto) throws ParseException;
}

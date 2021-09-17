package ca.ulaval.glo4003.ws.api.login;

import ca.ulaval.glo4003.ws.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.ws.api.login.dto.LoginDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public interface LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response loginCustomer(LoginDto loginDto);
}

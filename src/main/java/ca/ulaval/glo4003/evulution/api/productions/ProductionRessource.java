package ca.ulaval.glo4003.evulution.api.productions;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.SecuredAdmin;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.exceptions.GenericException;
import ca.ulaval.glo4003.evulution.service.assemblyLine.AssemblyLineService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/productions")
public class ProductionRessource {

    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final AssemblyLineService assemblyLineService;

    public ProductionRessource(HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
            AssemblyLineService assemblyLineService) {
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.assemblyLineService = assemblyLineService;
    }

    @POST
    @SecuredAdmin
    @Path("/shutdown")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shutdownProductionLines() {
        try {
            this.assemblyLineService.shutdown();
            return Response.status(200, "Shutdown activated").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }

    @POST
    @SecuredAdmin
    @Path("/reactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reactivateProductionLines() {
        try {
            this.assemblyLineService.reactivate();
            return Response.status(200, "Activated").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}

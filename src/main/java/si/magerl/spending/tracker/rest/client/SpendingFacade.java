package si.magerl.spending.tracker.rest.client;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import si.magerl.spending.tracker.authentication.UserSecurityContext;
import si.magerl.spending.tracker.dao.ExpenseDao;
import si.magerl.spending.tracker.rest.dto.ExpenseDTO;
import si.magerl.spending.tracker.rest.dto.mappers.ExpenseMapper;
import si.magerl.spending.tracker.services.ExpenseProcessingService;

@Slf4j
@Path("/spending")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SpendingFacade {

    final ExpenseDao expenseDao;

    final ExpenseMapper expenseMapper;
    final ExpenseProcessingService expenseProcessingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExpenseDTO> getSpendingRecords(@Context SecurityContext securityContext) {
        return expenseDao.getUsersSpendingRecords(((UserSecurityContext) securityContext).getUserPrincipal()).stream()
                .map(expenseMapper::toDTO)
                .toList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveExpense(@Context SecurityContext securityContext, ExpenseDTO spendingRecord) {
        log.info("Received: {}", spendingRecord);
        expenseProcessingService.processExpense(
                ((UserSecurityContext) securityContext).getUserPrincipal(), spendingRecord);
    }

    @POST
    @Path("/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveExpenses(@Context SecurityContext securityContext, List<ExpenseDTO> spendingRecords) {
        log.info("Received: {} records", spendingRecords.size());
        expenseProcessingService.processExpenses(
                ((UserSecurityContext) securityContext).getUserPrincipal(), spendingRecords);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSpendingRecord(@Context SecurityContext securityContext, @PathParam("id") String id) {
        expenseDao.deleteUsersExpense(((UserSecurityContext) securityContext).getUserPrincipal(), id);
    }
}

package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.controller.util.SwaggerTexts;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * All HTTP endpoints related to user
 */
@RestController
@RequestMapping("${route.user.base}")
public class UsersController {

    private final UserService userService;
    private final ControllerUtil controllerUtil;

    public UsersController(UserService userService, ControllerUtil controllerUtil) {
        this.userService = userService;
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostAuthorize("@securityService.canAccessUserData(principal, returnObject)")
    @ApiOperation(value = "get user info", notes = SwaggerTexts.GET_USER_DATA)
    public User user(
            @ApiParam(value = SwaggerTexts.SENSITIVE_DATA) @RequestParam(required = false, name = "name") String queryName,
            HttpServletRequest request) throws NoObjectForIdException {
        String name = queryName != null ? queryName : controllerUtil.getUsername(request);
        return userService.getUserByName(name);
    }

    @RequestMapping(value = "${route.user.all}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public List<String> getAllUsernames() {
        return userService.getAllUsernames();
    }

    @RequestMapping(value = "${route.user.division}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.canGetDivisionUsers(principal, #queryDivision)")
    @ApiOperation(value = "get all users for division", notes = SwaggerTexts.GET_DIVISION_USERS)
    public Iterable<User> getAllUsersForDivision(
            @ApiParam(value = SwaggerTexts.SENSITIVE_DATA) @RequestParam(name = "division", required = false) String queryDivision,
            HttpServletRequest request) throws NoObjectForIdException {
        String division = queryDivision;
        if (division == null || division.length() < 1) {
            division = userService.getUserByName(controllerUtil.getUsername(request)).getDivision();
        }
        return userService.getUsersForDivision(division);
    }

    /**
     * Custom response if no User for the given name exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No user with name=" + ex.getId()),
                HttpStatus.NOT_FOUND);
    }
}

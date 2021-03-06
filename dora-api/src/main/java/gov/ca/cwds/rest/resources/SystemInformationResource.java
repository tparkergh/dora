package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.DoraConstants.SYSTEM_INFORMATION;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.dto.app.SystemInformationDto;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.resources.system.AbstractSystemInformationResource;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * A resource providing a basic information about the CWDS API.
 *
 * @author CWDS API Team
 */
@Api(value = SYSTEM_INFORMATION)
@Path(SYSTEM_INFORMATION)
@Produces(MediaType.APPLICATION_JSON)
public class SystemInformationResource extends AbstractSystemInformationResource {

  private static final String VERSION_PROPERTIES_FILE = "system-information.properties";
  private static final String BUILD_NUMBER = "build.number";

  /**
   * Constructor
   *
   * @param applicationName The name of the application
   * @param version The version of the API
   */
  @Inject
  public SystemInformationResource(@Named("app.name") String applicationName,
      @Named("app.version") String version, Environment environment) {
    super(environment.healthChecks());
    super.applicationName = applicationName;
    super.version = version;
    Properties versionProperties = getVersionProperties();
    super.buildNumber = versionProperties.getProperty(BUILD_NUMBER);
    super.systemHealthStatusStrategy = new DoraSystemHealthStatusStrategy();
  }

  private Properties getVersionProperties() {
    Properties versionProperties = new Properties();
    try {
      InputStream is = ClassLoader.getSystemResourceAsStream(VERSION_PROPERTIES_FILE);
      versionProperties.load(is);
    } catch (IOException e) {
      throw new ApiException("Can't read version.properties", e);
    }
    return versionProperties;
  }

  /**
   * Get the name of the application.
   *
   * @return the application data
   */
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 465, message = "CARES Service is not healthy")})
  @ApiOperation(value = "Returns System Information", response = SystemInformationDto.class)
  public Response get() {
    return super.buildResponse();
  }

}

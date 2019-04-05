package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import javax.ws.rs.HttpMethod;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.annotations.ApiModel;

/**
 * A domain API Request for Index Query feature to Elasticsearch.
 * <p>
 * The Index Query for an Index takes an index name, document type, and JSON as a String, which is
 * used to requestBody the Elasticsearch Index documents by ALL relevant fields that are specified
 * in the requestBody.
 * </p>
 *
 * @author CWDS API Team
 */
@ApiModel
public final class IndexQueryRequest implements Serializable {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 3L;

  private IndexQueryRequest() {}

  /**
   * Http method for elasticsearch call
   */
  private String httpMethod;
  private String requestBody;
  private String documentType;
  private String esEndpoint;
  private boolean customSort;

  public String getRequestBody() {
    return requestBody;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public String getDocumentType() {
    return documentType;
  }

  public String getEsEndpoint() {
    return esEndpoint;
  }

  public boolean isCustomSort() {
    return customSort;
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public static class IndexQueryRequestBuilder {

    private String requestBody;
    private String httpMethod;
    private String documentType;
    private String esEndpoint;
    private boolean customSort;


    public IndexQueryRequestBuilder addEsEndpoint(String endpoint) {
      this.esEndpoint = endpoint;
      return this;
    }

    public IndexQueryRequestBuilder addDocumentType(String documentType) {
      this.documentType = documentType;
      return this;
    }

    public IndexQueryRequestBuilder addRequestBody(String requestBody) {
      this.requestBody = requestBody;
      return this;
    }

    public IndexQueryRequestBuilder addHttpMethod(String httpMethod) {
      this.httpMethod = httpMethod;
      return this;
    }

    public IndexQueryRequestBuilder addCustomSort(boolean customSort) {
      this.customSort = customSort;
      return this;
    }

    public IndexQueryRequest build() {
      final IndexQueryRequest request = new IndexQueryRequest();
      request.esEndpoint = esEndpoint;
      request.documentType = documentType;
      request.requestBody = requestBody;
      request.customSort = customSort;

      if (StringUtils.isNotBlank(httpMethod)) {
        request.httpMethod = httpMethod;
      } else {
        request.httpMethod = HttpMethod.POST;
      }

      return request;
    }

  }

}

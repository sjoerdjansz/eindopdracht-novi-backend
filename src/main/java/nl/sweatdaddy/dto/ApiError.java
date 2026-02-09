package nl.sweatdaddy.dto;

import java.util.Map;

// Eventueel voor later icm met custom exception handlers
public class ApiError {

  private final String message;
  private final Map<String, String> errorFields;

  public ApiError(String message, Map<String, String> errorFields) {
    this.message = message;
    this.errorFields = errorFields;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getErrorFields() {
    return errorFields;
  }
}

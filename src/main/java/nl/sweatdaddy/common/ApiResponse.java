package nl.sweatdaddy.common;

/* Dit is geen domeinspecifieke dto zoals de exerciseDto, maar een transportDto
* Hij beschrijft hoe de API responses eruit moeten zien en niet wat een exercise is.
* Ik heb deze class gemaakt om consistente API responses af te dwingen.
* Door generics te gebruiken is die goed herbruikbaar
* Let op: responseEntity gaat over HTTP, ApiResponse gaat over de inhoud (feitelijk de payload).
*
* */
public class ApiResponse<T> {
  private final T data;
  private final String message;

  public ApiResponse(T data, String message) {
    this.data = data;
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }
}

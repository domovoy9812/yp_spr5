package ru.yandex.practicum.bliushtein.spr5.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;

/**
 * GetBalance5XXResponse
 */

@JsonTypeName("getBalance_5XX_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T20:02:59.907656600+05:00[Asia/Qyzylorda]", comments = "Generator version: 7.12.0")
public class GetBalance5XXResponse {

  @NotNull
  private String error;

  public GetBalance5XXResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GetBalance5XXResponse(String error) {
    this.error = error;
  }

  public GetBalance5XXResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
   */
  @NotNull 
  @Schema(name = "error", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetBalance5XXResponse getBalance5XXResponse = (GetBalance5XXResponse) o;
    return Objects.equals(this.error, getBalance5XXResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetBalance5XXResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


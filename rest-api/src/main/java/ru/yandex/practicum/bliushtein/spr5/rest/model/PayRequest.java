package ru.yandex.practicum.bliushtein.spr5.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;

/**
 * PayRequest
 */

@JsonTypeName("pay_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T20:02:59.907656600+05:00[Asia/Qyzylorda]", comments = "Generator version: 7.12.0")
public class PayRequest {

  private @NotNull Integer price;

  public PayRequest price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * minimum: 0
   * @return price
   */
  @Min(0) 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PayRequest payRequest = (PayRequest) o;
    return Objects.equals(this.price, payRequest.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PayRequest {\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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


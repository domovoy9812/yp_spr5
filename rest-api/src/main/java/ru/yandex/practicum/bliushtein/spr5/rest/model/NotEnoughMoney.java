package ru.yandex.practicum.bliushtein.spr5.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;

/**
 * NotEnoughMoney
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T20:02:59.907656600+05:00[Asia/Qyzylorda]", comments = "Generator version: 7.12.0")
public class NotEnoughMoney {

  private @NotNull Integer actualBalance;

  private @NotNull Integer requiredBalance;

  public NotEnoughMoney actualBalance(Integer actualBalance) {
    this.actualBalance = actualBalance;
    return this;
  }

  /**
   * текущий баланс
   * minimum: 0
   * @return actualBalance
   */
  @Min(0) 
  @Schema(name = "actualBalance", description = "текущий баланс", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("actualBalance")
  public Integer getActualBalance() {
    return actualBalance;
  }

  public void setActualBalance(Integer actualBalance) {
    this.actualBalance = actualBalance;
  }

  public NotEnoughMoney requiredBalance(Integer requiredBalance) {
    this.requiredBalance = requiredBalance;
    return this;
  }

  /**
   * минимально необходимый баланс для совершения платежа
   * minimum: 0
   * @return requiredBalance
   */
  @Min(0) 
  @Schema(name = "requiredBalance", description = "минимально необходимый баланс для совершения платежа", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requiredBalance")
  public Integer getRequiredBalance() {
    return requiredBalance;
  }

  public void setRequiredBalance(Integer requiredBalance) {
    this.requiredBalance = requiredBalance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotEnoughMoney notEnoughMoney = (NotEnoughMoney) o;
    return Objects.equals(this.actualBalance, notEnoughMoney.actualBalance) &&
        Objects.equals(this.requiredBalance, notEnoughMoney.requiredBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actualBalance, requiredBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotEnoughMoney {\n");
    sb.append("    actualBalance: ").append(toIndentedString(actualBalance)).append("\n");
    sb.append("    requiredBalance: ").append(toIndentedString(requiredBalance)).append("\n");
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


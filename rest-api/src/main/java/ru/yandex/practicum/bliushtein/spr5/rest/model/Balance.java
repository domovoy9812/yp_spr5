package ru.yandex.practicum.bliushtein.spr5.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;

/**
 * Balance
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T20:02:59.907656600+05:00[Asia/Qyzylorda]", comments = "Generator version: 7.12.0")
public class Balance {

  @NotNull
  private Integer balance;

  public Balance() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Balance(Integer balance) {
    this.balance = balance;
  }

  public Balance balance(Integer balance) {
    this.balance = balance;
    return this;
  }

  /**
   * текущий баланс
   * minimum: 0
   * @return balance
   */
  @NotNull @Min(0) 
  @Schema(name = "balance", description = "текущий баланс", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("balance")
  public Integer getBalance() {
    return balance;
  }

  public void setBalance(Integer balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Balance balance = (Balance) o;
    return Objects.equals(this.balance, balance.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Balance {\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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


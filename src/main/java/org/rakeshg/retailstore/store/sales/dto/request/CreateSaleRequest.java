package org.rakeshg.retailstore.store.sales.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.rakeshg.retailstore.store.sales.command.CreateSaleCommand;

import java.util.List;

@Data
public class CreateSaleRequest {
    @NotEmpty private List<SaleItemRequest> items;

    public CreateSaleCommand toCommand() {
        return CreateSaleCommand.builder()
                .items(
                        items.stream()
                                .map(SaleItemRequest::toCommand)
                                .toList()
                )
                .build();
    }
}

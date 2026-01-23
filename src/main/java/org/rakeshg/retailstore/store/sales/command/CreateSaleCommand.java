package org.rakeshg.retailstore.store.sales.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateSaleCommand {
    private List<CreateSaleItemCommand> items;
}

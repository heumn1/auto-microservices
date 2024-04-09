--changeset heumn:1


ALTER TABLE client_order
    DROP CONSTRAINT oneOrderToClient;

ALTER TABLE client_order
   ADD CONSTRAINT oneOrderToClient UNIQUE (id_order)
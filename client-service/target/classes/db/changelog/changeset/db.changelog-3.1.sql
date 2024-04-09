--changeset heumn:1


ALTER TABLE client_order
ADD CONSTRAINT oneOrderToClient UNIQUE (id_client)
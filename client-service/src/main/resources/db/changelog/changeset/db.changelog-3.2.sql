--changeset heumn:1


ALTER TABLE client
    ADD CONSTRAINT clientNumber UNIQUE (number_client)
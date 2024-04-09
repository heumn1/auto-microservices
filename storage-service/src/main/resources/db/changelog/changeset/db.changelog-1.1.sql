--changeset heumn:2

ALTER TABLE supplier_parts
    ALTER price_part TYPE double precision USING price_part::numeric::double precision,
    ALTER price_part DROP DEFAULT,
    ALTER price_part SET NOT NULL;


ALTER TABLE parts
    ALTER price_part TYPE double precision USING price_part::numeric::double precision,
    ALTER price_part DROP DEFAULT,
    ALTER price_part SET NOT NULL;
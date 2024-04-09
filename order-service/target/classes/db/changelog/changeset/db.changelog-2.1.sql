--changeset heumn:2

ALTER TABLE public.ordering
    ALTER estimate_cost_order TYPE double precision USING estimate_cost_order::numeric::double precision,
    ALTER estimate_cost_order DROP DEFAULT,
    ALTER estimate_cost_order SET NOT NULL;



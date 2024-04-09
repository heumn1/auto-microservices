--changeset heumn:1


CREATE TABLE IF NOT EXISTS public.ordering
(
    id_order bigserial primary key,
    type_order varchar(255) NOT NULL,
    brand_car_order varchar(255) NOT NULL,
    number_car_order varchar(255) NOT NULL,
    number_client_order varchar(255) NOT NULL,
    status_order varchar(255) NOT NULL,
    estimate_cost_order money NOT NULL
);

CREATE TABLE IF NOT EXISTS public.order_part
(
    id_order_part bigserial primary key,
    id_order bigserial references public.ordering,
    id_part bigserial references public.parts
);
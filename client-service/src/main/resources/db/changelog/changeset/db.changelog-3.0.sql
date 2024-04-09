--changeset heumn:1


CREATE TABLE IF NOT EXISTS public.client
(
    id_client bigserial primary key,
    name_client varchar(100) not null,
    lastname_client varchar(100) not null,
    patronymic_client varchar(100),
    number_client varchar(30) not null,
    email_client varchar(255)
);

CREATE TABLE IF NOT EXISTS public.client_order
(
    id_client bigserial references public.client,
    id_order bigserial references public.ordering
);
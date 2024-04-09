--changeset heumn:1

CREATE TABLE IF NOT EXISTS public.parts
(
    id_part bigserial primary key,
    name_part varchar(255) NOT NULL,
    description_part varchar(255),
    category_part varchar(255) NOT NULL,
    count_part integer NOT NULL,
    price_part money not null
);

CREATE TABLE IF NOT EXISTS public.supplier
(
    id_supplier bigserial primary key,
    name_supplier varchar(255) NOT NULL,
    email_supplier varchar(255),
    phone_supplier varchar(255)
);

CREATE TABLE IF NOT EXISTS public.supplier_parts
(
    id_sp bigserial primary key,
    id_supplier bigserial references public.supplier,
    id_part bigserial references public.parts,
    price_part money not null
);
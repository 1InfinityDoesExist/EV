-- Table: public.user_registration_page

-- DROP TABLE public.user_registration_page;

CREATE TABLE public.user_registration_page
(
    id bigint NOT NULL,
    creation_date timestamp without time zone,
    last_modification_date timestamp without time zone,
    address jsonb,
    dob timestamp without time zone,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    image character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    mobile character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    referral_code character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    delete_flag boolean,
    CONSTRAINT user_registration_page_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_registration_page
    OWNER to postgres;
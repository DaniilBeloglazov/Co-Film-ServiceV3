SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.film (
                             id bigint NOT NULL,
                             age_rating bigint NOT NULL,
                             audience bigint NOT NULL,
                             avatar_uri character varying(255) NOT NULL,
                             box_office bigint NOT NULL,
                             budget bigint NOT NULL,
                             description text NOT NULL,
                             production_year bigint NOT NULL,
                             title character varying(255) NOT NULL
);

ALTER TABLE public.film OWNER TO whiteyesx;

CREATE TABLE public.film_actors (
                                    actored_films_id bigint NOT NULL,
                                    actors_id bigint NOT NULL
);

ALTER TABLE public.film_actors OWNER TO whiteyesx;

CREATE TABLE public.film_directors (
                                       directed_films_id bigint NOT NULL,
                                       directors_id bigint NOT NULL
);

ALTER TABLE public.film_directors OWNER TO whiteyesx;

CREATE TABLE public.film_genres (
                                    films_id bigint NOT NULL,
                                    genres_id bigint NOT NULL
);

ALTER TABLE public.film_genres OWNER TO whiteyesx;

CREATE SEQUENCE public.film_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.film_id_seq OWNER TO whiteyesx;

ALTER SEQUENCE public.film_id_seq OWNED BY public.film.id;

CREATE TABLE public.film_writers (
                                     written_films_id bigint NOT NULL,
                                     writers_id bigint NOT NULL
);

ALTER TABLE public.film_writers OWNER TO whiteyesx;

CREATE TABLE public.genre (
                              id bigint NOT NULL,
                              name character varying(255)
);

ALTER TABLE public.genre OWNER TO whiteyesx;

CREATE SEQUENCE public.genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.genre_id_seq OWNER TO whiteyesx;

ALTER SEQUENCE public.genre_id_seq OWNED BY public.genre.id;

CREATE TABLE public.person (
                               id bigint NOT NULL,
                               avatar_uri character varying(255) NOT NULL,
                               date_of_birth date NOT NULL,
                               height double precision NOT NULL,
                               last_name character varying(255) NOT NULL,
                               name character varying(255) NOT NULL
);


ALTER TABLE public.person OWNER TO whiteyesx;

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.person_id_seq OWNER TO whiteyesx;

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;

CREATE TABLE public.photos (
                               id bigint NOT NULL,
                               path character varying(255),
                               owner_id bigint
);

ALTER TABLE public.photos OWNER TO whiteyesx;

CREATE SEQUENCE public.photos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.photos_id_seq OWNER TO whiteyesx;

ALTER SEQUENCE public.photos_id_seq OWNED BY public.photos.id;

ALTER TABLE ONLY public.film ALTER COLUMN id SET DEFAULT nextval('public.film_id_seq'::regclass);

ALTER TABLE ONLY public.genre ALTER COLUMN id SET DEFAULT nextval('public.genre_id_seq'::regclass);

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);

ALTER TABLE ONLY public.photos ALTER COLUMN id SET DEFAULT nextval('public.photos_id_seq'::regclass);

COPY public.film (id, age_rating, audience, avatar_uri, box_office, budget, description, production_year, title) FROM stdin;
\.

COPY public.film_actors (actored_films_id, actors_id) FROM stdin;
\.

COPY public.film_directors (directed_films_id, directors_id) FROM stdin;
\.

COPY public.film_genres (films_id, genres_id) FROM stdin;
\.

COPY public.film_writers (written_films_id, writers_id) FROM stdin;
\.

COPY public.genre (id, name) FROM stdin;
\.

COPY public.person (id, avatar_uri, date_of_birth, height, last_name, name) FROM stdin;
\.

COPY public.photos (id, path, owner_id) FROM stdin;
\.

SELECT pg_catalog.setval('public.film_id_seq', 1, false);

SELECT pg_catalog.setval('public.genre_id_seq', 1, false);

SELECT pg_catalog.setval('public.person_id_seq', 1, false);

SELECT pg_catalog.setval('public.photos_id_seq', 1, false);

ALTER TABLE ONLY public.film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.photos
    ADD CONSTRAINT photos_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.photos
    ADD CONSTRAINT fk24iwwci9atj7mttpwoseivj7v FOREIGN KEY (owner_id) REFERENCES public.person(id);

ALTER TABLE ONLY public.film_genres
    ADD CONSTRAINT fk4f3fk6o6y7tutfkgwfemxuumy FOREIGN KEY (films_id) REFERENCES public.film(id);

ALTER TABLE ONLY public.film_directors
    ADD CONSTRAINT fk6efj5u44a01urgkr1ibcs31hd FOREIGN KEY (directors_id) REFERENCES public.person(id);

ALTER TABLE ONLY public.film_genres
    ADD CONSTRAINT fkhjprjv3b8d1u1wkolsw7y5204 FOREIGN KEY (genres_id) REFERENCES public.genre(id);

ALTER TABLE ONLY public.film_writers
    ADD CONSTRAINT fkhsmh7ivm2jmxjpd98i3vowl1s FOREIGN KEY (writers_id) REFERENCES public.person(id);

ALTER TABLE ONLY public.film_actors
    ADD CONSTRAINT fknfn5m1im90iwaorjlue8am98d FOREIGN KEY (actored_films_id) REFERENCES public.film(id);

ALTER TABLE ONLY public.film_actors
    ADD CONSTRAINT fko81t4fprfggupcaj8ks7n077g FOREIGN KEY (actors_id) REFERENCES public.person(id);
ALTER TABLE ONLY public.film_directors
    ADD CONSTRAINT fkq9h1h4cqssf8hg7rvw53htikp FOREIGN KEY (directed_films_id) REFERENCES public.film(id);

ALTER TABLE ONLY public.film_writers
    ADD CONSTRAINT fkqd39i2gko67ia0odgeuaedhln FOREIGN KEY (written_films_id) REFERENCES public.film(id);

INSERT INTO public.genre (name)
VALUES ('Western'),
       ('Gangster Movie'),
       ('Detective'),
       ('Drama'),
       ('Historical Film'),
       ('Comedy'),
       ('Melodrama'),
       ('Musical Film'),
       ('Noir'),
       ('Political Film'),
       ('Adventure Movie'),
       ('Fairy'),
       ('Tragedy'),
       ('Tragicomedy');
CREATE TABLE public.person_books
(
    person_id bigint NOT NULL,
    livro_id integer NOT NULL,
    PRIMARY KEY (person_id, livro_id),
	FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
    FOREIGN KEY (livro_id) REFERENCES livro(id) ON DELETE CASCADE
);

ALTER TABLE IF EXISTS public.person_books
    OWNER to postgres;
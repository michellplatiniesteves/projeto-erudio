INSERT INTO person_books (person_id, livro_id)
SELECT 
    p.id AS person_id, 
    b.id AS livro_id
FROM 
    (SELECT id FROM person WHERE id <= 12) p
CROSS JOIN
    (SELECT id FROM livro ORDER BY random() LIMIT 20) b;

-- Associar 3 livros às demais pessoas
INSERT INTO person_books (person_id, livro_id)
SELECT
    p.id AS person_id,
    b.id AS livro_id
FROM
    (SELECT id FROM person WHERE id > 12) p
CROSS JOIN
    (SELECT id FROM livro ORDER BY random() LIMIT 3) b;
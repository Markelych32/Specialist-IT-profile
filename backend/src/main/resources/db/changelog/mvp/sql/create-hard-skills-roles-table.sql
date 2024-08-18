CREATE TABLE hard_skills_roles
(
    skill_id INT REFERENCES hard_skills (id),
    role_id  INT REFERENCES roles (id),
    CONSTRAINT hard_skills_roles_pkey PRIMARY KEY (skill_id, role_id)
);
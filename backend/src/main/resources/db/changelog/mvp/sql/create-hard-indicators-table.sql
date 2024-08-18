CREATE TABLE hard_indicators
(
    id             INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    indicator_name VARCHAR UNIQUE NOT NULL,
    skill_id INT REFERENCES hard_skills(id) NOT NULL
);
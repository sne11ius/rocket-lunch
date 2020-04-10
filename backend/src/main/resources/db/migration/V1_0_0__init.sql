CREATE TABLE location
(
    id    UUID PRIMARY KEY,
    version int NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITH TIME ZONE NOT NULL,
    title text NOT NULL
);

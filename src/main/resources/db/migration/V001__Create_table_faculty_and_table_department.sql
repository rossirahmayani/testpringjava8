-- Sequence for general purposes like invoice number, etc.
CREATE SEQUENCE general_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE faculty_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE department_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE faculty(
  id bigint NOT NULL DEFAULT nextval('faculty_id_seq'),
  faculty_code character varying(5) NOT NULL,
  faculty_name character varying(64) NOT NULL,
  create_date timestamp without time zone NOT NULL,
  update_date timestamp without time zone,
  status character varying(2) NOT NULL,
  CONSTRAINT faculty_id_pk PRIMARY KEY (id)
);

CREATE TABLE department(
  id bigint NOT NULL DEFAULT nextval('department_id_seq'),
  department_code character varying(10) NOT NULL,
  department_name character varying(64) NOT NULL,
  faculty_id bigint NOT NULL,
  create_date timestamp without time zone NOT NULL,
  update_date timestamp without time zone,
  status character varying(2) NOT NULL,
  CONSTRAINT department_id_pk PRIMARY KEY (id),
  CONSTRAINT faculty_id_fk FOREIGN KEY (faculty_id)
    REFERENCES faculty(id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
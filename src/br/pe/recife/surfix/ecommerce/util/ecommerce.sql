--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ADQUIRENTE; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "ADQUIRENTE" (
    "ID" integer NOT NULL,
    "NOME" character varying(50) NOT NULL,
    "DESCRICAO" character varying(200)
);


ALTER TABLE public."ADQUIRENTE" OWNER TO postgres;

--
-- Name: ADQUIRENTE_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "ADQUIRENTE_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ADQUIRENTE_ID_seq" OWNER TO postgres;

--
-- Name: ADQUIRENTE_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "ADQUIRENTE_ID_seq" OWNED BY "ADQUIRENTE"."ID";


--
-- Name: EMPRESA; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "EMPRESA" (
    "ID" integer NOT NULL,
    "CNPJ" character varying(14) NOT NULL,
    "NOME" character varying(100) NOT NULL,
    "USUARIO" character varying(20) NOT NULL,
    "SENHA" character varying(20) NOT NULL
);


ALTER TABLE public."EMPRESA" OWNER TO postgres;

--
-- Name: EMPRESA_ADQUIRENTE; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "EMPRESA_ADQUIRENTE" (
    "ID" integer NOT NULL,
    "ID_EMPRESA" integer NOT NULL,
    "ID_ADQUIRENTE" integer NOT NULL,
    "MEC_ID" character varying(50) NOT NULL,
    "MEC_KEY" character varying(50) NOT NULL,
    "MEC_ID_TESTE" character varying(50) NOT NULL,
    "MEC_KEY_TESTE" character varying(50) NOT NULL
);


ALTER TABLE public."EMPRESA_ADQUIRENTE" OWNER TO postgres;

--
-- Name: EMPRESA_ADQUIRENTE_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "EMPRESA_ADQUIRENTE_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."EMPRESA_ADQUIRENTE_ID_seq" OWNER TO postgres;

--
-- Name: EMPRESA_ADQUIRENTE_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "EMPRESA_ADQUIRENTE_ID_seq" OWNED BY "EMPRESA_ADQUIRENTE"."ID";


--
-- Name: EMPRESA_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "EMPRESA_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."EMPRESA_ID_seq" OWNER TO postgres;

--
-- Name: EMPRESA_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "EMPRESA_ID_seq" OWNED BY "EMPRESA"."ID";


--
-- Name: TRANSACAO; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "TRANSACAO" (
    "ID" integer NOT NULL,
    "ID_EMPRESA_ADQUIRENTE" integer NOT NULL,
    "JSON_IN" character varying(1000) NOT NULL,
    "JSON_OUT" character varying(1000) NOT NULL
);


ALTER TABLE public."TRANSACAO" OWNER TO postgres;

--
-- Name: TRANSACAO_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "TRANSACAO_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."TRANSACAO_ID_seq" OWNER TO postgres;

--
-- Name: TRANSACAO_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "TRANSACAO_ID_seq" OWNED BY "TRANSACAO"."ID";


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "ADQUIRENTE" ALTER COLUMN "ID" SET DEFAULT nextval('"ADQUIRENTE_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "EMPRESA" ALTER COLUMN "ID" SET DEFAULT nextval('"EMPRESA_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "EMPRESA_ADQUIRENTE" ALTER COLUMN "ID" SET DEFAULT nextval('"EMPRESA_ADQUIRENTE_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "TRANSACAO" ALTER COLUMN "ID" SET DEFAULT nextval('"TRANSACAO_ID_seq"'::regclass);


--
-- Data for Name: ADQUIRENTE; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "ADQUIRENTE" ("ID", "NOME", "DESCRICAO") FROM stdin;
1	Cielo	Cielo Ecommerce
\.


--
-- Name: ADQUIRENTE_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"ADQUIRENTE_ID_seq"', 1, true);


--
-- Data for Name: EMPRESA; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "EMPRESA" ("ID", "CNPJ", "NOME", "USUARIO", "SENHA") FROM stdin;
1	11111111111112	Monier	usuario	senha
\.


--
-- Data for Name: EMPRESA_ADQUIRENTE; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "EMPRESA_ADQUIRENTE" ("ID", "ID_EMPRESA", "ID_ADQUIRENTE", "MEC_ID", "MEC_KEY", "MEC_ID_TESTE", "MEC_KEY_TESTE") FROM stdin;
1	1	1	7e326012-288a-4acb-a961-c71e545b32bc	UQR7HP9VrTGwoo0dFx8SRCKaUvfLi9gb47xMtnro	5da83acc-6fd6-48ec-b22a-f7e9b5de8bef	ILWIORZCPKQUYZCYNSSJGXPDUAOPCODLGGAOFDGH
\.


--
-- Name: EMPRESA_ADQUIRENTE_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"EMPRESA_ADQUIRENTE_ID_seq"', 1, true);


--
-- Name: EMPRESA_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"EMPRESA_ID_seq"', 1, true);


--
-- Data for Name: TRANSACAO; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "TRANSACAO" ("ID", "ID_EMPRESA_ADQUIRENTE", "JSON_IN", "JSON_OUT") FROM stdin;
\.


--
-- Name: TRANSACAO_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"TRANSACAO_ID_seq"', 1, false);


--
-- Name: ADQUIRENTE_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "ADQUIRENTE"
    ADD CONSTRAINT "ADQUIRENTE_pkey" PRIMARY KEY ("ID");


--
-- Name: EMPRESA_ADQUIRENTE_ID_EMPRESA_ID_ADQUIRENTE_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "EMPRESA_ADQUIRENTE"
    ADD CONSTRAINT "EMPRESA_ADQUIRENTE_ID_EMPRESA_ID_ADQUIRENTE_key" UNIQUE ("ID_EMPRESA", "ID_ADQUIRENTE");


--
-- Name: EMPRESA_ADQUIRENTE_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "EMPRESA_ADQUIRENTE"
    ADD CONSTRAINT "EMPRESA_ADQUIRENTE_pkey" PRIMARY KEY ("ID");


--
-- Name: EMPRESA_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "EMPRESA"
    ADD CONSTRAINT "EMPRESA_pkey" PRIMARY KEY ("ID");


--
-- Name: TRANSACAO_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "TRANSACAO"
    ADD CONSTRAINT "TRANSACAO_pkey" PRIMARY KEY ("ID");


--
-- Name: fki_EMPRESA_ADQUIRENTE_ID_ADQUIRENTE_fkey -> ADQUIRENTE; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_EMPRESA_ADQUIRENTE_ID_ADQUIRENTE_fkey -> ADQUIRENTE" ON "EMPRESA_ADQUIRENTE" USING btree ("ID_ADQUIRENTE");


--
-- Name: fki_EMPRESA_ADQUIRENTE_ID_EMPRESA_fkey -> EMPRESA; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_EMPRESA_ADQUIRENTE_ID_EMPRESA_fkey -> EMPRESA" ON "EMPRESA_ADQUIRENTE" USING btree ("ID_EMPRESA");


--
-- Name: fki_TRANSACAO_ID_EMPRESA_ADQUIRENTE_fkey -> EMPRESA_ADQUIRENTE; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_TRANSACAO_ID_EMPRESA_ADQUIRENTE_fkey -> EMPRESA_ADQUIRENTE" ON "TRANSACAO" USING btree ("ID_EMPRESA_ADQUIRENTE");


--
-- Name: EMPRESA_ADQUIRENTE_ID_ADQUIRENTE_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "EMPRESA_ADQUIRENTE"
    ADD CONSTRAINT "EMPRESA_ADQUIRENTE_ID_ADQUIRENTE_fkey" FOREIGN KEY ("ID_ADQUIRENTE") REFERENCES "ADQUIRENTE"("ID");


--
-- Name: EMPRESA_ADQUIRENTE_ID_EMPRESA_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "EMPRESA_ADQUIRENTE"
    ADD CONSTRAINT "EMPRESA_ADQUIRENTE_ID_EMPRESA_fkey" FOREIGN KEY ("ID_EMPRESA") REFERENCES "EMPRESA"("ID");


--
-- Name: TRANSACAO_ID_EMPRESA_ADQUIRENTE_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "TRANSACAO"
    ADD CONSTRAINT "TRANSACAO_ID_EMPRESA_ADQUIRENTE_fkey" FOREIGN KEY ("ID_EMPRESA_ADQUIRENTE") REFERENCES "EMPRESA_ADQUIRENTE"("ID");


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


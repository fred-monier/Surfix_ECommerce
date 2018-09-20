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
    "CPF_CNPJ" character varying(14) NOT NULL,
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
    "JSON_IN" character varying(1000),
    "JSON_OUT" character varying(5000),
    "OPERACAO" character varying(100) NOT NULL,
    "DATA_HORA" timestamp without time zone NOT NULL,
    "PROVIDER" character varying(20),
    "AMOUNT" integer,
    "CREDITCARD_BRAND" character varying(20),
    "CREDITCARD_NUMBER" character varying(20),
    "STATUS" character varying(10),
    "PAYMENT_ID" character varying(50),
    "PAYMENT_AUTHCODE" character varying(20),
    "PAYMENT_PROOF_OF_SALE" character varying(20),
    "PAYMENT_TID" character varying(20),
    "PAYMENT_RECEIVED_DATE" character varying(20),
    "PAYMENT_RETURN_CODE" character varying(20),
    "PAYMENT_RETURN_MESSAGE" character varying(100),
    "REC_PAYMENT_ID" character varying(50),
    "REC_PAYMENT_AUTH_NOW" boolean,
    "REC_PAYMENT_START_DATE" character varying(10),
    "REC_PAYMENT_END_DATE" character varying(10),
    "REC_PAYMENT_NEXT_RECURRENCY" character varying(10),
    "REC_PAYMENT_REASON_CODE" character varying(20),
    "REC_PAYMENT_REASON_MESSAGE" character varying(100),
    "NUM_PEDIDO_VIRTUAL" character varying(20),
    "PAYMENT_CANCELADO" boolean,
    "REC_PAYMENT_DESABILITADO" boolean,
    "PAYMENT_REASON_CODE" character varying(20),
    "PAYMENT_REASON_MESSAGE" character varying(100),
    "PAYMENT_PROVIDER_RETURN_CODE" character varying(20),
    "PAYMENT_PROVIDER_RETURN_MESSAGE" character varying(100),
    "ID_PAI" integer,
    "REC_PAYMENT_MONTHS_INTERVAL" character varying(20),
    "REC_PAYMENT_RECURRENCY_DAY" character varying(2)
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

COPY "EMPRESA" ("ID", "CPF_CNPJ", "NOME", "USUARIO", "SENHA") FROM stdin;
1	11111111111112	Monier	usuario1	senha1
2	22222222222222	Hipotetica	usuario2	senha2
\.


--
-- Data for Name: EMPRESA_ADQUIRENTE; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "EMPRESA_ADQUIRENTE" ("ID", "ID_EMPRESA", "ID_ADQUIRENTE", "MEC_ID", "MEC_KEY", "MEC_ID_TESTE", "MEC_KEY_TESTE") FROM stdin;
1	1	1	7e326012-288a-4acb-a961-c71e545b32bc	UQR7HP9VrTGwoo0dFx8SRCKaUvfLi9gb47xMtnro	5da83acc-6fd6-48ec-b22a-f7e9b5de8bef	ILWIORZCPKQUYZCYNSSJGXPDUAOPCODLGGAOFDGH
5	2	1	7e326012-288a-4acb-a961-c71e545b32bc	UQR7HP9VrTGwoo0dFx8SRCKaUvfLi9gb47xMtnro	5da83acc-6fd6-48ec-b22a-f7e9b5de8bef	ILWIORZCPKQUYZCYNSSJGXPDUAOPCODLGGAOFDGH
\.


--
-- Name: EMPRESA_ADQUIRENTE_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"EMPRESA_ADQUIRENTE_ID_seq"', 5, true);


--
-- Name: EMPRESA_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"EMPRESA_ID_seq"', 2, true);


--
-- Data for Name: TRANSACAO; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "TRANSACAO" ("ID", "ID_EMPRESA_ADQUIRENTE", "JSON_IN", "JSON_OUT", "OPERACAO", "DATA_HORA", "PROVIDER", "AMOUNT", "CREDITCARD_BRAND", "CREDITCARD_NUMBER", "STATUS", "PAYMENT_ID", "PAYMENT_AUTHCODE", "PAYMENT_PROOF_OF_SALE", "PAYMENT_TID", "PAYMENT_RECEIVED_DATE", "PAYMENT_RETURN_CODE", "PAYMENT_RETURN_MESSAGE", "REC_PAYMENT_ID", "REC_PAYMENT_AUTH_NOW", "REC_PAYMENT_START_DATE", "REC_PAYMENT_END_DATE", "REC_PAYMENT_NEXT_RECURRENCY", "REC_PAYMENT_REASON_CODE", "REC_PAYMENT_REASON_MESSAGE", "NUM_PEDIDO_VIRTUAL", "PAYMENT_CANCELADO", "REC_PAYMENT_DESABILITADO", "PAYMENT_REASON_CODE", "PAYMENT_REASON_MESSAGE", "PAYMENT_PROVIDER_RETURN_CODE", "PAYMENT_PROVIDER_RETURN_MESSAGE", "ID_PAI", "REC_PAYMENT_MONTHS_INTERVAL", "REC_PAYMENT_RECURRENCY_DAY") FROM stdin;
800	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20182000","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0918073433562","ProofOfSale":"3433562","AuthorizationCode":"329269","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"d220f7b9-f262-4bb3-a216-aa9fad16c7f2","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-18 19:34:32","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/d220f7b9-f262-4bb3-a216-aa9fad16c7f2"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/d220f7b9-f262-4bb3-a216-aa9fad16c7f2/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/d220f7b9-f262-4bb3-a216-aa9fad16c7f2/void"}]}	CREDITO_AVISTA	2018-09-18 19:34:36.332	Simulado	18880	Visa	000000******0001	1	d220f7b9-f262-4bb3-a216-aa9fad16c7f2	329269	3433562	0918073433562	2018-09-18 19:34:32	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20182000	\N	\N	\N	\N	\N	\N	\N	\N	\N
801	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20182001","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"3748c094-e8af-4b98-a5f3-a519323cfbbf","NextRecurrency":"2018-10-18","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0918073618293","ProofOfSale":"3618293","AuthorizationCode":"796859","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"15a08a56-3d90-4583-9c74-079f06035523","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-18 19:36:18","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/15a08a56-3d90-4583-9c74-079f06035523"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/15a08a56-3d90-4583-9c74-079f06035523/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/15a08a56-3d90-4583-9c74-079f06035523/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-18 19:36:21.067	Simulado	122990	Visa	000000******0001	1	15a08a56-3d90-4583-9c74-079f06035523	796859	3618293	0918073618293	2018-09-18 19:36:18	4	Operation Successful	3748c094-e8af-4b98-a5f3-a519323cfbbf	t	\N	2019-12-01	2018-10-18	0	Successful	20182001	\N	\N	\N	\N	\N	\N	\N	null	\N
802	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20182002","descricaoVenda":"Sistema Axe","valor":122991},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataInicial":"2018-09-25","dataFinal":"2020-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"9042c3a8-b273-4cac-839f-451cdb103502","NextRecurrency":"2018-09-25","StartDate":"2018-09-25","EndDate":"2020-12-01","AuthorizeNow":false,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"SoftDescriptor":"Sistema Axe","Provider":"Simulado","Type":"CreditCard","Amount":122991,"Currency":"BRL","Country":"BRA","Status":20}	CREDITO_AGENDADO_RECORRENTE	2018-09-18 19:37:53.656	Simulado	122991	Visa	000000******0001	20	\N	\N	\N	\N	\N	\N	\N	9042c3a8-b273-4cac-839f-451cdb103502	f	2018-09-25	2020-12-01	2018-09-25	0	Successful	20182002	\N	\N	\N	\N	\N	\N	\N	null	\N
803	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/d220f7b9-f262-4bb3-a216-aa9fad16c7f2"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-18 19:44:55.41	\N	\N	\N	\N	10	d220f7b9-f262-4bb3-a216-aa9fad16c7f2	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	0	Successful	0	Operation Successful	800	\N	\N
804	1	{"pedidoVirtualHttp":{"descricaoVenda":"Sistema AxeII","valor":19900},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	\N	CREDITO_RECORRENTE_ALTERACAO_PAGAMENTO_RECORRENTE	2018-09-18 19:46:41.163	\N	19900	Visa	0000000000000001	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	\N	\N
805	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DATA_FINAL_RECORRENCIA	2018-09-18 19:47:26.843	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	2025-07-01	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	\N	\N
806	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DIA_RECORENCIA	2018-09-18 19:47:46.959	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	\N	30
807	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_VALOR_RECORRENCIA	2018-09-18 19:48:01.46	\N	25000	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	\N	\N
808	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DATA_RECORRENCIA	2018-09-18 19:48:25.718	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	2018-10-16	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	\N	\N
809	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_INTERVALO_RECORRENCIA	2018-09-18 19:48:41.468	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	801	Bimonthly	\N
810	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-18 19:49:01.683	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	9042c3a8-b273-4cac-839f-451cdb103502	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	802	\N	\N
811	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-18 19:49:07.346	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	9042c3a8-b273-4cac-839f-451cdb103502	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	802	\N	\N
812	1	\N	\N	CREDITO_RECORRENTE_REATIVACAO	2018-09-18 19:49:24.425	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	9042c3a8-b273-4cac-839f-451cdb103502	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	\N	\N	802	\N	\N
813	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/15a08a56-3d90-4583-9c74-079f06035523"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-18 20:29:41.867	\N	\N	\N	\N	10	15a08a56-3d90-4583-9c74-079f06035523	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	0	Successful	0	Operation Successful	801	\N	\N
814	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-18 20:32:30.33	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	3748c094-e8af-4b98-a5f3-a519323cfbbf	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	801	\N	\N
850	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20183000","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0920103412053","ProofOfSale":"3412053","AuthorizationCode":"414784","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"e04d1607-daf7-4ea4-b67f-486f145613c9","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-20 10:34:11","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/e04d1607-daf7-4ea4-b67f-486f145613c9"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/e04d1607-daf7-4ea4-b67f-486f145613c9/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/e04d1607-daf7-4ea4-b67f-486f145613c9/void"}]}	CREDITO_AVISTA	2018-09-20 10:34:14.055	Simulado	18880	Visa	000000******0001	1	e04d1607-daf7-4ea4-b67f-486f145613c9	414784	3412053	0920103412053	2018-09-20 10:34:11	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20183000	\N	\N	\N	\N	\N	\N	\N	\N	\N
851	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20183001","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"1a68dd30-8216-4f42-a0d7-1c8f49da386a","NextRecurrency":"2018-10-20","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0920103452945","ProofOfSale":"3452945","AuthorizationCode":"418164","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"598b0299-8fec-492a-8124-79f1c9c8ce31","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-20 10:34:52","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/598b0299-8fec-492a-8124-79f1c9c8ce31"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/598b0299-8fec-492a-8124-79f1c9c8ce31/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/598b0299-8fec-492a-8124-79f1c9c8ce31/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-20 10:34:55.048	Simulado	122990	Visa	000000******0001	1	598b0299-8fec-492a-8124-79f1c9c8ce31	418164	3452945	0920103452945	2018-09-20 10:34:52	4	Operation Successful	1a68dd30-8216-4f42-a0d7-1c8f49da386a	t	\N	2019-12-01	2018-10-20	0	Successful	20183001	\N	\N	\N	\N	\N	\N	\N	null	\N
852	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20183002","descricaoVenda":"Sistema Axe","valor":122991},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataInicial":"2018-09-25","dataFinal":"2020-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"88fc8371-1630-4096-9b99-dde2720d70cf","NextRecurrency":"2018-09-25","StartDate":"2018-09-25","EndDate":"2020-12-01","AuthorizeNow":false,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"SoftDescriptor":"Sistema Axe","Provider":"Simulado","Type":"CreditCard","Amount":122991,"Currency":"BRL","Country":"BRA","Status":20}	CREDITO_AGENDADO_RECORRENTE	2018-09-20 10:35:16.773	Simulado	122991	Visa	000000******0001	20	\N	\N	\N	\N	\N	\N	\N	88fc8371-1630-4096-9b99-dde2720d70cf	f	2018-09-25	2020-12-01	2018-09-25	0	Successful	20183002	\N	\N	\N	\N	\N	\N	\N	null	\N
900	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/e04d1607-daf7-4ea4-b67f-486f145613c9"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-20 14:14:32.302	\N	\N	\N	\N	10	e04d1607-daf7-4ea4-b67f-486f145613c9	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	0	Successful	0	Operation Successful	850	\N	\N
901	1	{"pedidoVirtualHttp":{"descricaoVenda":"Sistema AxeII","valor":19900},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	\N	CREDITO_RECORRENTE_ALTERACAO_PAGAMENTO_RECORRENTE	2018-09-20 14:16:33.643	\N	19900	Visa	0000000000000001	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	\N	\N
902	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DATA_FINAL_RECORRENCIA	2018-09-20 14:17:18.154	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	2025-07-01	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	\N	\N
903	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DIA_RECORENCIA	2018-09-20 14:17:50.205	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	\N	30
904	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_VALOR_RECORRENCIA	2018-09-20 14:18:07.706	\N	25000	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	\N	\N
905	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_DATA_RECORRENCIA	2018-09-20 14:18:25.865	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	\N	2018-10-16	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	\N	\N
906	1	\N	\N	CREDITO_RECORRENTE_ALTERACAO_INTERVALO_RECORRENCIA	2018-09-20 14:18:46.636	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1a68dd30-8216-4f42-a0d7-1c8f49da386a	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	851	Bimonthly	\N
907	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-20 14:19:48.746	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	88fc8371-1630-4096-9b99-dde2720d70cf	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	852	\N	\N
908	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-20 14:19:57.045	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	88fc8371-1630-4096-9b99-dde2720d70cf	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	852	\N	\N
909	1	\N	\N	CREDITO_RECORRENTE_REATIVACAO	2018-09-20 14:20:11.022	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	88fc8371-1630-4096-9b99-dde2720d70cf	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	\N	\N	852	\N	\N
950	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20184000","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0920050220988","ProofOfSale":"220988","AuthorizationCode":"882452","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"bf3a5afb-d89f-4761-accb-3fc13a2c6cd7","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-20 17:02:12","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/bf3a5afb-d89f-4761-accb-3fc13a2c6cd7"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/bf3a5afb-d89f-4761-accb-3fc13a2c6cd7/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/bf3a5afb-d89f-4761-accb-3fc13a2c6cd7/void"}]}	CREDITO_AVISTA	2018-09-20 17:02:22.967	Simulado	18880	Visa	000000******0001	1	bf3a5afb-d89f-4761-accb-3fc13a2c6cd7	882452	220988	0920050220988	2018-09-20 17:02:12	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20184000	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Name: TRANSACAO_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"TRANSACAO_ID_seq"', 19, true);


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
-- Name: EMPRESA_USUARIO_SENHA_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "EMPRESA"
    ADD CONSTRAINT "EMPRESA_USUARIO_SENHA_key" UNIQUE ("USUARIO", "SENHA");


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


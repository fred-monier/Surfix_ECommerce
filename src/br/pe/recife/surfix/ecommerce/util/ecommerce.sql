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
1	11111111111112	Monier	usuario	senha
2	22222222222222	Hipotetica	usuario	senha
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
401	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/9f8f874e-fae4-4eb6-942c-0da7d7cc2066"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-06 17:51:17.036	\N	\N	\N	\N	10	9f8f874e-fae4-4eb6-942c-0da7d7cc2066	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	400	\N	\N
402	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180991","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"391f6752-7b6d-4cae-9ded-2b0f5fc6665b","NextRecurrency":"2018-10-06","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0906055831240","ProofOfSale":"5831240","AuthorizationCode":"108318","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"22f8d81a-b0d2-45d5-bc26-b3eb092057d7","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-06 17:58:31","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/22f8d81a-b0d2-45d5-bc26-b3eb092057d7"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/22f8d81a-b0d2-45d5-bc26-b3eb092057d7/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/22f8d81a-b0d2-45d5-bc26-b3eb092057d7/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-06 17:58:39.763	Simulado	122990	Visa	000000******0001	1	22f8d81a-b0d2-45d5-bc26-b3eb092057d7	108318	5831240	0906055831240	2018-09-06 17:58:31	4	Operation Successful	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	t	\N	2019-12-01	2018-10-06	0	Successful	20180991	\N	\N	\N	\N	\N	\N	\N	\N	\N
200	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180966","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0905123811229","ProofOfSale":"3811229","AuthorizationCode":"361639","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"26df8453-e45a-4202-ad15-95fd908ac289","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-05 12:38:11","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/26df8453-e45a-4202-ad15-95fd908ac289"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/26df8453-e45a-4202-ad15-95fd908ac289/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/26df8453-e45a-4202-ad15-95fd908ac289/void"}]}	CREDITO_AVISTA	2018-09-05 12:38:28.024	Simulado	18880	Visa	000000******0001	1	26df8453-e45a-4202-ad15-95fd908ac289	361639	3811229	0905123811229	2018-09-05 12:38:11	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
250	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180970","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"dd2d8d0d-2a91-4c4d-881c-de88f46c7359","NextRecurrency":"2018-10-05","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0905055459977","ProofOfSale":"5459977","AuthorizationCode":"135775","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"7918e20c-e9c9-4e74-a29e-8248ca71554b","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-05 17:54:47","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/7918e20c-e9c9-4e74-a29e-8248ca71554b"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/7918e20c-e9c9-4e74-a29e-8248ca71554b/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/7918e20c-e9c9-4e74-a29e-8248ca71554b/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-05 17:55:09.922	Simulado	122990	Visa	000000******0001	1	7918e20c-e9c9-4e74-a29e-8248ca71554b	135775	5459977	0905055459977	2018-09-05 17:54:47	4	Operation Successful	dd2d8d0d-2a91-4c4d-881c-de88f46c7359	t	\N	2019-12-01	2018-10-05	0	Successful	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
251	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180971","descricaoVenda":"Sistema Axe","valor":122991},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataInicial":"2018-09-09","dataFinal":"2020-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"8e73dc38-5fdd-4aad-96df-49e1236e95de","NextRecurrency":"2018-09-09","StartDate":"2018-09-09","EndDate":"2020-12-01","AuthorizeNow":false,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"SoftDescriptor":"Sistema Axe","Provider":"Simulado","Type":"CreditCard","Amount":122991,"Currency":"BRL","Country":"BRA","Status":20}	CREDITO_AGENDADO_RECORRENTE	2018-09-05 17:59:07.639	Simulado	122991	Visa	000000******0001	20	\N	\N	\N	\N	\N	\N	\N	8e73dc38-5fdd-4aad-96df-49e1236e95de	f	2018-09-09	2020-12-01	2018-09-09	0	Successful	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
300	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180978","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0905062015049","ProofOfSale":"2015049","AuthorizationCode":"325083","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"bc4e4b7f-cf5c-47e4-b1f0-73561d0959a8","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-05 18:20:14","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/bc4e4b7f-cf5c-47e4-b1f0-73561d0959a8"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/bc4e4b7f-cf5c-47e4-b1f0-73561d0959a8/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/bc4e4b7f-cf5c-47e4-b1f0-73561d0959a8/void"}]}	CREDITO_AVISTA	2018-09-05 18:20:50.645	Simulado	18880	Visa	000000******0001	1	bc4e4b7f-cf5c-47e4-b1f0-73561d0959a8	325083	2015049	0905062015049	2018-09-05 18:20:14	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20180978	\N	\N	\N	\N	\N	\N	\N	\N	\N
301	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180979","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"c95dcc75-6f9f-4e8a-b231-efbb4ea89a0d","NextRecurrency":"2018-10-05","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0905062142301","ProofOfSale":"2142301","AuthorizationCode":"976261","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"f1e15072-0f05-4bd4-97aa-f80bf9f5102b","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-05 18:21:42","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/f1e15072-0f05-4bd4-97aa-f80bf9f5102b"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/f1e15072-0f05-4bd4-97aa-f80bf9f5102b/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/f1e15072-0f05-4bd4-97aa-f80bf9f5102b/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-05 18:21:49.956	Simulado	122990	Visa	000000******0001	1	f1e15072-0f05-4bd4-97aa-f80bf9f5102b	976261	2142301	0905062142301	2018-09-05 18:21:42	4	Operation Successful	c95dcc75-6f9f-4e8a-b231-efbb4ea89a0d	t	\N	2019-12-01	2018-10-05	0	Successful	20180979	\N	\N	\N	\N	\N	\N	\N	\N	\N
302	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180980","descricaoVenda":"Sistema Axe","valor":122991},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataInicial":"2018-09-09","dataFinal":"2020-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"844c03ef-21b3-469a-b4dd-7f3aa0575011","NextRecurrency":"2018-09-09","StartDate":"2018-09-09","EndDate":"2020-12-01","AuthorizeNow":false,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"SoftDescriptor":"Sistema Axe","Provider":"Simulado","Type":"CreditCard","Amount":122991,"Currency":"BRL","Country":"BRA","Status":20}	CREDITO_AGENDADO_RECORRENTE	2018-09-05 18:22:50.013	Simulado	122991	Visa	000000******0001	20	\N	\N	\N	\N	\N	\N	\N	844c03ef-21b3-469a-b4dd-7f3aa0575011	f	2018-09-09	2020-12-01	2018-09-09	0	Successful	20180980	\N	\N	\N	\N	\N	\N	\N	\N	\N
350	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180980","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0906023319380","ProofOfSale":"3319380","AuthorizationCode":"247862","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"776fe64f-f58a-427b-8d5d-9477b2c89ad0","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-06 14:33:19","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/776fe64f-f58a-427b-8d5d-9477b2c89ad0"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/776fe64f-f58a-427b-8d5d-9477b2c89ad0/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/776fe64f-f58a-427b-8d5d-9477b2c89ad0/void"}]}	CREDITO_AVISTA	2018-09-06 14:33:26.225	Simulado	18880	Visa	000000******0001	1	776fe64f-f58a-427b-8d5d-9477b2c89ad0	247862	3319380	0906023319380	2018-09-06 14:33:19	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20180980	\N	\N	\N	\N	\N	\N	\N	\N	\N
351	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/776fe64f-f58a-427b-8d5d-9477b2c89ad0"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-06 14:36:35.16	\N	\N	\N	\N	10	776fe64f-f58a-427b-8d5d-9477b2c89ad0	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	350	\N	\N
352	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180981","descricaoVenda":"Sistema Axe","valor":122990},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataFinal":"2019-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"ae24d399-da0e-4a45-8d28-f0fcbb312dce","NextRecurrency":"2018-10-06","EndDate":"2019-12-01","AuthorizeNow":true,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0906023807698","ProofOfSale":"3807698","AuthorizationCode":"379112","SoftDescriptor":"Sistema Axe","Provider":"Simulado","PaymentId":"d947d620-66d4-45a1-8ba5-d3fdc155fae5","Type":"CreditCard","Amount":122990,"ReceivedDate":"2018-09-06 14:38:07","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/d947d620-66d4-45a1-8ba5-d3fdc155fae5"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/d947d620-66d4-45a1-8ba5-d3fdc155fae5/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/d947d620-66d4-45a1-8ba5-d3fdc155fae5/void"}]}	CREDITO_AVISTA_RECORRENTE	2018-09-06 14:38:14.841	Simulado	122990	Visa	000000******0001	1	d947d620-66d4-45a1-8ba5-d3fdc155fae5	379112	3807698	0906023807698	2018-09-06 14:38:07	4	Operation Successful	ae24d399-da0e-4a45-8d28-f0fcbb312dce	t	\N	2019-12-01	2018-10-06	0	Successful	20180981	\N	\N	\N	\N	\N	\N	\N	\N	\N
353	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180982","descricaoVenda":"Sistema Axe","valor":122991},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"},"recProgHttp":{"intervalo":"Monthly","dataInicial":"2018-09-09","dataFinal":"2020-12-01"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"RecurrentPayment":{"RecurrentPaymentId":"2310d086-099f-4e8a-a23d-16c37c5598c8","NextRecurrency":"2018-09-09","StartDate":"2018-09-09","EndDate":"2020-12-01","AuthorizeNow":false,"ReasonCode":0,"ReasonMessage":"Successful"},"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"SoftDescriptor":"Sistema Axe","Provider":"Simulado","Type":"CreditCard","Amount":122991,"Currency":"BRL","Country":"BRA","Status":20}	CREDITO_AGENDADO_RECORRENTE	2018-09-06 14:40:54.998	Simulado	122991	Visa	000000******0001	20	\N	\N	\N	\N	\N	\N	\N	2310d086-099f-4e8a-a23d-16c37c5598c8	f	2018-09-09	2020-12-01	2018-09-09	0	Successful	20180982	\N	\N	\N	\N	\N	\N	\N	\N	\N
400	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20180990","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0906054708644","ProofOfSale":"4708644","AuthorizationCode":"168145","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"9f8f874e-fae4-4eb6-942c-0da7d7cc2066","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-06 17:47:08","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/9f8f874e-fae4-4eb6-942c-0da7d7cc2066"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/9f8f874e-fae4-4eb6-942c-0da7d7cc2066/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/9f8f874e-fae4-4eb6-942c-0da7d7cc2066/void"}]}	CREDITO_AVISTA	2018-09-06 17:47:15.784	Simulado	18880	Visa	000000******0001	1	9f8f874e-fae4-4eb6-942c-0da7d7cc2066	168145	4708644	0906054708644	2018-09-06 17:47:08	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20180990	\N	\N	\N	\N	\N	\N	\N	\N	\N
354	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-06 14:44:28.294	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	2310d086-099f-4e8a-a23d-16c37c5598c8	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	353	\N	\N
355	1	\N	\N	CREDITO_RECORRENTE_REATIVACAO	2018-09-06 14:48:03.734	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	2310d086-099f-4e8a-a23d-16c37c5598c8	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	\N	\N	353	\N	\N
403	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-06 18:00:13.581	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	402	\N	\N
404	1	\N	\N	CREDITO_RECORRENTE_REATIVACAO	2018-09-06 18:01:23.403	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	\N	\N	402	\N	\N
405	1	\N	\N	CREDITO_RECORRENTE_REATIVACAO	2018-09-06 18:05:54.511	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	\N	\N	402	\N	\N
406	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-06 18:06:31.796	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	402	\N	\N
407	1	\N	\N	CREDITO_RECORRENTE_DESATIVACAO	2018-09-06 18:06:35.543	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	391f6752-7b6d-4cae-9ded-2b0f5fc6665b	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	402	\N	\N
450	1	{"pedidoVirtualHttp":{"numPedidoVirtual":"20181001","descricaoVenda":"Sistema Orion","valor":18880},"cartaoCreditoHttp":{"bandeiraCartao":"Visa","numCartao":"0000000000000001","mesAnoExpDate":"12/2030","nomeClienteCartao":"Fulano de Tal","cvv":"123"}}	{"ServiceTaxAmount":0,"Installments":1,"Interest":"0","Capture":false,"Authenticate":false,"Recurrent":false,"CreditCard":{"CardNumber":"000000******0001","Holder":"Fulano de Tal","ExpirationDate":"12/2030","SaveCard":false,"Brand":"Visa"},"Tid":"0912051511741","ProofOfSale":"1511741","AuthorizationCode":"843400","SoftDescriptor":"Sistema Orion","Provider":"Simulado","PaymentId":"f2e51b9a-2c85-4cf4-b009-9c796eaed225","Type":"CreditCard","Amount":18880,"ReceivedDate":"2018-09-12 17:15:10","Currency":"BRL","Country":"BRA","ReturnCode":"4","ReturnMessage":"Operation Successful","Status":1,"Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/f2e51b9a-2c85-4cf4-b009-9c796eaed225"},{"Method":"PUT","Rel":"capture","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/f2e51b9a-2c85-4cf4-b009-9c796eaed225/capture"},{"Method":"PUT","Rel":"void","Href":"https://apisandbox.cieloecommerce.cielo.com.br/1/sales/f2e51b9a-2c85-4cf4-b009-9c796eaed225/void"}]}	CREDITO_AVISTA	2018-09-12 17:15:16.529	Simulado	18880	Visa	000000******0001	1	f2e51b9a-2c85-4cf4-b009-9c796eaed225	843400	1511741	0912051511741	2018-09-12 17:15:10	4	Operation Successful	\N	\N	\N	\N	\N	\N	\N	20181001	\N	\N	\N	\N	\N	\N	\N	\N	\N
500	1	\N	{"Status":"10","ReasonCode":"0","ReasonMessage":"Successful","ProviderReturnCode":"0","ProviderReturnMessage":"Operation Successful","ReturnCode":"0","ReturnMessage":"Operation Successful","Links":[{"Method":"GET","Rel":"self","Href":"https://apiquerysandbox.cieloecommerce.cielo.com.br/1/sales/f2e51b9a-2c85-4cf4-b009-9c796eaed225"}]}	CREDITO_AVISTA_CANCELAMENTO	2018-09-12 17:45:24.62	\N	\N	\N	\N	10	f2e51b9a-2c85-4cf4-b009-9c796eaed225	\N	\N	\N	\N	0	Operation Successful	\N	\N	\N	\N	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	450	\N	\N
\.


--
-- Name: TRANSACAO_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"TRANSACAO_ID_seq"', 10, true);


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


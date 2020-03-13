--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12
-- Dumped by pg_dump version 10.12

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

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    id integer NOT NULL,
    firstname character varying(30),
    lastname character varying(30)
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_id_seq OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- Name: goods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.goods (
    id integer NOT NULL,
    goodname character varying(30),
    price money
);


ALTER TABLE public.goods OWNER TO postgres;

--
-- Name: goods_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.goods_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.goods_id_seq OWNER TO postgres;

--
-- Name: goods_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.goods_id_seq OWNED BY public.goods.id;


--
-- Name: purchases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchases (
    id integer NOT NULL,
    customerid integer,
    goodid integer,
    purchasedate date
);


ALTER TABLE public.purchases OWNER TO postgres;

--
-- Name: purchases_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchases_id_seq OWNER TO postgres;

--
-- Name: purchases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;


--
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- Name: goods id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods ALTER COLUMN id SET DEFAULT nextval('public.goods_id_seq'::regclass);


--
-- Name: purchases id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (id, firstname, lastname) FROM stdin;
1	Ivan	Petrov
2	Igor	Fedorov
3	Semen	Ivanov
4	Vlad	Egorov
5	Pavel	Semenov
6	Anna	Alekseeva
7	Maria	Usova
8	Vera	Nikitina
\.


--
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.goods (id, goodname, price) FROM stdin;
1	Milk	62,50 ?
2	Apples	90,00 ?
3	Oranges	65,00 ?
4	Rice	75,00 ?
5	Butter	120,00 ?
6	Meat	450,00 ?
7	Tea	112,90 ?
8	Coffee	250,00 ?
9	Sweets	134,50 ?
10	Tomatoes	130,00 ?
11	Chocolate	50,00 ?
\.


--
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchases (id, customerid, goodid, purchasedate) FROM stdin;
1	3	5	2020-02-12
2	3	1	2020-02-12
3	3	6	2020-02-12
4	3	2	2020-02-10
5	1	8	2020-02-09
6	1	11	2020-02-23
7	1	5	2020-02-01
8	2	7	2020-02-29
9	4	3	2020-02-15
10	4	1	2020-01-10
11	4	10	2020-02-12
12	6	5	2020-03-01
13	6	3	2020-03-01
14	2	11	2020-02-24
15	2	9	2020-02-24
16	2	3	2020-02-24
17	2	4	2020-02-24
18	8	2	2020-02-01
19	8	1	2020-02-01
20	8	7	2020-02-10
21	8	4	2020-02-25
22	7	3	2020-03-03
23	7	2	2020-02-10
24	7	4	2020-02-11
25	7	10	2020-02-27
26	6	2	2020-02-05
27	6	3	2020-02-05
28	6	8	2020-02-18
29	6	10	2020-02-12
30	6	2	2020-02-12
31	6	11	2020-02-13
32	5	1	2020-02-12
33	5	6	2020-02-12
34	5	2	2020-02-10
35	5	8	2020-02-09
36	5	11	2020-02-23
37	7	5	2020-02-01
38	7	7	2020-02-29
39	7	3	2020-02-15
40	8	1	2020-01-10
41	8	10	2020-02-12
42	8	5	2020-03-01
43	8	3	2020-03-01
44	1	11	2020-02-24
45	1	9	2020-02-24
46	1	3	2020-02-24
47	1	4	2020-02-24
48	5	2	2020-02-01
49	5	1	2020-02-01
50	5	7	2020-02-10
51	5	3	2020-02-25
52	1	3	2020-03-03
53	1	2	2020-02-10
54	1	4	2020-02-11
55	1	10	2020-02-27
56	7	2	2020-02-05
57	7	3	2020-02-05
58	4	8	2020-02-18
59	3	10	2020-02-12
60	2	2	2020-02-12
61	1	11	2020-02-13
\.


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_id_seq', 8, true);


--
-- Name: goods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.goods_id_seq', 11, true);


--
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchases_id_seq', 61, true);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- Name: purchases purchases_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);


--
-- Name: purchases purchases_customerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.customers(id);


--
-- Name: purchases purchases_goodid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_goodid_fkey FOREIGN KEY (goodid) REFERENCES public.goods(id);


--
-- PostgreSQL database dump complete
--


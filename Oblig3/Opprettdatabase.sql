-- Oppretter tabellen Skole med prim�rn�kkelen navn
CREATE TABLE Skole (
    navn text PRIMARY KEY
);

-- Oppretter tabellen Kull med prim�rn�kkelen kode og fremmedn�kkelen 
-- skole som gj�r at et kull assosieres til Skole via navn
CREATE TABLE Kull (
    kode text PRIMARY KEY,
    skole text REFERENCES Skole(navn)
);

-- Oppretter tabellen Student med prim�rn�kkelen nr, attributten navn og
-- fremmedn�kkelen kull som gj�r at en student assosieres til Kull via kode
CREATE TABLE Student (
    nr text PRIMARY KEY,
    navn text NOT NULL,
    kull text REFERENCES Kull(kode)
);

-- Oppretter tabellen Karakter med prim�rn�kkelen id, attributtene karakter og �r og
-- fremmedn�kkelen student som gj�r at en karakter assieres til Student via nr
CREATE TABLE Karakter (
    id integer PRIMARY KEY,
    karakter text NOT NULL,
    ar integer NOT NULL,
    student text REFERENCES Student(nr),
	kurs text REFERENCES Kurs(kode)
);

-- Oppretter tabellen Kurs med prim�rn�kkelen kode, attributten navn og
-- fremmedn�klene skole og karakter som gj�r at et kurs assosieres med
-- henholdsvis Skole via navn og Karakter via id
CREATE TABLE Kurs (
    kode text PRIMARY KEY,
    navn text NOT NULL,
    skole text REFERENCES Skole (navn)
);

-- Oppretter tabellen Skole med primærnøkkelen navn
CREATE TABLE Skole (
    navn text PRIMARY KEY
);

-- Oppretter tabellen Kull med primærnøkkelen kode og fremmednøkkelen 
-- skole som gjør at et kull assosieres til Skole via navn
CREATE TABLE Kull (
    kode text PRIMARY KEY,
    skole text REFERENCES Skole(navn)
);

-- Oppretter tabellen Student med primærnøkkelen nr, attributten navn og
-- fremmednøkkelen kull som gjør at en student assosieres til Kull via kode
CREATE TABLE Student (
    nr text PRIMARY KEY,
    navn text NOT NULL,
    kull text REFERENCES Kull(kode)
);

-- Oppretter tabellen Karakter med primærnøkkelen id, attributtene karakter og år og
-- fremmednøkkelen student som gjør at en karakter assieres til Student via nr
CREATE TABLE Karakter (
    id integer PRIMARY KEY,
    karakter text NOT NULL,
    ar integer NOT NULL,
    student text REFERENCES Student(nr),
	kurs text REFERENCES Kurs(kode)
);

-- Oppretter tabellen Kurs med primærnøkkelen kode, attributten navn og
-- fremmednøklene skole og karakter som gjør at et kurs assosieres med
-- henholdsvis Skole via navn og Karakter via id
CREATE TABLE Kurs (
    kode text PRIMARY KEY,
    navn text NOT NULL,
    skole text REFERENCES Skole (navn)
);

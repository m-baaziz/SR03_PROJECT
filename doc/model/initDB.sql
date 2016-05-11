CREATE TABLE user (
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
	name VARCHAR(255) NOT NULL,
	company VARCHAR(255),
	phone INTEGER,
	isActive BOOLEAN,
	PRIMARY KEY (email),
	CHECK (LEN(password) > 6),
	CHECK (type IN ('administrator', 'intern'))
);

CREATE TABLE test (
	subject VARCHAR(255) NOT NULL,
	isActive BOOLEAN,
	PRIMARY KEY (subject)
);

CREATE TABLE record (
	recordId INTEGER NOT NULL AUTO_INCREMENT,
	email VARCHAR(255) NOT NULL,
	subject VARCHAR(255) NOT NULL,
	score INTEGER,
	duration TIME,
	PRIMARY KEY (recordId),
	FOREIGN KEY (subject) REFERENCES test(subject),
	FOREIGN KEY (email) REFERENCES user(email) ON DELETE CASCADE
);

CREATE TABLE question (
	questionId INTEGER NOT NULL AUTO_INCREMENT,
	subject VARCHAR(255) NOT NULL,
	questionText TEXT NOT NULL,
	isActive BOOLEAN,
	PRIMARY KEY (questionID),
	FOREIGN KEY (subject) REFERENCES test(subject) ON DELETE CASCADE
);

CREATE TABLE answer (
	answerId INTEGER NOT NULL AUTO_INCREMENT,
	questionId INTEGER NOT NULL,
	answerText TEXT NOT NULL,
	isActive BOOLEAN,
	PRIMARY KEY (answerId),
	FOREIGN KEY (questionId) REFERENCES question(questionId) ON DELETE CASCADE
);

CREATE TABLE recordAnswers (
	recordId INTEGER NOT NULL,
	answerId INTEGER NOT NULL,
	PRIMARY KEY (recordId, answerId),
	FOREIGN KEY (recordId) REFERENCES record(recordId) ON DELETE CASCADE,
	FOREIGN KEY (answerId) REFERENCES answer(answerId) ON DELETE CASCADE
);
	


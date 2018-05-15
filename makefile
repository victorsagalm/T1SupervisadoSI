JCC= javac

JFLAGS= -g -Xlint:all
PACKAGE= Ejemplo/
JAR= ".:jar/weka.jar"
TRAIN= input/data/training.arff
TEST= input/data/testing.arff
all: Main.class

%.class: src/$(PACKAGE)%.java
	$(JCC) -sourcepath src -classpath . -cp $(JAR) $(JFLAGS) $< -d .


run: 
	java -cp $(JAR) $(PACKAGE)Main $(TRAIN) $(TEST)

clean: 
	rm -Rf $(PACKAGE)


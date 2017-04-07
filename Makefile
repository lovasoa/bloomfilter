JAVAC=javac

all: build/BloomFilter.jar

clean :
	rm -rf build/*

build/BloomFilterTest.jar : build/BloomFilter.class build/Test.class
	jar cmvf META-INF/MANIFEST.MF $@ -C build .

test: build/BloomFilterTest.jar
	java -jar $< 

build/%.class : src/main/java/BloomFilter/%.java
	$(JAVAC) -d ./build/ $<

build/%.class : test/%.java src/main/java/BloomFilter/BloomFilter.java
	$(JAVAC) -d ./build/ $^

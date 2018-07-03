# TüBa-D/Z to UD converter

This program converts the
[TüBa-D/Z constituency treebank](http://www.sfs.uni-tuebingen.de/en/ascl/resources/corpora/tueba-dz.html)
to [Universal Dependencies](http://universaldependencies.org/)
version 2 compliant dependency treebank.

The details of the conversion process is described in the paper:

- Çağrı Çöltekin, Ben Campbell, Erhard Hinrichs and Heike Telljohann
  (2017) [Converting the TüBa-D/Z Treebank of German to Universal
  Dependencies](http://www.aclweb.org/anthology/W17-0404).
  In: Proceedings of the NoDaLiDa 2017 Workshop on
  Universal Dependencies (UDW 2017), pages 27–37
  ([bibtex](tueba-to-ud.bib))

The latest release of the TüBa-D/Z distribution (release 11)
already includes a `.conllu` file created using this tool.
This application is useful for converting earlier versions of the treebank,
as well as serving as a detailed documentation of the conversion process.

## Usage

The application is written in Java, and can be compiled
using [Maven](https://maven.apache.org/).
For example,
```
cd TreebankUdConverter
mvn clean package
```
should produce a jar file `TreebankUdConverter-0.0.1-SNAPSHOT.jar`
in the directory `target`.

The `TuebaUdConverter` program takes two arguments:

- The first is the path of the file to be converted, the TüBa-D/Z treebank in XML format.
- The second is the path of the generated output file.

For example (assuming the xml version of the treebank is copied to the same
directory),

```
java -jar TreebankUdConverter-0.0.1-SNAPSHOT.jar tuebadz-10.0-exportXML-v2.xml output.conllu
```

should convert the release 10 of the TüBa-D/Z treebank to UD,
and store the resulting CoNLL-U file as `output.conlllu`.

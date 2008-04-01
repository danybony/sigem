
# Azienda: Stylosoft
# Nome file: Makefile
# Autore: Luca Rubin
# Data: 30/03/2008
# Versione: 1.0
# Licenza: open-source



.SUFFIXES: .java .class

.class .java:
	javac $<

install:
	javac sorgenti/logic/parametri/EccezioneConfigurazioneNonValida.java  -d ./ -classpath ./
	javac sorgenti/logic/gestioneMemoria/FrameMemoria.java  -d ./ -classpath ./
	javac sorgenti/logic/parametri/Id.java  -d ./ -classpath ./
	javac sorgenti/logic/parametri/Processo.java  -d ./ -classpath ./
	javac sorgenti/logic/parametri/ProcessoConPriorita.java  -d ./ -classpath ./
	javac sorgenti/logic/schedulazione/*.java  -d ./ -classpath ./
	javac sorgenti/logic/parametri/ConfigurazioneIniziale.java  -d ./ -classpath ./
	javac sorgenti/logic/caricamento/*.java  -d ./ -classpath ./
	javac sorgenti/logic/gestioneMemoria/*.java  -d ./ -classpath ./
	javac sorgenti/logic/parametri/*.java  -d ./ -classpath ./
	javac sorgenti/logic/simulazione/Istante.java  -d ./ -classpath ./
	javac sorgenti/logic/*.java  -d ./ -classpath ./
	javac sorgenti/logic/simulazione/*.java  -d ./ -classpath ./
	javac sorgenti/logic/*.java  -d ./ -classpath ./

	javac sorgenti/gui/utility/*.java  -d ./ -classpath "./lib/BareBonesBrowserLaunch.jar"
	javac sorgenti/gui/view/*.java  -d ./ -classpath "./:./lib/jfreechart-1.0.9.jar:./lib/jfreechart-1.0.9-experimental.jar:./lib/jfreechart-1.0.9-swt.jar:./lib/swtgraphics2d.jar:./lib/gnujaxp.jar:./lib/itext-2.0.6.jar:./lib/jcommon-1.0.12.jar"
	javac sorgenti/gui/dialog/*.java sorgenti/gui/*.java  -d ./ -classpath "./:./lib/BareBonesBrowserLaunch.jar:./lib/forms-1.2.0.jar:./lib/idw-gpl.jar:./lib/ilf-gpl.jar:./lib/itp-gpl.jar:./lib/looks-2.1.4.jar:./lib/jfreechart-1.0.9.jar"
	javac sorgenti/gui/dialog/*.java  -d ./ -classpath ./

	jar cvfm SiGeM.jar ./lib/MANIFEST ./gui ./logic ./images
	
	rm -f ./gui/dialog/*.class
	rmdir ./gui/dialog
	rm -f ./gui/utility/*.class
	rmdir ./gui/utility
	rm -f ./gui/view/*.class
	rmdir ./gui/view
	rm -f ./gui/*.class
	rmdir ./gui/
	rm -f ./logic/caricamento/*.class
	rmdir ./logic/caricamento
	rm -f ./logic/gestioneMemoria/*.class
	rmdir ./logic/gestioneMemoria
	rm -f ./logic/parametri/*.class
	rmdir ./logic/parametri
	rm -f ./logic/schedulazione/*.class
	rmdir ./logic/schedulazione
	rm -f ./logic/simulazione/*.class
	rmdir ./logic/simulazione
	rm -f ./logic/*.class
	rmdir ./logic

esegui:
	java -jar SiGeM.jar

uninstall: 
	rm SiGeM.jar


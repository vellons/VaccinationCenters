# Centri vaccinali - Raccolta di eventi avversi
All'interno della cartella bin sono già presenti i file .jar dell'applicazione.

### Build ed esecuzione con Maven
Il progetto è stato sviluppato con Java 11 e IntelliJ IDEA.
Se stai usando IntelliJ, esegui questi comandi nel terminale premendo CTRL+INVIO:

$ mvn clean compile package install

Dopo la compilazione, nella cartella di destinazione troverai 3 file jar.

Per eseguire il server con maven esegui il comando:
$ mvn exec:java

### In caso di problemi
Se hai problemi con IntelliJ: rimuovi la cartella 'target' e ricompila il progetto.

Aggiungi le dipendenze all'interno di lib/ a IntelliJ:

File > Struttura del progetto > Moduli > Aggiungi libreria. 

In questo modo puoi compilare ed eseguire le applicazioni con il tasto play di IntelliJ.
